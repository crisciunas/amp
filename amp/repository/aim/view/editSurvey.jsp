<%@ page pageEncoding="UTF-8" %>

<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>

<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>

<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/taglib/struts-html" prefix="html" %>

<%@ taglib uri="/taglib/struts-nested" prefix="nested" %>

<%@ taglib uri="/taglib/digijava" prefix="digi" %>

<%@ taglib uri="/taglib/jstl-core" prefix="c" %>
<%@ taglib uri="/taglib/aim" prefix="aim" %>


<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/addActivity.js"/>"></script>
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/common.js"/>"></script>

<jsp:include page="/repository/aim/view/addOrganizationPopin.jsp" flush="true" />

<div id="popin" style="display: none">
	<div id="popinContent" class="content">
	</div>
</div>

<script type="text/javascript">
<!--
		YAHOO.namespace("YAHOO.amp");
		var myPanel = new YAHOO.widget.Panel("newmyComment", {
			width:"600px",
			fixedcenter: true,
		    constraintoviewport: true,
		    underlay:"none",
		    close:true,
		    visible:false,
		    modal:true,
		    draggable:true
		    });
		var panelStart=0;
		var checkAndClose=false;
	function initStep17Scripts() {
		var msg='\n<digi:trn key="aim:addeditComment">Add/Edit Comment</digi:trn>';
		myPanel.setHeader(msg);
		myPanel.setBody("");
		myPanel.beforeHideEvent.subscribe(function() {
			panelStart=1;
		}); 
		myPanel.render(document.body);
	}
	//this is called from editActivityMenu.jsp
	//window.onload=initStep17Scripts;
-->	
</script>

<style type="text/css">
	.mask {
	  -moz-opacity: 0.8;
	  opacity:.80;
	  filter: alpha(opacity=80);
	  background-color:#2f2f2f;
	}
	
	#popin .content { 
	    overflow:auto; 
	    height:455px; 
	    background-color:fff; 
	    padding:10px; 
	} 
	.bd a:hover {
  		background-color:#ecf3fd;
		font-size: 10px; 
		color: #0e69b3; 
		text-decoration: none	  
	}
	.bd a {
	  	color:black;
	  	font-size:10px;
	}
		
</style>
<script language="JavaScript">
    <!--
   
    //DO NOT REMOVE THIS FUNCTION --- AGAIN!!!!
    function mapCallBack(status, statusText, responseText, responseXML){
       window.location.reload();
    }
    
    
    var responseSuccess = function(o){
	/* Please see the Success Case section for more
	 * details on the response object's properties.
	 * o.tId
	 * o.status
	 * o.statusText
	 * o.getResponseHeader[ ]
	 * o.getAllResponseHeaders
	 * o.responseText
	 * o.responseXML
	 * o.argument
	 */
		var response = o.responseText; 
		var content = document.getElementById("popinContent");
	    //response = response.split("<!")[0];
		content.innerHTML = response;
	    //content.style.visibility = "visible";
		showContent();
	}
	var delCommentContent=false;	 
	var responseFailure = function(o){ 
	// Access the response object's properties in the 
	// same manner as listed in responseSuccess( ). 
	// Please see the Failure Case section and 
	// Communication Error sub-section for more details on the 
	// response object's properties.
		alert("Connection Failure!"); 
	}  
	var callback = 
	{ 
		success:responseSuccess, 
		failure:responseFailure 
	};
	function showContent(){
		var element = document.getElementById("popin");
		element.style.display = "inline";
		if (panelStart < 1){
			myPanel.setBody(element);
		}
		if (panelStart < 2){
			document.getElementById("popin").scrollTop=0;
			myPanel.show();
			panelStart = 2;
		}
		checkErrorAndClose();
	}
	function checkErrorAndClose(){
		if(checkAndClose==true){
			if(document.getElementsByName("someError")[0]==null || document.getElementsByName("someError")[0].value=="false"){
				myclose();
				refreshPage();
			}
			checkAndClose=false;			
		}
	}
	function refreshPage(){
		//document.aimEditActivityForm.step.value = "17";
		//document.aimEditActivityForm.action = "/aim/addActivity.do?edit=true";
		//document.aimEditActivityForm.target = "_self";
		document.aimEditActivityForm.submit();		
	}

	function myclose(){
		myPanel.hide();	
		panelStart=1;
	
	}
	function closeWindow() {
		myclose();
	}
	
	function showPanelLoading(msg){
		myPanel.setHeader(msg);		
		var content = document.getElementById("popinContent");
		content.innerHTML = '<div style="text-align: center">' + 
		'<img src="/TEMPLATE/ampTemplate/imagesSource/loaders/ajax-loader-darkblue.gif" border="0" height="17px"/>&nbsp;&nbsp;' + 
		'<digi:trn>Loading, please wait ...</digi:trn><br/><br/></div>';
		showContent();
	}
