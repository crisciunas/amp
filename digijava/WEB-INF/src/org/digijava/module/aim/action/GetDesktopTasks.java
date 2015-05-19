package org.digijava.module.aim.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.actions.TilesAction;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.util.DbUtil;

public class GetDesktopTasks extends TilesAction {

	public ActionForward execute(ComponentContext context,
			ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute(Constants.MY_TASKS) == null) {
			TeamMember tm = (TeamMember) session.getAttribute(Constants.CURRENT_MEMBER);
			if (tm != null && tm.getTeamHead() == true) {
		 		Collection myTaskColl = DbUtil.getCreatedOrEditedActivities(tm.getTeamId());
				session.setAttribute(Constants.MY_TASKS,myTaskColl);				
			}
		}
		return null;
	}
}