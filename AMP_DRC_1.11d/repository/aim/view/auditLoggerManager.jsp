<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/taglib/struts-bean" prefix="bean"%>
<%@ taglib uri="/taglib/struts-logic" prefix="logic"%>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/taglib/struts-html" prefix="html"%>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>
<%@ taglib uri="/taglib/jstl-functions" prefix="fn" %>

<%@page import="org.digijava.module.aim.services.auditcleaner.AuditCleaner"%>
<style>
.contentbox_border{
	border:1px solid #666666;
	width:100%;	
	background-color: #f4f4f2;
	padding: 20 0 20 0;
}
</style>
<style>
.link{
	text-decoration: none;
	font-size: 8pt; font-family: Tahoma;
}
</style>

<style>

.tableEven {
	background-color:#dbe5f1;
	font-size:8pt;
	padding:2px;
}

.tableOdd {
	background-color:#FFFFFF;
	font-size:8pt;!important
	padding:2px;
}
 
.Hovered {
	background-color:#a5bcf2;
}

</style>
<script language="javascript">

function submitClean(){
 <digi:context name="cleanurl" property="context/module/moduleinstance/auditLoggerManager.do?clean=true" />
 		document.aimAuditLoggerManagerForm.action = "<%=cleanurl%>";
		document.aimAuditLoggerManagerForm.target = "_self";
		document.aimAuditLoggerManagerForm.submit();
}

function show_hide(divID){
	var divArea = document.getElementById(divID)
	
	if(divArea.style.visibility =='hidden'){
		divArea.style.visibility = 'visible'
	}else{
		divArea.style.visibility = 'hidden'
	}
}


function showUser(email){
	if (email != ""){
		<digi:context name="information" property="context/module/moduleinstance/userProfile.do" />
		openURLinWindow("<%= information %>~edit=true~email="+email,480, 350);
	}
	else{
		var trasnlation = "<digi:trn key='aim:userblankmail'>The user does not have a valid email address</digi:trn>";
		alert (trasnlation);
	}
}

