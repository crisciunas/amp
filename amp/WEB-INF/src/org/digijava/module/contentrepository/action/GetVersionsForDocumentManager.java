/**
 * 
 */
package org.digijava.module.contentrepository.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.version.Version;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.module.contentrepository.action.DocumentManager.DocumentData;
import org.digijava.module.contentrepository.dbentity.CrDocumentNodeAttributes;
import org.digijava.module.contentrepository.form.DocumentManagerForm;
import org.digijava.module.contentrepository.util.DocumentManagerUtil;


/**
 * @author Alex Gartner
 *
 */
public class GetVersionsForDocumentManager extends Action {
	
	HttpServletRequest myRequest;
	DocumentManagerForm myForm;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws java.lang.Exception {
		
		myForm							= (DocumentManagerForm) form;
		myRequest						= request;
		
		ArrayList<DocumentData> docs	= new ArrayList<DocumentData>();
		myForm.setOtherDocuments(docs);
		
		String nodeUUID		= request.getParameter("uuid"); 
		Collection versions	= DocumentManagerUtil.getVersions(nodeUUID, request, false);
		DocumentManager dm	= new DocumentManager();
		if (versions != null) {
			int counter	= 0;
			Iterator iter	= versions.iterator();
			while ( iter.hasNext() ) {
				Version v			= (Version)iter.next();
				//String testVersionUUID	= v.getUUID();
				NodeIterator nIter	= v.getNodes();
				while (nIter.hasNext()) {
					DocumentData docData	= dm.new DocumentData();
					Node n					= nIter.nextNode();
					String testUUID			= n.getUUID();
					System.out.println(testUUID);
					
					this.generateDocumentData(n, counter+1, docData);
					docData.computeIconPath( false );
					docs.add( docData );
				}
				counter++;
			}
		}
		return mapping.findForward("forward");
	}
	
	private void generateDocumentData (Node n, float verNum, DocumentData docData) 
					throws UnsupportedRepositoryOperationException, RepositoryException {
		String name 		= "";
		String title 		= "";
		String date			= "";
		String description	= "";
		String contentType	= "";
		String notes		= "";
		
		float version		= 0;
		
		try{
			name		= n.getProperty("ampdoc:name").getString();
			docData.setName( name );
		}
		catch(Exception E) {
			return;
		}
		try{
			title		= n.getProperty("ampdoc:title").getString();
			docData.setTitle( title );
		}
		catch(Exception E) {
			;
		}
		try{
			description	= n.getProperty("ampdoc:description").getString();
			docData.setDescription( description );
		}
		catch(Exception E) {
			;
		}
		try{
			contentType	= n.getProperty("ampdoc:contentType").getString();
			docData.setContentType( contentType );
		}
		catch(Exception E) {
			;
		}
		try{
			notes		= n.getProperty("ampdoc:notes").getString();
			docData.setNotes( notes );
		}
		catch(Exception E) {
			;
		}
		try{
			date	= DocumentManagerUtil.calendarToString( n.getProperty("ampdoc:addingDate").getDate() );
			docData.setCalendar( date );
		}
		catch(Exception E) {
			;
		}
		try{
			version	= (float) n.getProperty("ampdoc:versionNumber").getDouble();
			docData.setVersionNumber( version );
		}
		catch(Exception E) {
			docData.setVersionNumber( verNum );
		}
		
		HashMap<String,CrDocumentNodeAttributes> uuidMapVer		= CrDocumentNodeAttributes.getPublicDocumentsMap(true);
		String nodeUUID											= n.getUUID();
		if ( uuidMapVer.containsKey(nodeUUID) ) {
			docData.setIsPublic(true);
		}

	}
}

