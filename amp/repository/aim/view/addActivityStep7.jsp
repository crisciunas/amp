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

<jsp:include page="addActivityStep7Popin.jsp" flush="true" />
<jsp:include page="addOrganizationPopin.jsp" flush="true" />

<digi:instance property="aimEditActivityForm" />

<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/addActivity.js"/>"></script>
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/common.js"/>"></script>

<script language="JavaScript">

<!--

	function validateForm() {
		return true;
	}

function addOrgs(value) {
		openNewWindow(600, 400);
		<digi:context name="selectOrganization" property="context/module/moduleinstance/selectOrganization.do~orgSelReset=true" />
		document.aimEditActivityForm.action = "<%= selectOrganization %>~item="+value+"~edit=true";
		document.aimEditActivityForm.prevOrg.value = value;
		document.aimEditActivityForm.target = popupPointer.name;
		document.aimEditActivityForm.submit();
}

function resetAll()
{
	<digi:context name="resetAll" property="context/module/moduleinstance/resetAll.do?edit=true" />
	document.aimEditActivityForm.action = "<%= resetAll %>";
	document.aimEditActivityForm.target = "_self";
	document.aimEditActivityForm.submit();
	return true;
}

function removeSelOrgs(value) {
	if (isOrganisationSelectedForDeletion(value)) {
		document.getElementsByName("agencies.item")[0].value = value;
		<digi:context name="remOrgs" property="context/module/moduleinstance/removeSelRelOrgs.do?edit=true" />
		document.aimEditActivityForm.action = "<%= remOrgs %>";
		document.aimEditActivityForm.target = "_self"
		document.aimEditActivityForm.submit();
		return true;
	}
	return false;
}


function isOrganisationSelectedForDeletion(val) {
	//alert(val);
	var sel;
	var selected = true;
	var count = 0;
	if (val == '1') {// executing agency
		sel = document.getElementsByName("agencies.selExAgencies");		
	} else if (val == '2') {// implementing agency
		sel = document.getElementsByName("agencies.selImpAgencies");
	} else if (val == '5') {// beneficiary agency
		sel = document.getElementsByName("agencies.selBenAgencies");
	} else if (val == '6') {// contracting agency
		sel = document.getElementsByName("agencies.selConAgencies");
	} else if (val == '7') {// regional group
		sel = document.getElementsByName("agencies.selRegGroups");
	} else if (val == '8') {//sector group
		sel = document.getElementsByName("agencies.selSectGroups");
	} else if (val == '9') {//responsible organisation
		sel = document.getElementsByName("agencies.selRespOrganisations");
	}
	for (var i = 0; i<sel.length; i++) {
		if (!sel[i].checked) {
			count++;
		}
	}
	//alert(sel.length);
	//alert(count);
	if (count == sel.length) {
		selected = false;
		<c:set var="errMsg">
	   		<digi:trn>Please select an organization to remove</digi:trn>
	    </c:set>
	    alert("${errMsg}");
	}
	//alert(selected);
	return selected;
}
-->
</script>

<script type="text/javascript">
	function  submitAfterSelectingOrg()
	{
		<digi:context name="nextTarget" property="context/module/moduleinstance/addActivity.do" />
    	document.aimEditActivityForm.action = "<%= nextTarget %>?edit=true";
    	document.aimEditActivityForm.target = "_self";
  		document.aimEditActivityForm.step.value = "${aimEditActivityForm.step}";
    	document.aimEditActivityForm.submit();
    	return true;
	}
</script>

<digi:form action="/addActivity.do" method="post">
<html:hidden property="step" />
<html:hidden property="agencies.item" />
<input type="hidden" name="prevOrg">

<html:hidden property="editAct" />
<c:set var="stepNm">
  ${aimEditActivityForm.stepNumberOnPage}
 </c:set>


<table width="100%" cellPadding="0" cellSpacing="0" vAlign="top" align="left"  border=0>
<tr><td width="100%" vAlign="top" align="left">
<!--  AMP Admin Logo -->
<jsp:include page="teamPagesHeader.jsp" flush="true" />
<!-- End of Logo -->
</td></tr>
<tr><td width="100%" vAlign="top" align="left">

	