function setStripsTable(tableId, classOdd, classEven) {
	var tableElement = document.getElementById(tableId);
	rows = tableElement.getElementsByTagName('tr');
	for(var i = 0, n = rows.length; i < n; ++i) {
		if(i%2 == 0)
			rows[i].className = classEven;
		else
			rows[i].className = classOdd;
	}
	rows = null;
}
function setHoveredTable(tableId, hasHeaders) {

	var tableElement = document.getElementById(tableId);
	if(tableElement){
    	var className = 'Hovered',
        pattern   = new RegExp('(^|\\s+)' + className + '(\\s+|$)'),
        rows      = tableElement.getElementsByTagName('tr');

		for(var i = 0, n = rows.length; i < n; ++i) {
			rows[i].onmouseover = function() {
				this.className += ' ' + className;
			};
			rows[i].onmouseout = function() {
				this.className = this.className.replace(pattern, ' ');

			};
		}
		rows = null;
	}
}
function toggleSettings(){
	var currentDisplaySettings = document.getElementById('currentDisplaySettings');
	var displaySettingsButton = document.getElementById('displaySettingsButton');
	if(currentDisplaySettings.style.display == "block"){
		currentDisplaySettings.style.display = "none";
		displaySettingsButton.innerHTML = '<digi:trn key="aim:Showcleanupoptions">Show cleanup options</digi:trn>'+ ' &gt;&gt;';
	}
	else
	{
		currentDisplaySettings.style.display = "block";
		displaySettingsButton.innerHTML = '<digi:trn key="aim:Hidecleanupoptions">Hide cleanup options</digi:trn>'+ ' &lt;&lt;';
	}
}
</script>
<digi:instance property="aimAuditLoggerManagerForm" />
<!--  AMP Admin Logo -->
<jsp:include page="teamPagesHeader.jsp" flush="true" />
<!-- End of Logo -->
<digi:form action="/auditLoggerManager.do" method="post">
<table cellPadding=0 cellSpacing=0 width=1000px>
	<tr>
		<td class=r-dotted-lg width=14>&nbsp;</td>
		<td align=left vAlign=top width=750px>
			<table cellPadding=5 cellSpacing=0 width="100%" border=0>
				<tr>
					<!-- Start Navigation -->
					<td height=33><span class=crumb>
						<c:set var="translation">
							<digi:trn key="aim:clickToViewAdmin">Click here to goto Admin Home</digi:trn>
						</c:set>
						<digi:link href="/admin.do" styleClass="comment" title="${translation}" >
						<digi:trn key="aim:AmpAdminHome">
							Admin Home						
						</digi:trn>
						</digi:link>&nbsp;&gt;&nbsp;
						<digi:trn key="aim:AuditLoggerManager">
							Audit Logger Manager						
						</digi:trn>					
						</td>
					<!-- End navigation -->
				</tr>
				<tr>
					<td height=16 vAlign=center width=758>
						<span class=subtitle-blue>
						<digi:trn key="aim:AuditLoggerManager">
							Audit Logger Manager						
						</digi:trn>
					  </span>				  
					</td>
				</tr>
				<tr bgcolor="#edf5ff">
				  <td valign="top">
				  <div style="padding: 3px; width: 100%; background-color: rgb(204, 219, 255); font-size: 8pt;">
				  <span style="cursor:pointer; font-style: italic;float:left;color: red;">
				  	<%if(AuditCleaner.getInstance().isRunning()){%>
				  	<%if (AuditCleaner.getInstance().getRemainingdays()!= null){%>
				  		<digi:trn key="aim:auditautocleanup">The automatic cleanup of the audit trail will occur in</digi:trn> 
				  		<%=AuditCleaner.getInstance().getRemainingdays()%>
				  		<digi:trn key="aim:days">Days</digi:trn>
				  	<%}
				  	}%>
				  </span>
				  <span style="cursor:pointer;font-style: italic;float:right;" onClick="toggleSettings();" id="displaySettingsButton"><digi:trn key="aim:Showcleanupoptions">Show cleanup options</digi:trn> &gt;&gt;</span>
                                &nbsp;<br>
								<div style="display:none;background-color:#FFFFCC;padding:2px" id="currentDisplaySettings" >
                                 <table cellpadding="2" cellspacing="2" border="0" width="250px">
                                 <tr>
                                 	<td align="right">
                                 	<strong>Selected Action:&nbsp;&nbsp;</strong>
                                	</td>
                                	<td>
                                 	<html:select property="useraction" styleClass="inp-text">
                                 		<html:option value="delete">Delete</html:option>
                                 		<html:option value="export">Export</html:option>
                                 	</html:select>
                                 	</td>
                                 </tr>
                                 <tr>
									<td align="right">
                                 	<strong>Selected Period:&nbsp;&nbsp; </strong>
                                 	</td>
                                 	<td align="left" height="30px">
                                 	<html:select property="frecuency" styleClass="inp-text">
                                 			<html:option value="30">30 <digi:trn key="aim:days">Days</digi:trn></html:option>
                                 			<html:option value="60">60 <digi:trn key="aim:days">Days</digi:trn></html:option>
                                 			<html:option value="90">90 <digi:trn key="aim:days">Days</digi:trn></html:option>
                                 	</html:select>
                                 	</td>
                                 </tr>
                                 <tr>
                                	<td align="right">
                                 		<input  class="dr-menu" type="button" onclick="submitClean()" value="OK">
                                 		&nbsp;
									</td>
									<td align="left">
										<input class="dr-menu" type="button" value="Cancel">
									</td>
                                 </tr>
                                 </table>
                                 </div>
                                </div>                        
                            	</div>
                          		<br>
				<table width="100%" height="100%" cellPadding=0 cellSpacing=0 bgColor=#ffffff>
				<tr>
						<td colspan="2" valign="top">
						<div>
						<div align="center">
						<table width="100%" height="100%" border="0" align=center cellPadding=0 cellSpacing=0  id="dataTable">
							<tr>
								<td width="280" height="22" align="center" valign="center" bgcolor="#999999"
									style="color: black"><c:if 
									test="${aimAuditLoggerManagerForm.sortBy!='nameasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=nameasc">
										<b><digi:trn key="aim:name">Name</digi:trn></b>									
									</digi:link>
								</c:if> <c:if
									test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='nameasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=namedesc">
										<b><digi:trn key="aim:name">Name</digi:trn></b>									
									</digi:link>
							  </c:if></td>
								<td valign="center" align="center" bgcolor="#999999" style="color: black" width="150">
								<c:if test="${aimAuditLoggerManagerForm.sortBy!='typeasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=typeasc">
										<b><digi:trn key="aim:objectType">Object Type</digi:trn></b>									
									</digi:link>
								</c:if>
								<c:if test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='typeasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=typedesc">
										<b><digi:trn key="aim:objectType">Object Type</digi:trn></b>									
									</digi:link>
								</c:if>
								</td>
								<td valign="center" align="center" bgcolor="#999999"
									style="color: black"><c:if
									test="${aimAuditLoggerManagerForm.sortBy!='teamasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=teamasc">
										<b><digi:trn key="aim:teamName">Team Name</digi:trn></b>									
									</digi:link>
								</c:if> <c:if
									test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='teamasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=teamdesc">
										<b><digi:trn key="aim:teamName">Team Name</digi:trn></b>									
									</digi:link>
								</c:if></td>
								<td align="center" valign="center" bgcolor="#999999"
									style="color: black"><c:if
									test="${aimAuditLoggerManagerForm.sortBy!='authorasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=authorasc">
										<b><digi:trn key="aim:authorName">Author Name</digi:trn></b>									
									</digi:link>
								</c:if> <c:if
									test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='authorasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=authordesc">
										<b><digi:trn key="aim:authorName">Author Name</digi:trn></b>									
									</digi:link>
								</c:if></td>
								<td width="100" align="center" valign="center" bgcolor="#999999"
									style="color: black"><c:if
									test="${aimAuditLoggerManagerForm.sortBy!='creationdateasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=creationdateasc">
										<b><digi:trn key="aim:creationDateLogger">Creation Date</digi:trn></b>									
									</digi:link>
								</c:if> <c:if
									test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='creationdateasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=creationdatedesc">
										<b><digi:trn key="aim:creationDateLogger">Creation Date</digi:trn></b>									
									</digi:link>
							  </c:if></td>
								<td width="208" align="center" valign="center" bgcolor="#999999"
									style="color: black"><c:if
									test="${aimAuditLoggerManagerForm.sortBy!='editorasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=editorasc">
										<b><digi:trn key="aim:editorName">Editor Name</digi:trn></b>									
									</digi:link>
								</c:if> <c:if
									test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='editorasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=editordesc">
										<b><digi:trn key="aim:editorName">Editor Name</digi:trn></b>									
									</digi:link>
								</c:if></td>
								<td align="center" valign="center" bgcolor="#999999"
									style="color: black"><c:if
									test="${aimAuditLoggerManagerForm.sortBy!='changedateasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=changedateasc">
										<b><digi:trn key="aim:changeDate">Change Date</digi:trn></b>									
									</digi:link>
								</c:if> <c:if
									test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='changedateasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=changedatedesc">
										<b><digi:trn key="aim:changeDate">Change Date</digi:trn></b>									
									</digi:link>
								</c:if></td>
								<td width="129" align="center" valign="center" bgcolor="#999999"style="color: black">
								<c:if test="${aimAuditLoggerManagerForm.sortBy!='actionasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=actionasc">
										<b><digi:trn key="aim:action">Action</digi:trn></b>									
									</digi:link>
								</c:if> <c:if
									test="${not empty aimAuditLoggerManagerForm.sortBy && aimAuditLoggerManagerForm.sortBy=='actionasc'}">
									<digi:link style="color:black" href="/auditLoggerManager.do?sortBy=actiondesc">
										<b><digi:trn key="aim:action">Action</digi:trn></b>									
									</digi:link>
								</c:if></td>
							</tr>
							<logic:iterate name="aimAuditLoggerManagerForm" property="logs"
								id="log" type="org.digijava.module.aim.dbentity.AmpAuditLogger">
								<tr>
								<td width="280" height="18" align="center" title="${log.objectName}">
								<c:if test="${fn:length(log.objectName) > 30}" >
									<c:out value="${fn:substring(log.objectName, 0, 30)}" />...
								</c:if>
								<c:if test="${fn:length(log.objectName) < 30}" >
									<bean:write name="log" property="objectName"/>
								</c:if>
								</td>
								<td align="center" width="150" title="${log.objectTypeTrimmed}">
									<digi:trn key="aim:ObjectType${log.objectTypeTrimmed}"><bean:write name="log" property="objectTypeTrimmed"/></digi:trn>									
								</td>
								<td align="center" width="100" title="${log.teamName}">
									<bean:write name="log" property="teamName"/>									
								</td>
								<td align="center" width="150">
									<a href="javascript:showUser('<bean:write name="log" property="authorEmail"/>')" style="text-decoration: none" title="${log.authorName}">
									<c:if test="${fn:length(log.objectName) > 8}" >
										<c:out value="${fn:substring(log.authorName, 0, 8)}" />...
									</c:if>
									<c:if test="${fn:length(log.objectName) < 8}" >
										 <bean:write name="log" property="authorName"/>
									</c:if>
									</a>								  
								</td>
								<td width="100" align="center" title="${log.loggedDate}">
									<bean:write name="log" property="sloggeddate"/>
								</td>
								
								<td width="100" align="center" title="${log.editorName}">
									<bean:write name="log" property="editorName" />								  
								</td>
								<td width="150" align="center" title="${log.modifyDate}">
									  <bean:write name="log" property="smodifydate"/>								  
								 </td>
									<td width="100" align="center">
										<logic:equal value="delete" property="action" name="log">
											<digi:trn key="admin:delete">Delete</digi:trn>
										</logic:equal> <logic:equal value="add" property="action" name="log">
											<digi:trn key="admin:add">Add</digi:trn>
										</logic:equal> <logic:equal value="update" property="action" name="log">
											<digi:trn key="admin:update">Update</digi:trn>
										</logic:equal>								  
									</td>
								</tr>
							</logic:iterate>
						</table>
						</div>
						</div>					
					</td>
				  </tr>
					<tr>
						<td align="left" valign="middle">
						<div style="cursor: pointer; font-family: Arial; text-align: left; text-decoration: none;">
						<br>
						<c:if test="${aimAuditLoggerManagerForm.currentPage > 1}">
							<jsp:useBean id="urlParamsFirst" type="java.util.Map" class="java.util.HashMap"/>
							<c:set target="${urlParamsFirst}" property="page" value="1"/>
							<c:set target="${urlParamsFirst}" property="sortBy" value="${aimAuditLoggerManagerForm.sortBy}" />
							<c:set var="translation">
								<digi:trn key="aim:firstpage">First Page</digi:trn>
							</c:set>
							<digi:link href="/auditLoggerManager.do" style="text-decoration=none" name="urlParamsFirst" title="${translation}"  >
								<span style="font-size: 8pt; font-family: Tahoma;">&lt;&lt;</span>							
							</digi:link>
							<jsp:useBean id="urlParamsPrevious" type="java.util.Map" class="java.util.HashMap"/>
							<c:set target="${urlParamsPrevious}" property="page" value="${aimAuditLoggerManagerForm.currentPage -1}"/>
							<c:set target="${urlParamsPrevious}" property="sortBy" value="${aimAuditLoggerManagerForm.sortBy}" />
							<c:set var="translation">
								<digi:trn key="aim:previouspage">Previous Page</digi:trn>
							</c:set>|
							<digi:link href="/auditLoggerManager.do" name="urlParamsPrevious" style="text-decoration=none" title="${translation}" >
								<digi:trn key="aim:previous">
									<span style="font-size: 8pt; font-family: Tahoma;">Previous</span>
								</digi:trn>&nbsp;	
							</digi:link>|
						</c:if>
					<c:set var="length" value="${aimAuditLoggerManagerForm.pagesToShow}"></c:set>
					<c:set var="start" value="${aimAuditLoggerManagerForm.offset}"/>
					<logic:iterate name="aimAuditLoggerManagerForm" property="pages" id="pages" type="java.lang.Integer" offset="${start}" length="${length}">	
						<jsp:useBean id="urlParams1" type="java.util.Map" class="java.util.HashMap"/>
						<c:set target="${urlParams1}" property="sortBy" value="${aimAuditLoggerManagerForm.sortBy}" />
						<c:set target="${urlParams1}" property="page"><%=pages%></c:set>
						<c:if test="${aimAuditLoggerManagerForm.currentPage == pages && aimAuditLoggerManagerForm.pagesSize > 1}">
							<font color="#FF0000"><%=pages%></font>
							|	
						</c:if>
						<c:if test="${aimAuditLoggerManagerForm.currentPage != pages && aimAuditLoggerManagerForm.pagesSize > 1}">
							<c:set var="translation">
								<digi:trn key="aim:clickToViewNextPage">Click here to go to Next Page</digi:trn>
							</c:set>
							<digi:link href="/auditLoggerManager.do" name="urlParams1" title="${translation}" >
								<%=pages%>							
							</digi:link>
							|
						</c:if>
						</logic:iterate>
						<c:if test="${aimAuditLoggerManagerForm.currentPage != aimAuditLoggerManagerForm.pagesSize}">
							<jsp:useBean id="urlParamsNext" type="java.util.Map" class="java.util.HashMap" />
							<c:set target="${urlParamsNext}" property="page" value="${aimAuditLoggerManagerForm.currentPage+1}"/>
							<c:set target="${urlParamsNext}" property="sortBy" value="${aimAuditLoggerManagerForm.sortBy}" />
							<c:set var="translation"> <digi:trn key="aim:nextpage">Next Page</digi:trn></c:set>
							<digi:link  href="/auditLoggerManager.do" style="text-decoration=none" name="urlParamsNext" title="${translation}">
								<digi:trn key="aim:next"><span style="font-size: 8pt; font-family: Tahoma;">Next</span></digi:trn>
							</digi:link>
							<jsp:useBean id="urlParamsLast" type="java.util.Map" class="java.util.HashMap" />|
							
						<c:if test="${aimAuditLoggerManagerForm.pagesSize > aimAuditLoggerManagerForm.pagesToShow}">
							<c:set target="${urlParamsLast}" property="page" value="${aimAuditLoggerManagerForm.pagesSize}" />
							<c:set target="${urlParamsLast}" property="sortBy" value="${aimAuditLoggerManagerForm.sortBy}" />
						</c:if>
						
						<c:if test="${aimAuditLoggerManagerForm.pagesSize < aimAuditLoggerManagerForm.pagesToShow}">
							<c:set target="${urlParamsLast}" property="sortBy" value="${aimAuditLoggerManagerForm.sortBy}" />
							<c:set target="${urlParamsLast}" property="page" value="${aimAuditLoggerManagerForm.pagesSize}" />
						</c:if>
						<c:set var="translation"> <digi:trn key="aim:lastpage">
							<span style="font-size: 8pt; font-family: Tahoma;">Last Page</span>
						</digi:trn>
						</c:set>
						<digi:link href="/auditLoggerManager.do" style="text-decoration=none" name="urlParamsLast" title="${translation}">
							<span style="font-size: 8pt; font-family: Tahoma;">&gt;&gt;</span>
						</digi:link> 
					</c:if>
					<c:out value="${aimAuditLoggerManagerForm.currentPage}"/>&nbsp; 
					<digi:trn key="aim:of">
						<span style="font-size: 8pt; font-family: Tahoma;">of</span>
					</digi:trn>&nbsp;
					<span style="font-size: 8pt; font-family: Tahoma;">
						<c:out value="${aimAuditLoggerManagerForm.pagesSize}"/>
					</span>					
						<a style="float: right; cursor: pointer;" onclick="window.scrollTo(0,0); return false">
							<digi:trn key="aim:backtotop">Back to Top</digi:trn>
					</span>
						<span style="font-size: 10pt; font-family: Tahoma;">↑</span></a>					
					</td>
					</div>
				</tr>
				</table>				
				</td>
 			</tr>
		</table>	
		</td>
	</tr>
</table>
<br>
<script language="javascript">
	setStripsTable("dataTable", "tableEven", "tableOdd");
	setHoveredTable("dataTable", false);
</script>
</digi:form>