-->
</script>


<script language="Javascript">

<!--

	function openSelOrgPopup() {
      openNewWindow(600, 450);
      <digi:context name="url" property="context/module/moduleinstance/selectOrganisationForAhsurvey.do?edit=true&orgSelReset=true&edit=true&svAction=searsh" />
      openURLinWindow("<%=url%>",600, 450)
    }

	function move(pg) {

		document.aimEditActivityForm.page.value = pg;

		document.aimEditActivityForm.submit();

	}



	function fnGetSurveyList() {

		<digi:context name="survey" property="context/module/moduleinstance/editSurveyList.do?edit=true" />

		document.aimEditActivityForm.action = "<%= survey %>";

		document.aimEditActivityForm.target = "_self";

		document.aimEditActivityForm.submit();

	}



	function fnChk(frmContrl) {

		if (isNaN(frmContrl.value)) {

      		alert('Please enter numeric value only.');

      		frmContrl.value = "";

      		//frmContrl.focus();

      		return false;

      	}

		if (frmContrl.value < 0 || frmContrl.value > 100) {
      		alert('Value must be between 0 and 100.');
      		frmContrl.value = "";
      		return false;
      	}

	}

	function validateForm() {
      return true;
	}


-->

</script>



<digi:instance property="aimEditActivityForm" />

<digi:form action="/editSurvey.do~edit=true" method="post">



<input type="hidden" name="page" value="">

<html:hidden property="step" />



