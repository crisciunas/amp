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
   
<%@page import="org.dgfoundation.amp.ar.ArConstants"%>

						<div id="measures_step_div" class="main_side_cont yui-hidden">
							<c:set var="stepNum" value="3" scope="request" />
							<c:if test="${!myForm.onePager}">
								<jsp:include page="toolbar.jsp" />
							</c:if>		
							<br />			
							<table cellpadding="5px" style="vertical-align: middle" width="100%">
								<tr>
									<td width="48%" align="center">
										<fieldset class="main_side_cont">
											<legend><span class="legend_label"><digi:trn key="rep:wizard:availableMeasures">Available Measures</digi:trn></span></legend>
											<ul id="source_measures_ul" class="draglist">
												<jsp:include page="setMeasures.jsp" />
											</ul>
										</fieldset>
									</td>
									<td valign="middle"  align="center">
										<button style="border: none;" type="button" onClick="MyDragAndDropObject.selectObjs('source_measures_ul', 'dest_measures_ul')">
											<img src="/TEMPLATE/ampTemplate/img_2/ico_arr_right.gif"/>
										</button>
										<br/> <br />
										<button style="border: none;" type="button" onClick="MyDragAndDropObject.deselectObjs('dest_measures_ul', 'source_measures_ul')">
											<img src="/TEMPLATE/ampTemplate/img_2/ico_arr_left.gif"/>
										</button>
									</td>
									<td width="48%" align="center">
										<fieldset class="main_side_cont">
											<legend><span class="legend_label"><digi:trn key="rep:wizard:selectedMeasures">Selected Measures</digi:trn></span></legend>
											<ul id="dest_measures_ul" class="draglist">
											</ul>
										</fieldset>
									</td>
								</tr>
								<tr>
									<td align="center" valign="top">
										<span id="measuresMust" style="visibility: visible">
											<font color="red">* 
												<digi:trn key="rep:wizard:hint:mustselectmeasure">
													Must select at least one measure
												</digi:trn>
											</font>
										</span>
									</td>
									<td>&nbsp;</td>
									<td align="center" valign="top">
										<span id="measuresLimit" style="visibility: hidden">
											<font color="red">* 
												<digi:trn key="rep:wizard:hint:limit2measures">
													You cannot select more than 2 measures in a desktop tab
												</digi:trn>
											</font>
										</span>
									</td>
								</tr>
							</table>
						</div>