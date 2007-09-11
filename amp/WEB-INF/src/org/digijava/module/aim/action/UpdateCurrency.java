/*
 * UpdateCurrency.java
 */

package org.digijava.module.aim.action;

import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.dbentity.Country;
import org.digijava.module.aim.dbentity.AmpCurrency;
import org.digijava.module.aim.dbentity.AmpCurrencyRate;
import org.digijava.module.aim.form.CurrencyForm;
import org.digijava.module.aim.helper.CountryBean;
import org.digijava.module.aim.helper.DateConversion;
import org.digijava.module.aim.util.CurrencyUtil;
import org.digijava.module.aim.util.DbUtil;

public class UpdateCurrency extends Action {

        private static Logger logger = Logger.getLogger(UpdateCurrency.class);

        public ActionForward execute(ActionMapping mapping, ActionForm form,
                        HttpServletRequest request, HttpServletResponse response)
                        throws java.lang.Exception {

                HttpSession session = request.getSession();
                if (session.getAttribute("ampAdmin") == null) {
                        return mapping.findForward("index");
                } else {
                        String str = (String)session.getAttribute("ampAdmin");
                        if (str.equals("no")) {
                                return mapping.findForward("index");
                        }
                }

                try {

                CurrencyForm crForm = (CurrencyForm) form;

                if (crForm.getDoAction() == null ||
                                crForm.getDoAction().equals("showCurrencies")) {
                        if (crForm.getCurrencyCode() != null && !crForm.getCurrencyCode().equals("") ) {
                                Iterator itr = crForm.getAllCurrencies().iterator();
                                AmpCurrency curr = null;
                                while (itr.hasNext()) {
                                        curr = (AmpCurrency) itr.next();
                                        if (curr.getCurrencyCode().equals(crForm.getCurrencyCode())) {
                                                if (curr.getCountryName()!=null && !curr.getCountryName().equals("")){
                                                        Country country=DbUtil.getCountryByName(curr.getCountryName());
                                                        if (country!=null){
                                                                crForm.setCountryIso(country.getIso());
                                                        }else{
                                                                crForm.setCountryIso("-1");
                                                                }
                                                }else{
                                                        crForm.setCountryIso("-1");
                                                }
                                                crForm.setCountryName(curr.getCountryName());
                                                crForm.setCurrencyName(curr.getCurrencyName());
                                                crForm.setId(curr.getAmpCurrencyId());
                                                break;
                                        }
                                }
                        } else {
                                crForm.setId(new Long(-1));
                                crForm.setCountryName(null);
                                crForm.setCountryIso("-1");
                                crForm.setCurrencyCode(null);
                                crForm.setCurrencyName(null);
                                crForm.setExchangeRate(null);
                                crForm.setExchangeRateDate(null);
                        }
                        if (crForm.getCountries() == null ||
                                        crForm.getCountries().size() < 1) {
                            Collection<CountryBean> countries = org.digijava.module.aim.util.DbUtil.getTranlatedCountries(request);
                            crForm.setCountries(countries);
                        }

                } else if(crForm.getCurrencyCode()!=null){
                     AmpCurrency curr=CurrencyUtil.getCurrencyByCode(crForm.getCurrencyCode());
                     if(curr==null){
                         curr = new AmpCurrency();
                         if (crForm.getCountryIso() != null && !crForm.getCountryIso().equals("-1")) {
                             Country cn=DbUtil.getDgCountry(crForm.getCountryIso());
                             if(cn!=null){
                                 curr.setCountryId(cn);
                                 String countryName = cn.getCountryName();
                                 curr.setCountryName(countryName);
                             }
                         } else {
                             if (crForm.getCountryIso().equals("-1") && (crForm.getCountryName() == null || crForm.getCountryName().equals(""))) {
                                 curr.setCountryName(null);
                             } else {
                                 curr.setCountryName(crForm.getCountryName());
                             }
                         }
                         curr.setCurrencyCode(crForm.getCurrencyCode());
                         curr.setCurrencyName(crForm.getCurrencyName());
                         curr.setAmpCurrencyId(crForm.getId());
                         curr.setActiveFlag(new Integer(1));
                         AmpCurrencyRate cRate = new AmpCurrencyRate();
                         if (crForm.getExchangeRate() != null &&
                             crForm.getExchangeRateDate() != null &&
                             crForm.getExchangeRateDate().trim().length() > 0) {
                             cRate.setExchangeRate(crForm.getExchangeRate());
                             cRate.setExchangeRateDate(
                                 DateConversion.getDate(crForm.getExchangeRateDate()));
                             cRate.setToCurrencyCode(crForm.getCurrencyCode());

                         }
                         CurrencyUtil.saveCurrency(curr, cRate);
                         crForm.setCountryName(null);
                         crForm.setCountryIso("-1");
                         crForm.setCurrencyCode(null);
                         crForm.setCurrencyName(null);
                         crForm.setExchangeRate(null);
                         crForm.setExchangeRateDate(null);
                         crForm.setAllCurrencies(null);
                         crForm.setDoAction(null);
                         crForm.setId(null);
                         return mapping.findForward("curManager");
                     }else{
                         return mapping.findForward("curManager");
                     }
                }
                } catch (Exception e) {
                        e.printStackTrace(System.out);
                }
                return mapping.findForward("forward");
        }

}