<table width="100%" cellPadding="0" cellSpacing="0" vAlign="top" align="left" border=0>

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

								<c:set var="translation">

									<digi:trn key="aim:clickToViewMyDesktop">Click here to view MyDesktop </digi:trn>

								</c:set>

								<c:set var="message">
										<digi:trn key="aim:documentNotSaved">WARNING : The document has not been saved. Please press OK to continue or Cancel to save the document.</digi:trn>
									</c:set>

								<digi:link href="/viewMyDesktop.do" styleClass="comment"  onclick="return quitRnot1('${message}')"  title="${translation}">

									<digi:trn key="aim:portfolio">Portfolio</digi:trn>

								</digi:link>&nbsp;&gt;&nbsp;

								<bean:define id="tip">

									<digi:trn key="aim:clickToViewSurveyList">Click here to view survey list</digi:trn>

								</bean:define>

								<a href="javascript:fnGetSurveyList()" class="comment"  title="<%=tip%>">

									<digi:trn key="aim:aidEffectivenessSurvey">Aid Effectiveness Survey</digi:trn>

								</a>&nbsp;&gt;&nbsp;

									<digi:trn key="aim:editParisIndicators">Paris Indicators</digi:trn>

								</span>

							</td>

						</tr>

					</table>

				</td></tr>

				<tr><td>

					<table width="100%" cellSpacing="1" cellPadding="1" vAlign="top">

						<tr>

							<td height=16 vAlign=center width="100%"><span class=subtitle-blue>

								<c:if test="${aimEditActivityForm.editAct == false}">

									<digi:trn key="aim:addNewActivity">Add New Activity</digi:trn>

								</c:if>

								<c:if test="${aimEditActivityForm.editAct == true}">

									<digi:trn key="aim:editActivity">Edit Activity</digi:trn>

								</c:if>

							</td>

						</tr>

					</table>

				</td></tr>

				<tr><td>

					<digi:errors/>

				</td></tr>

				<tr><td>

					<table width="100%" cellSpacing="5" cellPadding="3" vAlign="top" border=0>

						<tr><td width="75%" vAlign="top">

						<table cellPadding=0 cellSpacing=0 width="100%" border=0>

							<tr>

								<td width="100%">

									<table cellPadding=0 cellSpacing=0 width="100%" border=0>

										<tr>

											<td width="13" height="20" background="module/aim/images/left-side.gif">

                                            &nbsp;

											</td>

											<td vAlign="center" align ="center" class="textalb" height="20" bgcolor="#006699">

												<digi:trn key="aim:aidEffectiveIndicators">

													Aid Effectiveness Indicators</digi:trn>

											</td>

											<td width="13" height="20" background="module/aim/images/right-side.gif">

                                            &nbsp;

											</td>

										</tr>

									</table>

								</td>

							</tr>

							<tr><td bgcolor="#f4f4f2" width="100%">

							<table width="100%" cellSpacing="1" cellPadding="3" vAlign="top" align="left" bgcolor="#006699">

							<tr><td bgColor=#f4f4f2 align="center" vAlign="top">

								<!-- contents -->



								<table width="95%" bgcolor="#f4f4f2" border=0>

									<tr><td>

										<IMG alt=Link height=10 src="/TEMPLATE/ampTemplate/imagesSource/arrows/arrow-014E86.gif" width=15>

										<b><c:out value="${aimEditActivityForm.survey.fundingOrg}" /></b>&nbsp;>&nbsp;

										<b><c:out value="${aimEditActivityForm.identification.title}" /></b>

										</td>

									</tr>

									<tr><td>&nbsp;</td></tr>

									<tr><td>

								<!-- Indicator Table starts here -->


                                <table width="100%" cellPadding=3>
                                  <tr>

                                    <td bgcolor=#ECF3FD width="100%">

                                      <b><digi:trn key="aim:piFirstQuestion">If different of the funding agency, please specify the point of delivery donor?</digi:trn></b>

                                    </td>
                                  </tr>

                                  <TR bgcolor="#f4f4f2">
                                    <TD colspan="2" width="90%">
                                      <TABLE width="90%" cellPadding="5" cellSpacing="1" vAlign="top" align="center" bgcolor="#ffffff">
                                        <tr>
                                          <td>
                                          ${aimEditActivityForm.survey.ahsurvey.pointOfDeliveryDonor.name}
                                          </td>
                                          <td align="right">
										  	 <aim:addOrganizationButton donorGroupTypes="MUL,BIL" useClient="false" aditionalRequestParameters="removeAddButton=true" form="${aimEditActivityForm.survey.ahsurvey}"  property="pointOfDeliveryDonor" refreshParentDocument="true" styleClass="dr-menu"><digi:trn key="aim:editactivity:selectorganization">Select organization</digi:trn></aim:addOrganizationButton>                                          </td>
                                        </tr>
                                      </TABLE>
                                     </td>
                                   </tr>
                                </table>

										<logic:notEmpty name="aimEditActivityForm" property="survey.indicators">

											<table width="100%" cellPadding=3>

												<bean:define id="start" name="aimEditActivityForm" property="survey.startIndex" />

													<nested:iterate name="aimEditActivityForm" property="survey.indicators"

																    offset="start" length="5">

														<tr>

															<td bgcolor=#ECF3FD width="5%">

																<b><nested:write property="indicatorCode" /></b>

															</td>

															<td bgcolor=#ECF3FD width="95%"><b>

																<bean:define id = "piIndcCode" >

																	<nested:write property="indicatorCode" />

																</bean:define>

																<digi:trn key='<%="aim:parisIndc" + piIndcCode %>'>

																	<nested:write property="name" />

																</digi:trn>

															</td>

														</tr>
														<nested:define id="indicatorCodeAux" property="indicatorCode" />
													<nested:iterate property="question">

														<TR bgcolor="#f4f4f2">

															<TD colspan="2" width="90%">

															<TABLE width="90%" cellPadding="5" cellSpacing="1" vAlign="top" align="center" bgcolor="#ffffff">

																<TR>

																	<c:choose>

																		<c:when test="${indicatorCodeAux == '7' }">

																			<TD width="100%">

																				<digi:trn key="aim:noQuestionPI7">

																				No question here. This indicator is calculated by the system based on

																				information entered for disbursements for this project/programme

																				</digi:trn>

																		</c:when>

																		<c:when test="${indicatorCodeAux == '10a' }">

																			<TD width="100%">

																				<digi:trn key="aim:noQuestionPI10a">

																				No question at the activity level; this indicator is calculated using the Calendar Module

																				</digi:trn>

																		</c:when>

																		<c:when test="${indicatorCodeAux == '10b' }">

																			<TD width="100%">

																				<digi:trn key="aim:noQuestionPI10b">

																				No question at the activity level; this indicator is calculated using the Document Management Module

																			</digi:trn>

																		</c:when>

																		<c:otherwise>

																			<TD width="80%">

																				<bean:define id = "piIndcQuesId" >

																					<nested:write property="questionId" />

																				</bean:define>

																				<digi:trn key='<%= "aim:parisIndc" + piIndcCode + "Ques" + piIndcQuesId %>'>

																					<nested:write property="questionText" />

																				</digi:trn>

																				<br>

																		</c:otherwise>

																	</c:choose>

																	</TD>

																<nested:equal property="questionType" value="yes-no">

																	<c:if test="${indicatorCodeAux != '10a' && indicatorCodeAux != '10b' }">

																		<TD width="3%">

																			<nested:radio property="response" value="Yes" />

																		</TD>

																		<TD width="7%"><digi:trn key="aim:parisIndcYesResponse">Yes</digi:trn></TD>

																		<TD width="3%">

																			<nested:radio property="response" value="No" />

																		</TD>

																		<TD width="7%"><digi:trn key="aim:parisIndcNoResponse">No</digi:trn></TD>

																	</c:if>

																</nested:equal>

																<nested:equal property="questionType" value="input">

																	<TD width="20%">

																		<nested:text property="response" size="2" maxlength="3" onkeyup="fnChk(this)" />%

																	</TD>

																</nested:equal>

																<nested:equal property="questionType" value="calculated">

																	<c:if test="${indicatorCodeAux != '7' }">

																		<TD width="20%">

																			<nested:equal property="response" value="nil">

																				<digi:trn key="aim:noPlannedDisbursement">No planned disbursement</digi:trn>

																			</nested:equal>

																			<nested:notEqual property="response" value="nil">

																				<nested:text property="response" size="5" readonly="true" />

																			</nested:notEqual>

																		</TD>

																	</c:if>

																</nested:equal>

																</TR>

															</TABLE>

															</TD>

														</TR>

														</nested:iterate>

													</nested:iterate>

												</table>

											</logic:notEmpty>

										</td></tr>

										<tr><td>&nbsp;</td></tr>



										<!-- pagination starts here-->



											<logic:notEmpty name="aimEditActivityForm" property="survey.pageColl">

												<tr>

													<td colspan="2">

														<digi:trn key="aim:surveyPages">Pages :</digi:trn>

														<logic:iterate name="aimEditActivityForm" property="survey.pageColl" id="pages" type="java.lang.Integer">

														<%--

															<jsp:useBean id="urlParams" type="java.util.Map" class="java.util.HashMap"/>

															<c:set target="${urlParams}" property="page"><%=pages%></c:set>

														--%>

															<c:if test="${aimEditActivityForm.survey.currPage == pages}">

																<font color="#FF0000"><%=pages%></font>

															</c:if>

															<c:if test="${aimEditActivityForm.survey.currPage!= pages}">

																<c:set var="translation">

																	<digi:trn key="aim:clickToViewNextPage">Click here to go to Next Page</digi:trn>

																</c:set>

																<a href="javascript:move(<%=pages%>)"><%=pages%></a>

																<%--<digi:link href="/editSurvey.do" name="urlParams" title="${translation}" >

																	<%=pages%>

																</digi:link> --%>

															</c:if>

															|&nbsp; </logic:iterate>

													</td>

												</tr>

											</logic:notEmpty>



										<!-- pagination ends here-->



												<tr><td>&nbsp;</td></tr>

											</table>



								<!-- Indicator Table ends here -->



									</td></tr>

								</table>



								<!-- end contents -->

							</td></tr>

							</table>

							</td></tr>

						</table>

						</td>

						<td width="25%" vAlign="top" align="right">
							<jsp:include page="editActivityMenu.jsp" flush="true" />
						</td></tr>

					</table>

				</td></tr>

				<tr><td>&nbsp;</td></tr>

			</table>

		</td>

		<td width="10">&nbsp;</td>

	</tr>

</table>

<%--

</td></tr>

</table> --%>

</digi:form>
