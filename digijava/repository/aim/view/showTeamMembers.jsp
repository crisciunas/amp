<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<digi:instance property="aimTeamMemberForm" />
<digi:context name="digiContext" property="context" />

<!--  AMP Admin Logo -->
<jsp:include page="teamPagesHeader.jsp" flush="true" />
<!-- End of Logo -->

<table bgColor=#ffffff cellPadding=0 cellSpacing=0 width=772>
	<tr>
		<td class=r-dotted-lg width=14>&nbsp;</td>
		<td align=left class=r-dotted-lg vAlign=top width=750>
			<table cellPadding=5 cellSpacing=0 width="100%">
				<tr>
					<!-- Start Navigation -->
					<td height=33>
						<span class=crumb>					
						<bean:define id="translation">
							<digi:trn key="aim:clickToViewAdmin">Click here to goto Admin Home</digi:trn>
						</bean:define>
						<digi:link href="/admin.do" styleClass="comment" title="<%=translation%>" >
						<digi:trn key="aim:AmpAdminHome">
						Admin Home
						</digi:trn>
						</digi:link>&nbsp;&gt;&nbsp;
						<bean:define id="translation">
							<digi:trn key="aim:clickToViewWorkspaceManager">Click here to view Workspace Manager</digi:trn>
						</bean:define>
						<digi:link href="/workspaceManager.do" styleClass="comment" title="<%=translation%>" >
						<digi:trn key="aim:workspaceManager">
						Workspace Manager
						</digi:trn>
						</digi:link>&nbsp;&gt;&nbsp;
						<digi:trn key="aim:members">
						Members
						</digi:trn>
						</span>
					</td>
					<!-- End navigation -->
				</tr>
				<tr>
					<td height=16 vAlign=center width=571><span class=subtitle-blue>
						<bean:write name="aimTeamMemberForm" property="teamName" />
					</span></td>
				</tr>
				<tr>
					<td noWrap width=100% vAlign="top">
					<table width="100%" cellspacing=1 cellSpacing=1>
					<tr><td noWrap width=600 vAlign="top">
						<table  bgcolor="#d7eafd" cellPadding=0 cellSpacing=1 width="100%" border=0>
							<tr bgColor=#ffffff>
								<td vAlign="top" width="100%">
								
									<table width="100%" cellspacing=1 cellpadding=1 valign=top align=left>	
										<tr><td bgColor=#d7eafd class=box-title height="20" align="center">
											<!-- Table title -->
											<digi:trn key="aim:membersFor">Members for </digi:trn>
											<bean:write name="aimTeamMemberForm" property="teamName" />
											<!-- end table title -->
										</td></tr>
										<tr><td>
											<table width="100%" cellspacing=1 cellpadding=4 valign=top align=left bgcolor="#d7eafd">

													<logic:empty name="aimTeamMemberForm" property="teamMembers">
														<tr bgcolor="#ffffff">
															<td colspan="3" align="center">
																<b>No team members present</b>
															</td>
														</tr>
													</logic:empty>
													<logic:notEmpty name="aimTeamMemberForm" property="teamMembers">
														<logic:iterate name="aimTeamMemberForm" property="teamMembers" id="teamMembers"
														type="org.digijava.module.aim.helper.TeamMember">
														<tr bgcolor=#ffffff>
															<td>
																<logic:equal name="teamMembers" property="teamHead" value="true">*
																</logic:equal>
																<a href="javascript:showUserProfile(<bean:write name="teamMembers" property="memberId" />)">	
																	<bean:write name="teamMembers" property="memberName" />
																</a>											
															</td>
															<td align="center" width="50">  
																<jsp:useBean id="urlParams" type="java.util.Map" class="java.util.HashMap"/>
																<c:set target="${urlParams}" property="id">
																	<bean:write name="teamMembers" property="memberId" />
																</c:set>
																<c:set target="${urlParams}" property="action" value="edit" />
																<bean:define id="translation">
																	<digi:trn key="aim:clickToEditTeamMemberDetails">
																	Click here to Edit Team Member Details</digi:trn>
																</bean:define>
																[ <digi:link href="/getTeamMemberDetails.do" name="urlParams" title="<%=translation%>" >
																	<digi:trn key="aim:showTeamMembersEdit">
																		Edit
																	</digi:trn>
																</digi:link> ]
															</td>
															<td align="center" width="60"> 
																<c:set target="${urlParams}" property="action" value="delete" />
																<bean:define id="translation">
																	<digi:trn key="aim:clickToDeleteTeamMember">Click here to Delete Team Member</digi:trn>
																</bean:define>
																[ <digi:link href="/getTeamMemberDetails.do" name="urlParams" title="<%=translation%>" >
																	<digi:trn key="aim:showTeamMembersDelete">
																		Delete
																	</digi:trn>
																</digi:link> ]															
															</td>
														</tr>
														</logic:iterate>
														<tr bgcolor="#ffffff"><td colspan="3">
															*
															<digi:trn key="aim:teamLead">	
															Team lead
															</digi:trn>
														</td></tr>
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
												<jsp:useBean id="urlParams1" type="java.util.Map" class="java.util.HashMap"/>
												<c:set target="${urlParams1}" property="teamId">
													<bean:write name="aimTeamMemberForm" property="teamId" />
												</c:set>
												<bean:define id="translation">
													<digi:trn key="aim:clickToAddTeamMember">Click here to Add Team Member</digi:trn>
												</bean:define>

												<c:set target="${urlParams1}" property="fromPage" value="1"/>
												<digi:link href="/showAddTeamMember.do" name="urlParams1" title="<%=translation%>" >
													<digi:trn key="aim:addTeamMember">Add Team Member </digi:trn>
												</digi:link>
											</td>
										</tr>
										<tr>
											<td>
												<digi:img src="module/aim/images/arrow-014E86.gif" width="15" height="10"/>
												<bean:define id="translation">
													<digi:trn key="aim:clickToAddRoles">Click here to Add Roles</digi:trn>
												</bean:define>
												<digi:link href="/roles.do" title="<%=translation%>" >
												<digi:trn key="aim:roles">Add Roles</digi:trn>
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
												<digi:trn key="aim:AmpAdminHome">Admin Home</digi:trn>
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
