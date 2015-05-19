/**
 * This file is part of DiGi project (www.digijava.org).
 * DiGi is a multi-site portal system written in Java/J2EE.
 *
 * Copyright (C) 2002-2007 Development Gateway Foundation, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

package org.digijava.module.admin.action;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.Constants;
import org.digijava.kernel.request.Site;
import org.digijava.kernel.request.SiteDomain;
import org.digijava.module.admin.form.CommonInstancesForm;
import org.digijava.kernel.util.DgUtil;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

public class DeleteCommonInstance
      extends Action {

    public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 javax.servlet.http.HttpServletRequest request,
				 javax.servlet.http.HttpServletResponse
				 response) throws java.lang.Exception {
	if (!DgUtil.isModuleInstanceAdministrator(request)) {
	    return new ActionForward("/admin/index", true);
	}

	CommonInstancesForm formBean = (CommonInstancesForm) form;

	int instanceIndex = Integer.parseInt(request.getParameter("index"));

	CommonInstancesForm.CommonInstanceInfo info = (CommonInstancesForm.
	      CommonInstanceInfo) formBean.getCommonInstances().get(
	      instanceIndex);

	ActionErrors errors = new ActionErrors();
	if ( (info.getModule().equals("admin") &&
	      info.getInstance().equals("default")) ||
	    (info.getModule().equals("um") && info.getInstance().equals("user"))) {

	    Object[] param = {
		  info.getModule(), info.getInstance()};

	    errors.add(null,
		       new ActionError("error.admin.blockRemove", param));
	}
	if (!errors.isEmpty()) {
	    saveErrors(request, errors);
	    return mapping.findForward("error");
	}
	else {
	    formBean.getCommonInstances().remove(instanceIndex);
	}

	return mapping.findForward("forward");
    }
}