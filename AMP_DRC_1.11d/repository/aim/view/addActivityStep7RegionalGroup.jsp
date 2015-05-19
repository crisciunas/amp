<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>
<%@ taglib uri="/taglib/fieldVisibility" prefix="field" %>
<%@ taglib uri="/taglib/featureVisibility" prefix="feature" %>
<%@ taglib uri="/taglib/moduleVisibility" prefix="module" %>
<%@ taglib uri="/taglib/jstl-functions" prefix="fn" %>
<%@ taglib uri="/taglib/aim" prefix="aim" %>

<digi:instance property="aimEditActivityForm" />
									<tr><td>
										<IMG alt=Link height=10 src="../ampTemplate/images/arrow-014E86.gif" width=15>
										<a title="<digi:trn key="aim:RegionalGroupTitle">The Regional Group</digi:trn>">
										<b><digi:trn key="aim:RegionalGroup">Regional Group</digi:trn></b></a>
									</td></tr>
									<tr><td bgColor=#f4f4f2>
										&nbsp;
									</td></tr>
									<tr><td>
										<logic:notEmpty name="aimEditActivityForm" property="regGroups">
											<table width="100%" cellSpacing=1 cellPadding=5 class="box-border-nopadding">
												<logic:iterate name="aimEditActivityForm" property="regGroups"
												id="regGroups" type="org.digijava.module.aim.dbentity.AmpOrganisation">
												<tr><td>
													<table width="100%" cellSpacing="1" cellPadding="1" vAlign="top" align="left">
														<tr>
															<td width="3">
																<html:multibox property="selRegGroups">
																	<bean:write name="regGroups" property="ampOrgId" />
																</html:multibox>
															</td>
															<td align="left">
																<bean:write name="regGroups" property="name" />
															</td>
														</tr>
													</table>
												</td></tr>
												</logic:iterate>
												<tr><td>
													<table cellSpacing=1 cellPadding=1>
														<tr>
															<td>
																<field:display name="Regional Group Add Button" feature="Regional Group">
																	<aim:addOrganizationButton form="${aimEditActivityForm}" collection="regGroups" refreshParentDocument="true"><digi:trn key="btn:addOrganizations">Add Organizations</digi:trn></aim:addOrganizationButton>
																</field:display>
															</td>
															<td>
																<field:display name="Regional Group Remove Button" feature="Regional Group">															
																<html:button  styleClass="dr-menu" property="submitButton" onclick="return removeSelOrgs(7)">
																	<digi:trn key="btn:removeSelectedOrganizations">Remove Selected Organizations</digi:trn>
																</html:button>
																</field:display>
															</td>
														</tr>
													</table>
												</td></tr>
											</table>
										</logic:notEmpty>

										<logic:empty name="aimEditActivityForm" property="regGroups">
											<table width="100%" bgcolor="#cccccc" cellSpacing=1 cellPadding=5>
												<tr>
													<td bgcolor="#ffffff">
													<field:display name="Regional Group Add Button" feature="Regional Group">
														<aim:addOrganizationButton form="${aimEditActivityForm}" collection="regGroups" refreshParentDocument="true"><digi:trn key="btn:addOrganizations">Add Organizations</digi:trn></aim:addOrganizationButton>
													</field:display>
													</td>
												</tr>
											</table>
										</logic:empty>
									</td></tr>