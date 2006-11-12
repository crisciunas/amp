/*
 * AddTheme.java 
 */

package org.digijava.module.aim.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.module.aim.dbentity.AmpTheme;
import org.digijava.module.aim.form.ThemeForm;
import org.digijava.module.aim.util.DbUtil;


public class AddTheme extends Action 
{
	private static Logger logger = Logger.getLogger(AddTheme.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws java.lang.Exception 
	{

		HttpSession session = request.getSession();
		if (session.getAttribute("ampAdmin") == null) 
		{
			return mapping.findForward("index");
		} else 
		{
			String str = (String) session.getAttribute("ampAdmin");
			if (str.equals("no")) 
			{
				return mapping.findForward("index");
			}
		}

		ThemeForm themeForm = (ThemeForm) form;
		String event = request.getParameter("event");
		
		if (event != null && event.equals("add")) 
		{
			themeForm.setProgramName(null);
			themeForm.setProgramCode(null);
			themeForm.setProgramDescription(null);
			themeForm.setProgramType(null);
			themeForm.setPrgLanguage(null);
			themeForm.setVersion(null);
			return mapping.findForward("forward");
		}
		else
		{
			AmpTheme ampTheme = new AmpTheme();
			ampTheme.setName(themeForm.getProgramName());
			ampTheme.setThemeCode(themeForm.getProgramCode());
			ampTheme.setDescription(themeForm.getProgramDescription());
			ampTheme.setType(themeForm.getProgramType());
			ampTheme.setParentThemeId(null);
			ampTheme.setLanguage(null);
			ampTheme.setVersion(null);
			DbUtil.add(ampTheme);
			return mapping.findForward("saveIt");
		}
	}
}
