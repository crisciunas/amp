

package org.digijava.module.aim.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.module.aim.dbentity.AmpIndicator;
import org.digijava.module.aim.dbentity.AmpSector;
import org.digijava.module.aim.form.ViewIndicatorsForm;
import org.digijava.module.aim.helper.IndicatorsBean;
import org.digijava.module.aim.util.IndicatorUtil;
import org.digijava.module.aim.util.ProgramUtil;
import org.digijava.module.aim.util.SectorUtil;

public class ViewIndicators
    extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws java.lang.Exception {
   
    	List<IndicatorsBean> allInds = new ArrayList<IndicatorsBean>();
    	Collection sectorsName = new ArrayList();
      
        ViewIndicatorsForm allIndForm = (ViewIndicatorsForm) form;
        allIndForm.setThemeName("");
        
        String types = request.getParameter("indicator");
        String id = request.getParameter("indicatorId");
        String view = request.getParameter("sector");
    
        if(view!=null){
        if(view.equalsIgnoreCase("viewall")){
        	allIndForm.setSectorId(new Long(-1));
        	allIndForm.setKeyword(null);
        	
         }
        }
  
        if (types != null && id != null){
            if(types.equals("delete")){
  
            	IndicatorUtil.deleteIndicator(new Long(id));
            	
             }
            }
        
        List<AmpIndicator>  searchResult = IndicatorUtil.getAllIndicators();
        Collection allSectors = SectorUtil.getAllParentSectors();
      
      
       allIndForm.setSectors(allSectors);
       allInds.clear();
       for(AmpIndicator indicator : searchResult){
        	
        	
        	IndicatorsBean indbean = new IndicatorsBean(indicator);
        	
        	indbean.setName(indicator.getName());
         	indbean.setType("0");
         	indbean.setCategory(Integer.valueOf(indbean.getCategory()));
            indbean.setSectorName("Z");
         	
         	
         	Collection indSector=indbean.getSector();
         	
         	
         	if(indSector != null){
				for(Iterator sectItr=indSector.iterator();sectItr.hasNext();){
        	        AmpSector sect=(AmpSector)sectItr.next();
        	        indbean.getSectorNames().add(sect.getName());
        	        indbean.setSectorName(sect.getName());
        			
        			
				 }
			  }
          	
         	boolean addFlag = false;
         	  if(allIndForm.getSectorId()!=-1){
  				Collection indSectors=indbean.getSector();
  				if(indSectors!=null){
   					for(Iterator sectItr=indSectors.iterator();sectItr.hasNext();){
  						AmpSector sect=(AmpSector)sectItr.next();
  						
  						if(sect.getAmpSectorId().equals(allIndForm.getSectorId())){
  							addFlag = true;
  							break;
  						}
  					}
  				}
  			}else{
  				addFlag=true;
  			}
         	if (addFlag) {
				if (allIndForm.getKeyword() != ""
						&& allIndForm.getKeyword() != null) {
					if (indbean.getName().toLowerCase().indexOf(
							allIndForm.getKeyword().toLowerCase()) > -1) {
						allInds.add(indbean);
					}
				} else {
					allInds.add(indbean);
				}
			}
		}
         	 
 
        switch(allIndForm.getSortBy()) {
            case 0: {
                Collections.sort(allInds, new ProgramUtil.HelperAllIndicatorBeanNameComparator());
                break;
            }
            case 1: {
                Collections.sort(allInds, new ProgramUtil.HelperAllIndicatorBeanSectorComparator());
                break;
            }
////            case 2:{
////                Collections.sort(allInds, new ProgramUtil.HelperAllIndicatorBeanTypeComparator());
////                break;
////            }
            default:{
                Collections.sort(allInds, new ProgramUtil.HelperAllIndicatorBeanNameComparator());
            }
        }
        
        
        allIndForm.setAllIndicators(allInds); 
 
        return mapping.findForward("forward");
    }
  }
