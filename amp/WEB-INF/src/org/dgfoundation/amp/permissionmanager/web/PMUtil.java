/**
 * 
 */
package org.dgfoundation.amp.permissionmanager.web;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.model.IModel;
import org.dgfoundation.amp.permissionmanager.components.features.models.AmpPMGateWrapper;
import org.digijava.kernel.exception.DgException;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.gateperm.core.CompositePermission;
import org.digijava.module.gateperm.core.GatePermConst;
import org.digijava.module.gateperm.core.GatePermission;
import org.digijava.module.gateperm.core.Permission;
import org.digijava.module.gateperm.core.PermissionMap;
import org.digijava.module.gateperm.util.PermissionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author dan
 *
 */
public final class PMUtil {

	public static void setGlobalPermission(Class globalPermissionMapForPermissibleClass, Permission permission,String simpleName) {
		// TODO Auto-generated method stub
		Session hs=null;
		try {
			hs = PermissionUtil.saveGlobalPermission(globalPermissionMapForPermissibleClass, permission.getId(), simpleName);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DgException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(hs!=null){
			//pf.setPermissionId(new Long(0));
			try {
				PersistenceManager.releaseSession(hs);
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	
	
	public static void savePermission(IModel<CompositePermission> cpModel, Set<AmpPMGateWrapper> gatesSet) throws DgException {
		// TODO Auto-generated method stub
		Session session = PersistenceManager.getRequestDBSession();
		PermissionMap permissionMap=new PermissionMap(); 
		permissionMap.setPermissibleCategory(null);
		permissionMap.setObjectIdentifier(null);
		
		CompositePermission cp=new CompositePermission(true);
		cp.setDescription("This permission was created using the PM UI by admin user");
		cp.setDedicated(false);
		cp.setName(cpModel.getObject().getName());
		
		for (AmpPMGateWrapper ampPMGateWrapper : gatesSet) {
			initializeAndSaveGatePermission(session,cpModel,ampPMGateWrapper);
		}
		
		session.save(cp);
		
		permissionMap.setPermission(cp);
		
		session.save(permissionMap);
		
	}
	public static void initializeAndSaveGatePermission(Session session,IModel<CompositePermission> cpModel, AmpPMGateWrapper ampPMGateWrapper) throws HibernateException {
		initializeAndSaveGatePermission(session, cpModel.getObject(), cpModel.getObject().getName()+" - "+ampPMGateWrapper.getName(), 
				ampPMGateWrapper.getParameter(), ampPMGateWrapper.getClass(),ampPMGateWrapper.getEditFlag()?"on":"off",
						ampPMGateWrapper.getReadFlag()?"on":"off", "This permission has been generated by the Permission Manager UI");
	}
	
	public static void initializeAndSaveGatePermission(Session session,CompositePermission cp,String permissionName,String parameter, Class gate,String readFlag,String editFlag, String description) throws HibernateException {
		GatePermission baGate=new GatePermission(true);
		baGate.setName(permissionName);
		baGate.setDescription(description);
		baGate.getGateParameters().add(parameter);
		baGate.setGateTypeName(gate.getName());
		HashSet baActions=new HashSet();
		if("on".equals(editFlag)) baActions.add(GatePermConst.Actions.EDIT);
		if("on".equals(readFlag)) baActions.add(GatePermConst.Actions.VIEW);
		baGate.setActions(baActions);
		if(baGate.getActions().size()>0) { 
			session.save(baGate);
			cp.getPermissions().add(baGate);
		}
	}
	

}
