<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>

<digi:errors/>
<digi:instance property="aimReportsForm" />
<digi:form action="/addReports.do" method="post">



<!--  AMP Admin Logo -->
<jsp:include page="teamPagesHeader.jsp"  />
<!-- End of Logo -->

<table bgColor=#ffffff cellPadding=0 cellSpacing=0 width=772>
	<tr>
		<td class=r-dotted-lg width=14>&nbsp;</td>
		<td align=left class=r-dotted-lg vAlign=top width=750>
			<table cellPadding=5 cellSpacing=0 width="100%">
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
						<c:set var="translation">
							<digi:trn key="aim:clickToViewReportsManager">Click here to view Reports Manager</digi:trn>
						</c:set>
						<digi:link href="/reportsManager.do" styleClass="comment" title="${translation}" >
						<digi:trn key="aim:reportsManager">
						Reports Manager
						</digi:trn>
						</digi:link>&nbsp;&gt;&nbsp;
						<digi:trn key="aim:addReport">Add Report</digi:trn>	
					</td>
					<!-- End navigation -->
				</tr>
				<tr>
					<td height=16 vAlign=center width=571><span class=subtitle-blue>
						<digi:trn key="aim:reportsManager">
						Reports Manager
						</digi:trn>
						</span>
					</td>
				</tr>
				<tr>
					<td noWrap width=100% vAlign="top">
					<table width="100%" cellspacing=1 cellSpacing=1>
					<tr>
						<td noWrap width=600 vAlign="top">
							<table bgColor=#ffffff cellPadding=0 cellSpacing=0 class=box-border-nopadding width="100%">
								<tr bgColor=#f4f4f2>
									<td vAlign="top" width="100%">
										&nbsp;
									</td>
								</tr>
								<tr bgColor=#f4f4f2>
									<td valign="top">
										<table align=center bgColor=#f4f4f2 cellPadding=0 cellSpacing=0 width="90%" border=0>	
											<tr>
												<td bgColor=#ffffff class=box-border>
													<table border=0 cellPadding=1 cellSpacing=1 class=box-border width="100%">
														<tr bgColor=#dddddb>
															<!-- header -->
															<td bgColor=#dddddb height="20" align="center" colspan="5"><B>
																<digi:trn key="aim:addReport">Add Report</digi:trn>	
															<!-- end header -->
														</tr>
														<!-- Page Logic -->
														<tr>
															<td width="100%">	
																<table width="100%" border=0	 bgColor=#f4f4f2>
																	<tr>
																		<td width="30%" align="right">
																		<digi:trn key="aim:reportName">Report Name</digi:trn>
																		</td>
																	    <td width="30%" >
																	          <html:text property="name" size="20" />
																	    </td>
																	</tr>
																	<tr>
																		<td width="30%" align="right">
																	        <digi:trn key="aim:reportDescription">Description</digi:trn>
																		</td>
																	    <td width="30%">
																           <html:text property="description" size="40" />
																		</td>
																	</tr>
																	
																	<tr>
																		<td colspan="2" width="60%"  align="center">
																			<table width="100%" cellspacing="5">
																				<tr>
																					<td width="50%" align="right">
																					<html:submit value="Save" styleClass="dr-menu" />
																					</td>
																					<td width="50%" align="left">
																						<html:reset value="Cancel" styleClass="dr-menu" />
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																</table>	
															</td>
														</tr>
													<!-- end page logic -->
													</table>
												</td>
											</tr>
											
										</table>
									</td>
								</tr>
								<tr>
									<td bgColor=#f4f4f2>
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
						<td noWrap width=100% vAlign="top">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</td>
	</tr>
</table>
</digi:form>












