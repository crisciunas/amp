/**
 * 
 */
package org.dgfoundation.amp.permissionmanager.web.pages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.dgfoundation.amp.permissionmanager.components.features.sections.AmpPMManageGlobalPermissionsSectionFeaturePanel;
import org.dgfoundation.amp.permissionmanager.components.features.sections.AmpPMManageUsersSectionFeature;
import org.dgfoundation.amp.permissionmanager.components.features.sections.AmpPMManageWorkspacesSectionFeature;
import org.dgfoundation.amp.permissionmanager.components.features.sections.AmpPMSectionFeaturePanel;
import org.digijava.kernel.user.User;
import org.digijava.module.aim.dbentity.AmpTeam;
import org.digijava.module.um.exception.UMException;

/**
 * @author dan
 *
 */
public class PermissionManager extends AmpPMHeaderFooter {

	/**
	 * 
	 */
	public PermissionManager() throws Exception{
		// TODO Auto-generated constructor stub
		super();
		
		//final IModel<Set<User>> usersModel = new AmpPMUserModel();
		
		//managing users
		Set<User> s = new TreeSet<User>();
		List<User> users = new ArrayList<User>();
		try {
			users = org.digijava.module.um.util.DbUtil.getList(User.class.getName(),"firstNames");
		} catch (UMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.addAll(users);
		final IModel<Set<User>> usersModel = new Model((Serializable)s);
		add(new AmpPMManageUsersSectionFeature("manageUsers", "Manage Users", usersModel));
		
		
		//managing workspaces
		Set<AmpTeam> w = new TreeSet<AmpTeam>();
		List<AmpTeam> teams = new ArrayList<AmpTeam>();
		try {
			teams = org.digijava.module.um.util.DbUtil.getList(AmpTeam.class.getName(),"name");
		} catch (UMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w.addAll(teams);
		final IModel<Set<AmpTeam>> teamsModel = new Model((Serializable)w);
		add(new AmpPMManageWorkspacesSectionFeature("manageWorkspaces", teamsModel, "Manage Workspaces", false));
		
		
		//add(new AmpPMSectionFeaturePanel("manageWorkspaces", "Manage Workspaces"));
		//add(new AmpPMSectionFeaturePanel("manageGlobalPermissions", "Manage Global Permissions"));
		add(new AmpPMManageGlobalPermissionsSectionFeaturePanel("manageGlobalPermissions", teamsModel, "Manage Global Permissions", false));
		
		add(new AmpPMSectionFeaturePanel("manageFieldLevelPermissions", "Manage Field Level Permissions"));
	}

	/**
	 * @param model
	 */
	public PermissionManager(IModel<?> model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pageMap
	 */
	public PermissionManager(IPageMap pageMap) {
		super(pageMap);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parameters
	 */
	public PermissionManager(PageParameters parameters) {
		super(parameters);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pageMap
	 * @param model
	 */
	public PermissionManager(IPageMap pageMap, IModel<?> model) {
		super(pageMap, model);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pageMap
	 * @param parameters
	 */
	public PermissionManager(IPageMap pageMap, PageParameters parameters) {
		super(pageMap, parameters);
		// TODO Auto-generated constructor stub
	}

}
