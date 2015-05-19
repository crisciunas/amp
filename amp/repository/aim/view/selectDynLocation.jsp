<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>
<%@ taglib uri="/taglib/category" prefix="category" %>

<digi:ref href="css/styles.css" type="text/css" rel="stylesheet" />
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/addActivity.js"/>"></script>
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/common.js"/>"></script>

<digi:instance property="selectLocationForm" />

<digi:form action="/locationSelected.do">
<html:hidden styleId="locationReset" property="locationReset" value="false" />
<html:hidden styleId="parentLocId" property="parentLocId" />
<html:hidden styleId="parentIndex" property="parentIndex" />


<table width="100%" cellSpacing=5 cellPadding=5 vAlign="top" border=0>
	<tr><td vAlign="top">
		<table bgcolor=#f4f4f2 cellPadding=5 cellSpacing=5 width="100%" class=box-border-nopadding>
			<tr>
				<td align=left vAlign=top>
					<table bgcolor=#f4f4f2 cellPadding=0 cellSpacing=0 width="100%" class=box-border-nopadding>
						<tr bgcolor="#006699">
							<td vAlign="center" width="100%" align ="center" class="textalb" height="20">
								<digi:trn key="aim:selectLocation">
								Select Location</digi:trn>
							</td>
						</tr>
						<tr>
							<td align="center" bgcolor=#ECF3FD>
								<table cellPadding=2 cellSpacing=2>
                                                                    <c:if test="${selectLocationForm.showLocLevelSelect}">
                                                                     <tr>
                                                                        <td><digi:trn key="aim:pleaseSelectLevel">Select level</digi:trn></td>
                                                                        <td>
                                                                            <c:set var="translation">
                                                                                <digi:trn key="aim:addActivityImplLevelFirstLine">Please select from below</digi:trn>
                                                                            </c:set>
                                                                    <category:showoptions multiselect="false" firstLine="${translation}" name="selectLocationForm" property="implemLocationLevel" keyName="<%= org.digijava.module.categorymanager.util.CategoryConstants.IMPLEMENTATION_LOCATION_KEY %>"  outeronchange="levelChanged()" styleClass="inp-text"/>
                                                                    </td>
                                                                    </tr>
                                                                    </c:if>
									<logic:notEmpty name="selectLocationForm" property="locationByLayers">
										<logic:iterate name="selectLocationForm" property="locationByLayers" id="entry">
											<bean:define id="myCollection" type="java.util.Collection" name="entry" property="value" />
											<% pageContext.setAttribute("colSize", myCollection.size() ) ;%>
											<c:choose>
												<c:when test="${entry.key==(selectLocationForm.impLevelValue -1) }">
													<c:set var="sizeString">5</c:set>
													<c:set var="multipleString">multiple="multiple"</c:set>
													<c:set var="changeString"> </c:set>
													<c:set var="nameString">name="userSelectedLocs"</c:set>
												</c:when>
												<c:otherwise>
													<c:set var="sizeString">1</c:set>
													<c:set var="multipleString"></c:set>
													<c:set var="changeString">locationChanged('loc_${entry.key}')</c:set>
													<c:set var="nameString"></c:set>
												</c:otherwise>
											</c:choose>
											<tr>
											<td>
												<category:getoptionvalue categoryKey="<%= org.digijava.module.categorymanager.util.CategoryConstants.IMPLEMENTATION_LOCATION_KEY%>" categoryIndex="${entry.key}"/>
											</td>
											<td>
												<select id="loc_${entry.key}" class="inp-text" size="${sizeString}" onchange="${changeString}" ${multipleString} ${nameString} >
													<c:if test="${colSize!=1 && sizeString=='1'}">
														<option value="-1">&nbsp;&nbsp;<digi:trn>Please select from below</digi:trn>&nbsp;&nbsp;</option>
													</c:if>
													<logic:notEmpty name="entry" property="value">
														<logic:iterate name="entry" property="value" id="locationEntry">
															<c:choose>
															<c:when test="${locationEntry.key == selectLocationForm.selectedLayers[entry.key]}">
																<option selected="selected" value="${locationEntry.key}">${locationEntry.value}</option>
															</c:when>
															<c:otherwise>
																<option value="${locationEntry.key}">${locationEntry.value}</option>
															</c:otherwise>
															</c:choose>
														</logic:iterate>
													</logic:notEmpty>
												</select>
											</td>
										</tr>
										</logic:iterate>
									
									</logic:notEmpty>
									
								</table>
							</td>
						</tr>
						<c:if test="${aimEditActivityForm.location.noMoreRecords}">
								<tr bgcolor="#ECF3FD">
									<td colspan="2" align="center" height="20"> 
									<digi:trn key="location:norcords:found">No records found</digi:trn>
									</td>	
								</tr>
						</c:if>
						<tr bgcolor="#ECF3FD">
							<td align="center">
								<table cellPadding=3 cellSpacing=3>
									<tr>
										<td>	
											
											<input 
											<c:if test="${aimEditActivityForm.location.noMoreRecords}">
												disabled="true"
											</c:if> 
											
											type="button" value="<digi:trn key='btn:add'>Add</digi:trn>" class="dr-menu"
											onclick="buttonAddLocation()">
										</td>
										<td>
											<input type="button" value="<digi:trn key='btn:close'>Close</digi:trn>" class="dr-menu" onclick="closeWindow()">
										</td>
									</tr>
								</table>
							</td>
						</tr>

					</table>
				</td>
			</tr>
		</table>
	</td></tr>
</table>

</digi:form>