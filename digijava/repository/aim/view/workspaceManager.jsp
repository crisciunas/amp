<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>


<script langauage="JavaScript">
	function onDelete() {
		var flag = confirm("Delete this workspace?");
		return flag;
	}
</script>

<digi:instance property="aimWorkspaceForm" />
<digi:context name="digiContext" property="context" />

<!--  AMP Admin Logo -->
<jsp:include page="teamPagesHeader.jsp" flush="true" />
<!-- End of Logo -->


<table bgColor=#ffffff cellPadding=0 cellSpacing=0 width=772>
	<tr>
		<td class=r-dotted-lg width=14>&nbsp;</td>
		<td align=left class=r-dotted-lg vAlign=top width=750>
			<table cellPadding=5 cellSpacing=0 width="100%" border=0>
				<tr>
					<!-- Start Navigation -->
					<td height=33><span class=crumb>
						<bean:define id="translation">
							<digi:trn key="aim:clickToViewAdmin">Click here to goto Admin Home</digi:trn>
						</bean:define>
						<digi:link href="/admin.do" styleClass="comment" title="<%=translation%>" >
						<digi:trn key="aim:AmpAdminHome">
						Admin Home
						</digi:trn>
						</digi:link>&nbsp;&gt;&nbsp;
						<digi:trn key="aim:workspaceManager">
						Workspace Manager
						</digi:trn>
					</td>
					<!-- End navigation -->
				</tr>
				<tr>
					<td height=16 vAlign=center width=571><span class=subtitle-blue>Workspace Manager</span>
					</td>
				</tr>
				<tr>
					<td height=16 vAlign=center width=571>
						<html:errors />
					</td>
				</tr>				
				<tr>
					<td noWrap width=100% vAlign="top">
					<table width="100%" cellspacing=1 cellSpacing=1 border=0>
					<tr><td noWrap width=600 vAlign="top">
						<table bgColor=#d7eafd cellPadding=1 cellSpacing=1 width="100%" valign="top">
							<tr bgColor=#ffffff>
								<td vAlign="top" width="100%">
									
									<table width="100%" cellspacing=1 cellpadding=1 valign=top align=left>	
										<tr><td bgColor=#d7eafd class=box-title height="20" align="center">
											<!-- Table title -->
											<digi:trn key="aim:teams">
												Teams
											</digi:trn>
											<!-- end table title -->										
										</td></tr>
										<tr><td>
											<table width="100%" cellspacing=1 cellpadding=4 valign=top align=left bgcolor="#d7eafd">
													<logic:empty name="aimWorkspaceForm" property="workspaces">
													<tr bgcolor="#ffffff">
														<td colspan="5" align="center"><b>
															<digi:trn key="aim:noTeams">
															No teams present
															</digi:trn>
														</b></td>
													</tr>
													</logic:empty>
													<logic:notEmpty name="aimWorkspaceForm" property="workspaces">
													<logic:iterate name="aimWorkspaceForm" property="workspaces"
													id="workspaces" type="org.digijava.module.aim.dbentity.AmpTeam">
													<tr>
														<td bgcolor="#ffffff">
															<jsp:useBean id="urlParams2" type="java.util.Map" class="java.util.HashMap"/>
															<c:set target="${urlParams2}" property="tId">
															<bean:write name="workspaces" property="ampTeamId" />
															</c:set>
															<c:set target="${urlParams2}" property="event" value="edit" />
															<c:set target="${urlParams2}" property="dest" value="admin" />
															<bean:define id="translation">
																<digi:trn key="aim:clickToViewWorkspace">Click here to view Workspace</digi:trn>
															</bean:define>
															<digi:link href="/getWorkspace.do" name="urlParams2" title="<%=translation%>" >
															<bean:write name="workspaces" property="name"/></digi:link>
														</td>
														<td bgcolor="#ffffff" width="70" align="center">
															<jsp:useBean id="urlParams" type="java.util.Map" class="java.util.HashMap"/>
															<c:set target="${urlParams}" property="teamId">
															<bean:write name="workspaces" property="ampTeamId" />
															</c:set>
															<bean:define id="translation">
																<digi:trn key="aim:clickToViewMembers">Click here to view Members</digi:trn>
															</bean:define>
															[ <digi:link href="/teamMembers.do" name="urlParams" title="<%=translation%>" >Members</digi:link> ]
														</td>
														<td bgcolor="#ffffff" width="70" align="center">
															<jsp:useBean id="urlParams1" type="java.util.Map" class="java.util.HashMap"/>
															<c:set target="${urlParams1}" property="id">
															<bean:write name="workspaces" property="ampTeamId" />
															</c:set>
															<bean:define id="translation">
																<digi:trn key="aim:clickToViewActivities">Click here to view Activities</digi:trn>
															</bean:define>
															[ <digi:link href="/teamActivities.do" name="urlParams1" title="<%=translation%>" >
															Activities</digi:link> ]
														</td>
														<td bgcolor="#ffffff" width="40" align="center">
															<%--
															<c:set target="${urlParams2}" property="actionEvent" value="edit" />--%>
															<bean:define id="translation">
																<digi:trn key="aim:clickToEditWorkspace">Click here to Edit Workspace</digi:trn>
															</bean:define>
															[ <digi:link href="/getWorkspace.do" name="urlParams2" title="<%=translation%>" >Edit</digi:link> ]
														</td>
														<td bgcolor="#ffffff" width="55" align="center">
															<jsp:useBean id="urlParams4" type="java.util.Map" class="java.util.HashMap"/>
															<c:set target="${urlParams4}" property="tId">
																<bean:write name="workspaces" property="ampTeamId" />
															</c:set>
															<c:set target="${urlParams4}" property="event" value="delete"/>
															<bean:define id="translation">
																<digi:trn key="aim:clickToDeleteWorkspace">Click here to Delete Workspace</digi:trn>
															</bean:define>
															[ <digi:link href="/deleteWorkspace.do" name="urlParams4" 
																title="<%=translation%>" onclick="return onDelete()">Delete</digi:link> ]
														</td>
													</tr>
													</logic:iterate>

													<!-- page logic for pagination -->
													<logic:notEmpty name="aimWorkspaceForm" property="pages">
													<tr bgcolor="#ffffff">
														<td colspan="5">
															<digi:trn key="aim:workspaceManagerPages">
																Pages :
															</digi:trn>
															<jsp:useBean id="urlParams3" type="java.util.Map" class="java.util.HashMap"/>
															<logic:iterate name="aimWorkspaceForm" property="pages" id="pages" 
															type="java.lang.Integer">
															<c:set target="${urlParams3}" property="page"><%=pages%></c:set>
															<bean:define id="translation">
																<digi:trn key="aim:clickToViewAllPages">Click here to view All pages</digi:trn>
															</bean:define>
															<digi:link href="/workspaceManager.do" name="urlParams3" 
															title="<%=translation%>" ><%=pages%></digi:link> |&nbsp; 
															</logic:iterate>
														</td>
													</tr>
													</logic:notEmpty>												
													<!-- end page logic for pagination -->													
													
													</logic:notEmpty>
													<!-- end page logic -->													
											</table>
										</td></tr>
									</table>
									
								</td>
							</tr>
						</table>
					</td>
					
					<td noWrap width=100% vAlign="top">
						<table align=center cellPadding=0 cellSpacing=0 width="90%" border=0>	
							<tr>
								<td>
									<!-- Other Links -->
									<table cellPadding=0 cellSpacing=0 width=100>
										<tr>
											<td bgColor=#c9c9c7 class=box-title>
												<digi:trn key="aim:otherLinks">
												Other links
												</digi:trn>
											</td>
											<td background="module/aim/images/corner-r.gif" height="17" width=17>
												&nbsp;
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td bgColor=#ffffff class=box-border>
									<table cellPadding=5 cellSpacing=1 width="100%">
										<tr>
											<td>
												<digi:img src="module/aim/images/arrow-014E86.gif" width="15" height="10"/>
												<bean:define id="translation">
													<digi:trn key="aim:clickToAddTeams">Click here to Add Teams</digi:trn>
												</bean:define>
												<digi:link href="/createWorkspace.do?dest=admin" title="<%=translation%>" >
												<digi:trn key="aim:addTeam">
												Add Teams
												</digi:trn>
												</digi:link>
											</td>
										</tr>
										<tr>
											<td>
												<digi:img src="module/aim/images/arrow-014E86.gif" width="15" height="10"/>
												<bean:define id="translation">
													<digi:trn key="aim:clickToViewRoles">Click here to view Roles</digi:trn>
												</bean:define>
												<digi:link href="/roles.do" title="<%=translation%>" >
												<digi:trn key="aim:roles">
												Roles
												</digi:trn>
												</digi:link>
											</td>
										</tr>
										<tr>
											<td>
												<digi:img src="module/aim/images/arrow-014E86.gif" width="15" height="10"/>
												<bean:define id="translation">
													<digi:trn key="aim:clickToAddRoles">Click here to Add Roles</digi:trn>
												</bean:define>
												<digi:link href="/updateRole.do" title="<%=translation%>" >
												<digi:trn key="aim:addRole">
												Add Roles
												</digi:trn>
												</digi:link>
											</td>
										</tr>
										<tr>
											<td>
												<digi:img src="module/aim/images/arrow-014E86.gif" width="15" height="10"/>
												<bean:define id="translation">
													<digi:trn key="aim:clickToViewAdmin">Click here to goto Admin Home</digi:trn>
												</bean:define>
												<digi:link href="/admin.do" title="<%=translation%>" >
												<digi:trn key="aim:AmpAdminHome">
												Admin Home
												</digi:trn>
												</digi:link>
											</td>
										</tr>
										<!-- end of other links -->
									</table>
								</td>
							</tr>
						</table>
					</td></tr>
					</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>