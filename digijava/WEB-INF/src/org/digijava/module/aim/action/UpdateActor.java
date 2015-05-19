/*
 * UpdateActor.java
 * Created : 07-Sep-2005
 */

package org.digijava.module.aim.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.module.aim.dbentity.AmpActor;
import org.digijava.module.aim.form.EditActivityForm;
import org.digijava.module.aim.helper.Issues;
import org.digijava.module.aim.helper.Measures;

public class UpdateActor extends Action {
	
	private static Logger logger = Logger.getLogger(UpdateActor.class);
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) {
		
		EditActivityForm eaForm = (EditActivityForm) form;
		if (eaForm.getIssueId() != null &&
				eaForm.getIssueId().longValue() > 0) {
			logger.debug("The issue id is " + eaForm.getIssueId());
			Issues issue = new Issues();
			issue.setId(eaForm.getIssueId());
			int index = eaForm.getIssues().indexOf(issue);
			issue = (Issues) eaForm.getIssues().get(index);			
			if (eaForm.getMeasureId() != null &&
					eaForm.getMeasureId().longValue() > 0) {
				logger.debug("The measure id is " + eaForm.getMeasureId());
				Measures measure = new Measures();
				measure.setId(eaForm.getMeasureId());
				int mIndex = issue.getMeasures().indexOf(measure);
				measure = (Measures) issue.getMeasures().get(mIndex);
				
				AmpActor actor = new AmpActor();
				if (eaForm.getActorId() != null && 
						eaForm.getActorId().longValue() > -1) {
					actor.setAmpActorId(eaForm.getActorId());
				} else {
					actor.setAmpActorId(new Long(System.currentTimeMillis()));
				}
				
				if (measure.getActors() == null) {
					measure.setActors(new ArrayList());
					actor.setName(eaForm.getActor());
					measure.getActors().add(actor);
					logger.debug("Actors empty. Adding ....");
				} else {
					int aIndex = measure.getActors().indexOf(actor);
					if (aIndex > -1) {
						actor = (AmpActor) measure.getActors().get(aIndex);
						actor.setName(eaForm.getActor());
						measure.getActors().set(aIndex,actor);
						logger.debug("Actor already existing. Updating .....");
					} else {
						actor.setName(eaForm.getActor());
						measure.getActors().add(actor);
						logger.debug("Actor not present. Adding ....");
					}
				}
				issue.getMeasures().set(mIndex,measure);
				logger.debug("measure set for index at pos " + mIndex);
			}
			eaForm.getIssues().set(index,issue);
			logger.debug("issues set for formbean at pos " + index);
			eaForm.setActor(null);
		}
		return mapping.findForward("forward");
	}
}