<table bgColor=#ffffff cellPadding=0 cellSpacing=0 width="100%" vAlign="top" align="center" border=0>
	<tr>
		<td class=r-dotted-lg width="10">&nbsp;</td>
		<td align=left vAlign=top class=r-dotted-lg>
			<table width="98%" cellSpacing="3" cellPadding="1" vAlign="top" align="left">
				<tr><td>
					<table width="100%" cellSpacing="1" cellPadding="1" vAlign="top">
						<tr>
							<td>
								<span class=crumb>
									<c:if test="${aimEditActivityForm.pageId == 0}">
										<c:set var="translation">
											<digi:trn key="aim:clickToViewAdmin">Click here to go to Admin Home</digi:trn>
										</c:set>
										<digi:link href="/admin.do" styleClass="comment" title="${translation}">
											<digi:trn key="aim:AmpAdminHome">
												Admin Home
											</digi:trn>
										</digi:link>&nbsp;&gt;&nbsp;
									</c:if>
									<c:if test="${aimEditActivityForm.pageId == 1}">
										<c:set var="translation">
											<digi:trn key="aim:clickToViewMyDesktop">Click here to view MyDesktop</digi:trn>
										</c:set>
										<c:set var="message">
											<digi:trn key="aim:documentNotSaved">WARNING : The document has not been saved. Please press OK to continue or Cancel to save the document.</digi:trn>
										</c:set>
										<c:set var="quote">'</c:set>
										<c:set var="escapedQuote">\'</c:set>
										<c:set var="msg">
											${fn:replace(message,quote,escapedQuote)}
										</c:set>
										<digi:link href="/viewMyDesktop.do" styleClass="comment" onclick="return quitRnot1('${msg}')" title="${translation}">
											<digi:trn key="aim:portfolio">
												Portfolio
											</digi:trn>
										</digi:link>&nbsp;&gt;&nbsp;
									</c:if>
								          
		                           <c:forEach var="step" items="${aimEditActivityForm.steps}" end="${stepNm-1}" varStatus="index">
		                               
		                               <c:set property="translation" var="trans">
		                                   <digi:trn key="aim:clickToViewAddActivityStep${step.stepActualNumber}">
		                                       Click here to goto Add Activity Step ${step.stepActualNumber}
		                                   </digi:trn>
		                               </c:set>
		                               <c:if test="${!index.last}">
		                                   <c:if test="${index.first}">
		                                       <digi:link href="/addActivity.do?step=${step.stepNumber}&edit=true" styleClass="comment" title="${trans}">
		                                             <c:if test="${aimEditActivityForm.editAct == true}">
		                                               <digi:trn key="aim:editActivityStep1">
		                                                   Edit Activity - Step 1
		                                               </digi:trn>
		                                           </c:if>
		                                           <c:if test="${aimEditActivityForm.editAct == false}">
		                                               <digi:trn key="aim:addActivityStep1">
		                                                   Add Activity - Step 1
		                                               </digi:trn>
		                                           </c:if>
		                                       </digi:link>
		                                        &nbsp;&gt;&nbsp;
		                                   </c:if>
		                                   <c:if test="${!index.first}">
		                                       <digi:link href="/addActivity.do?step=${step.stepNumber}&edit=true" styleClass="comment" title="${trans}">
		                                           <digi:trn key="aim:addActivityStep${step.stepActualNumber}">Step ${step.stepActualNumber}</digi:trn>
		                                       </digi:link>
		                                        &nbsp;&gt;&nbsp;
		                                   </c:if>
		                               </c:if>
		                               <c:if test="${index.last}">
		                                   <c:if test="${index.first}">
		                                       <c:if test="${aimEditActivityForm.editAct == true}">
		                                           <digi:trn key="aim:editActivityStep1">
		                                               Edit Activity - Step 1
		                                           </digi:trn>
		                                       </c:if>
		                                       <c:if test="${aimEditActivityForm.editAct == false}">
		                                           <digi:trn key="aim:addActivityStep1">
		                                               Add Activity - Step 1
		                                           </digi:trn>
		                                       </c:if>
		                                   </c:if>
		                                   <c:if test="${!index.first}">
		                                       <digi:trn key="aim:addActivityStep${step.stepActualNumber}"> Step ${step.stepActualNumber}</digi:trn>
		                                   </c:if>
		                               </c:if>
		                            </c:forEach>
                            	</span>
							</td>
						</tr>
					</table>
				</td></tr>
				<tr><td>
					<jsp:include page="/repository/aim/view/activityForm_actions_menu.jsp" />
				</td></tr>
				<tr><td>
					<table width="100%" cellSpacing="1" cellPadding="1" vAlign="top">
						<tr>
							<td height=16 vAlign=center width="100%"><span class=subtitle-blue>
								<c:if test="${aimEditActivityForm.editAct == false}">
									<digi:trn key="aim:addNewActivity">
										Add New Activity
									</digi:trn>
								</c:if>
								<c:if test="${aimEditActivityForm.editAct == true}">
									<digi:trn>Title:</digi:trn>&nbsp;<bean:write name="aimEditActivityForm" property="identification.title"/>
								</c:if>
							</td>
						</tr>
					</table>
				</td></tr>
				<tr><td>
					<table width="100%" cellSpacing="5" cellPadding="3" vAlign="top">
						<tr><td width="75%" vAlign="top">
						<table cellPadding=0 cellSpacing=0 width="100%">
							
							<tr><td width="100%" bgcolor="#f4f4f2">
							
							<table width="100%" cellSpacing="1" cellPadding="3" vAlign="top" align="left">
							
							<tr><td bgColor=#f4f4f2 align="center" vAlign="top">
								<table width="95%" bgcolor="#f4f4f2">
									<feature:display name="Responsible Organization" module="Organizations">
										<jsp:include page="addActivityStep7ResponsibleOrganisation.jsp"/>
									</feature:display>
		
											<tr><td>
												&nbsp;
											</td></tr>
									<feature:display name="Executing Agency" module="Organizations">
									<jsp:include page="addActivityStep7ExecutingAgency.jsp"/>
									</feature:display>
	
									<tr><td>
										&nbsp;
									</td></tr>
									<feature:display name="Implementing Agency" module="Organizations">
										<jsp:include page="addActivityStep7ImplementingAgency.jsp"/>
									</feature:display>
									<tr><td>
										&nbsp;
									</td></tr>


									<!-- Beneficiary Agency  -->
									<feature:display name="Beneficiary Agency" module="Organizations">
										<jsp:include page="addActivityStep7BeneficiaryAgency.jsp"/>
									</feature:display>

									<tr><td>&nbsp;</td></tr>


									<!-- Contracting Agency  -->
									<feature:display name="Contracting Agency" module="Organizations">
										<jsp:include page="addActivityStep7ContractingAgency.jsp"/>

									</feature:display>

									<tr><td>&nbsp;</td></tr>

									<!-- Regional Group  -->
									<feature:display name="Regional Group" module="Organizations">
										<jsp:include page="addActivityStep7RegionalGroup.jsp"/>
									</feature:display>

									<tr><td>&nbsp;</td></tr>
									
									<!-- Sector Group  -->
									<feature:display name="Sector Group" module="Organizations">
										<jsp:include page="addActivityStep7SectorGroup.jsp"/>
									</feature:display>
									
									<tr><td>&nbsp;</td></tr>
								</table>

								<!-- end contents -->
							</td></tr>
							</table>
							</td></tr>
						</table>
						</td>
						<td width="25%" vAlign="top" align="right">
						<!-- edit activity form menu -->
							<jsp:include page="editActivityMenu.jsp" flush="true" />
						<!-- end of activity form menu -->
						</td></tr>
					</table>
				</td></tr>
				<tr><td>
					&nbsp;
				</td></tr>
			</table>
		</td>
		<td width="10">&nbsp;</td>
	</tr>
</table>
</td></tr>
</table>
</digi:form>