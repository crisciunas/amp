package org.digijava.module.aim.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.entity.Locale;
import org.digijava.kernel.request.SiteDomain;
import org.digijava.kernel.translator.util.TrnLocale;
import org.digijava.kernel.translator.util.TrnUtil;
import org.digijava.kernel.util.RequestUtils;
import org.digijava.kernel.util.SiteUtils;
import org.digijava.module.aim.dbentity.AmpApplicationSettings;
import org.digijava.module.aim.dbentity.AmpTeamMember;
import org.digijava.module.aim.form.UpdateAppSettingsForm;
import org.digijava.module.aim.helper.ApplicationSettings;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.util.CurrencyUtil;
import org.digijava.module.aim.util.DbUtil;
import org.digijava.module.aim.util.TeamMemberUtil;
import org.digijava.module.aim.util.TeamUtil;

public class UpdateAppSettings extends Action {

	private static Logger logger = Logger.getLogger(UpdateAppSettings.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws java.lang.Exception {

		UpdateAppSettingsForm uForm = (UpdateAppSettingsForm) form;
		logger.debug("In updtate app settings");

		HttpSession session = request.getSession();

		if (session.getAttribute("currentMember") == null) {
			return mapping.findForward("index");
		}

		TeamMember tm = (TeamMember) session.getAttribute("currentMember");
		
		if (request.getParameter("updated") != null &&
				request.getParameter("updated").equals("true")) {
			uForm.setUpdated(true);
		} else {
			uForm.setUpdated(false);
		}

		if (uForm.getType() == null
				|| uForm.getType().trim().equals("")) {
			String path = mapping.getPath();
			logger.debug("path = " + path);
			AmpApplicationSettings ampAppSettings = null;
			if (path != null && (path.trim().equals("/aim/defaultSettings") || path.trim().equals("/defaultSettings"))) {
				if (tm.getTeamHead() == false) {
					return mapping.findForward("viewMyDesktop");
				}
				uForm.setType("default");
				uForm.setTeamName(tm.getTeamName());
				ampAppSettings = DbUtil.getTeamAppSettings(tm.getTeamId());
			} else if (path != null
					&& (path.trim().equals("/aim/customizeSettings") || path.trim().equals("/customizeSettings"))) {
				uForm.setType("userSpecific");
				uForm.setMemberName(tm.getMemberName());
				ampAppSettings = DbUtil.getMemberAppSettings(tm.getMemberId());
			}

			if (ampAppSettings != null) {
				uForm.setAppSettingsId(ampAppSettings.getAmpAppSettingsId());
				uForm.setDefRecsPerPage(ampAppSettings
						.getDefaultRecordsPerPage().intValue());
				uForm.setLanguage(ampAppSettings.getLanguage());
				uForm.setDefPerspective(ampAppSettings.getDefaultPerspective());
				uForm.setCurrencyId(ampAppSettings.getCurrency()
						.getAmpCurrencyId());
				uForm.setFisCalendarId(ampAppSettings.getFiscalCalendar()
						.getAmpFiscalCalId());
			}
			uForm.setCurrencies(CurrencyUtil.getAllCurrencies(1));
			uForm.setFisCalendars(DbUtil.getAllFisCalenders());
			
			// set Navigation languages
			Set languages = SiteUtils.getUserLanguages(RequestUtils
					.getSite(request));

			HashMap translations = new HashMap();
			Iterator iterator = TrnUtil.getLanguages(RequestUtils.
					getNavigationLanguage(request).getCode()).iterator();
			while (iterator.hasNext()) {
				TrnLocale item = (TrnLocale) iterator.next();
				translations.put(item.getCode(), item);
			}
			//sort languages
			List sortedLanguages = new ArrayList();
			iterator = languages.iterator();
			while (iterator.hasNext()) {
				Locale item = (Locale) iterator.next();
				sortedLanguages.add(translations.get(item.getCode()));
			}
			Collections.sort(sortedLanguages, TrnUtil.localeNameComparator);
			uForm.setLanguages(sortedLanguages);
			
			logger.info("TYpe =" + uForm.getType());
			if (uForm.getType().equalsIgnoreCase("userSpecific")) {
				logger.info("mapping to showUserSettings");
				return mapping.findForward("showUserSettings");				
			} else {
				return mapping.findForward("showDefaultSettings");
			}
		} else {
			logger.debug("In saving");
			AmpApplicationSettings ampAppSettings = null;
			if (uForm.getSave() != null) {
				ampAppSettings = new AmpApplicationSettings();
				ampAppSettings.setAmpAppSettingsId(uForm.getAppSettingsId());
				ampAppSettings.setDefaultRecordsPerPage(new Integer(uForm
						.getDefRecsPerPage()));
				ampAppSettings.setCurrency(CurrencyUtil.getAmpcurrency(uForm
						.getCurrencyId()));
				ampAppSettings.setFiscalCalendar(DbUtil
						.getAmpFiscalCalendar(uForm.getFisCalendarId()));
				ampAppSettings.setLanguage(uForm.getLanguage());
				ampAppSettings.setDefaultPerspective(uForm.getDefPerspective());
				ampAppSettings.setTeam(TeamUtil.getAmpTeam(tm.getTeamId()));
				if (uForm.getType().equals("userSpecific")) {
					ampAppSettings.setMember(TeamMemberUtil.getAmpTeamMember(tm
							.getMemberId()));
					ampAppSettings.setUseDefault(new Boolean(false));
				} else {
					/* change all members settings whose has 'useDefault' set */
					Iterator itr = TeamMemberUtil.getAllTeamMembers(tm.getTeamId())
							.iterator();
					logger.debug("before while");
					while (itr.hasNext()) {
						TeamMember member = (TeamMember) itr.next();
						AmpApplicationSettings memSettings = DbUtil
								.getMemberAppSettings(member.getMemberId());
						if (memSettings.getUseDefault().booleanValue() == true) {
							AmpTeamMember ampMember = TeamMemberUtil
									.getAmpTeamMember(member.getMemberId());
							restoreApplicationSettings(memSettings,
									ampAppSettings, ampMember);

						}
					}
				}

				try {
					DbUtil.update(ampAppSettings);
					uForm.setUpdated(true);
				} catch (Exception e) {
					uForm.setUpdated(false);
				}
			} else if (uForm.getRestore() != null) {
				ampAppSettings = DbUtil
						.getTeamAppSettings(tm.getTeamId());
				AmpApplicationSettings memSettings = DbUtil
						.getMemberAppSettings(tm.getMemberId());
				AmpTeamMember member = TeamMemberUtil
						.getAmpTeamMember(tm.getMemberId());
				try {
					restoreApplicationSettings(memSettings, ampAppSettings,
							member);
					uForm.setUpdated(true);
				} catch (Exception e) {
					uForm.setUpdated(false);
				}
			}
			AmpApplicationSettings tempSettings = DbUtil
					.getMemberAppSettings(tm.getMemberId());
			ApplicationSettings applicationSettings = getReloadedAppSettings(tempSettings);
			tm.setAppSettings(applicationSettings);
			if (session.getAttribute("currentMember") != null) {
				session.removeAttribute("currentMember");
				session.setAttribute("currentMember", tm);
			}
			logger.debug("settings updated");

			session.setAttribute(Constants.DESKTOP_SETTINGS_CHANGED,new Boolean(true));
			
			uForm.setUpdateFlag(false);
			SiteDomain currentDomain = RequestUtils.getSiteDomain(request);
			
			String context = SiteUtils.getSiteURL(currentDomain, request.getScheme(),
                            request.getServerPort(),
                            request.getContextPath());					
			if (uForm.getType().equals("default")) {
				uForm.setType(null);
				String url = context + "/translation/switchLanguage.do?code=" +
				ampAppSettings.getLanguage() +"&rfr="+context+"/aim/defaultSettings.do~updated="+uForm.getUpdated();
				response.sendRedirect(url);					
				logger.debug("redirecting " + url + " ....");
				//return mapping.findForward("default");
				return null;
			} else if (uForm.getType().equals("userSpecific")) {
				uForm.setType(null);
				String url = context + "/translation/switchLanguage.do?code=" +
				ampAppSettings.getLanguage() +"&rfr="+context+"/aim/customizeSettings.do~updated="+uForm.getUpdated();
				response.sendRedirect(url);					
				logger.debug("redirecting " + url + " ....");
				//return mapping.findForward("userSpecific");
				return null;
			} else {
				return mapping.findForward("index");
			}
		}
	}

	/* restoreApplicationSettings( oldSettings , newSettings , member) */
	public void restoreApplicationSettings(AmpApplicationSettings oldSettings,
			AmpApplicationSettings newSettings, AmpTeamMember ampMember) {

		logger.debug("In restoreApplicationSettings() ");

		/* set all values except id from oldSettings to newSettings */
		oldSettings.setDefaultRecordsPerPage(newSettings
				.getDefaultRecordsPerPage());
		oldSettings.setCurrency(newSettings.getCurrency());
		oldSettings.setFiscalCalendar(newSettings.getFiscalCalendar());
		oldSettings.setLanguage(newSettings.getLanguage());
		oldSettings.setDefaultPerspective(newSettings.getDefaultPerspective());
		oldSettings.setTeam(newSettings.getTeam());
		oldSettings.setMember(ampMember);
		oldSettings.setUseDefault(new Boolean(true));
		DbUtil.update(oldSettings);
		logger.debug("restoreApplicationSettings() returning");
	}

	public ApplicationSettings getReloadedAppSettings(
			AmpApplicationSettings ampAppSettings) {
		ApplicationSettings appSettings = new ApplicationSettings();
		appSettings.setAppSettingsId(ampAppSettings.getAmpAppSettingsId());
		appSettings.setDefRecsPerPage(ampAppSettings.getDefaultRecordsPerPage()
				.intValue());
		appSettings.setCurrencyId(ampAppSettings.getCurrency()
				.getAmpCurrencyId());
		appSettings.setFisCalId(ampAppSettings.getFiscalCalendar()
				.getAmpFiscalCalId());
		appSettings.setLanguage(ampAppSettings.getLanguage());
		appSettings.setPerspective(ampAppSettings.getDefaultPerspective());
		return appSettings;
	}
}