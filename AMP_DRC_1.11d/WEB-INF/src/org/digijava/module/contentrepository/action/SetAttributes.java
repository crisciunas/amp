/**
 * 
 */
package org.digijava.module.contentrepository.action;

import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.contentrepository.dbentity.CrDocumentNodeAttributes;
import org.digijava.module.contentrepository.exception.NoVersionsFoundException;
import org.digijava.module.contentrepository.form.SetAttributesForm;
import org.digijava.module.contentrepository.helper.CrConstants;
import org.digijava.module.contentrepository.util.DocumentManagerUtil;

/**
 * @author Alex Gartner
 *
 */
public class SetAttributes extends Action {
	private static Logger logger				= Logger.getLogger( CrDocumentNodeAttributes.class );
	private SetAttributesForm	myForm	= null;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws java.lang.Exception
	{
		myForm		= (SetAttributesForm) form;
		
		if ( myForm.getAction() != null && myForm.getAction().equals(CrConstants.MAKE_PUBLIC) ) 
				makePublic( myForm.getUuid(), request);
		if ( myForm.getAction() != null && myForm.getAction().equals(CrConstants.UNPUBLISH) ) 
				unpublish( myForm.getUuid() );
		return null;
	}
	
	private void makePublic(String uuid, HttpServletRequest request) {
		Session hbSession							= null;
		try {
			hbSession								= PersistenceManager.getSession();
			boolean shouldSaveObject				= false;
			
			CrDocumentNodeAttributes docAttributes	= null;
			try{
				docAttributes						= (CrDocumentNodeAttributes)hbSession.load(CrDocumentNodeAttributes.class, uuid);
			}
			catch (Exception e) {
				logger.debug("CrDocumentNodeAttribute object with uuid " + uuid + " was not found in db.");
			}
			if (docAttributes == null) {
				docAttributes						= new CrDocumentNodeAttributes();
				docAttributes.setUuid(uuid);
				shouldSaveObject					= true;
			} 
			
			Node lastVersionNode					= DocumentManagerUtil.getNodeOfLastVersion(uuid, request);
			
			docAttributes.setPublicDocument(true);
			docAttributes.setPublicVersionUUID(lastVersionNode.getUUID());
			
			if (shouldSaveObject) {
				hbSession.save( docAttributes );
			}
			hbSession.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				PersistenceManager.releaseSession(hbSession);
			} catch (Exception ex2) {
				logger.error("releaseSession() failed :" + ex2);
			}
		}
	}
	
	public static void unpublish(String uuid) {
		Session hbSession							= null;
		try {
			hbSession			= PersistenceManager.getSession();
			String queryStr		= "SELECT a FROM " + CrDocumentNodeAttributes.class.getName() + " a WHERE uuid=:uuid" ;
			Query query			= hbSession.createQuery(queryStr);
			
			query.setString("uuid", uuid);
			
			CrDocumentNodeAttributes docNodeAtt			= (CrDocumentNodeAttributes)query.uniqueResult();
			if (docNodeAtt != null )
				hbSession.delete(docNodeAtt);
			hbSession.flush();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				PersistenceManager.releaseSession(hbSession);
			} catch (Exception ex2) {
				logger.error("releaseSession() failed :" + ex2);
			}
		}
	}
}