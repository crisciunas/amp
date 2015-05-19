<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<digi:instance property="aimEditActivityForm" />

<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/addActivity.js"/>"></script>
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/common.js"/>"></script>

<script language="JavaScript">

<!-- 
function overwrite() {
	document.aimEditActivityForm.target = "_self";		
	document.aimEditActivityForm.submit();
}

function cancel() {
	document.aimEditActivityForm.step.value = "9";
	<digi:context name="preview" property="context/module/moduleinstance/previewActivity.do?edit=true" />
	document.aimEditActivityForm.action = "<%= preview %>";
	document.aimEditActivityForm.target = "_self";	
	document.aimEditActivityForm.submit();
}
-->

</script>

<digi:form action="/saveActivity.do" method="post">
<html:hidden property="step" />
<html:hidden property="activityId" />
<input type="hidden" name="delete" value="true">

<table width="100%" cellPadding="0" cellSpacing="0" vAlign="top" align="left">
<tr><td width="100%" vAlign="top" align="left">
<!--  AMP Admin Logo -->
<jsp:include page="teamPagesHeader.jsp" flush="true" />
<!-- End of Logo -->
</td></tr>
<tr><td width="100%" vAlign="top" align="left">

<table bgColor=#ffffff cellPadding=0 cellSpacing=0 width="780" vAlign="top" align="left" border=0>
	<tr>
		<td class=r-dotted-lg width="10" align="left" vAlign="top">&nbsp;</td>
		<td class=r-dotted-lg align=left vAlign=top>
			<table width="100%" cellSpacing="3" cellPadding="1" vAlign="top" align="left" border=0>
					<tr><td>
					<table width="100%" cellSpacing="1" cellPadding="1" vAlign="top">
						<tr>
							<td>
								<span class=crumb>
								<c:if test="${aimEditActivityForm.pageId == 0}">
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAdmin">Click here to go to Admin Home</digi:trn>
									</bean:define>
									<digi:link href="/admin.do" styleClass="comment" title="<%=translation%>">
										<digi:trn key="aim:AmpAdminHome">
											Admin Home
										</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;
								</c:if>
								<c:if test="${aimEditActivityForm.pageId == 1}">								
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewMyDesktop">Click here to view MyDesktop</digi:trn>
									</bean:define>
									<digi:link href="/viewMyDesktop.do" styleClass="comment" title="<%=translation%>" >
										<digi:trn key="aim:portfolio">
											Portfolio
										</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;								
								</c:if>	

								<c:if test="${aimEditActivityForm.pageId == 2}">
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewMyDesktop">Click here to view MyDesktop</digi:trn>
									</bean:define>
									<digi:link href="/viewMyDesktop.do" styleClass="comment" title="<%=translation%>" >
										<digi:trn key="aim:portfolio">
											Portfolio
										</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;	
									
									<jsp:useBean id="urlParam" type="java.util.Map" class="java.util.HashMap"/>
									<c:set target="${urlParam}" property="ampActivityId">
										<c:out value="${aimEditActivityForm.activityId}"/>
									</c:set>
									<c:set target="${urlParam}" property="tabIndex" value="0"/>
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewProjectDetails">Click here to view Project Details</digi:trn>
									</bean:define>
									<digi:link href="/viewChannelOverview.do" styleClass="comment" name="urlParam" title="<%=translation%>" >
										<digi:trn key="aim:channelOverview">Channel Overview</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;	
								</c:if>									
								
								<c:if test="${aimEditActivityForm.pageId != 2}">
								
								<bean:define id="translation">
									<digi:trn key="aim:clickToViewAddActivityStep1">Click here to goto Add Activity Step 1</digi:trn>
								</bean:define>
								<digi:link href="/addActivity.do?step=1&edit=true" styleClass="comment" title="<%=translation%>" >
								
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
								</digi:link>&nbsp;&gt;&nbsp;						
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAddActivityStep2">Click here to goto Add Activity Step 2</digi:trn>
									</bean:define>
									<digi:link href="/addActivity.do?step=2&edit=true" styleClass="comment" title="<%=translation%>" >						
									<digi:trn key="aim:addActivityStep2">
									Step 2
									</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;			
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAddActivityStep3">Click here to goto Add Activity Step 3</digi:trn>
									</bean:define>
									<digi:link href="/addActivity.do?step=3&edit=true" styleClass="comment" title="<%=translation%>" >						
									<digi:trn key="aim:addActivityStep3">
									Step 3
									</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;							
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAddActivityStep4">Click here to goto Add Activity Step 4</digi:trn>
									</bean:define>
									<digi:link href="/addActivity.do?step=4&edit=true" styleClass="comment" title="<%=translation%>" >						
									<digi:trn key="aim:addActivityStep4">
									Step 4
									</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;			
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAddActivityStep5">Click here to goto Add Activity Step 5</digi:trn>
									</bean:define>
									<digi:link href="/addActivity.do?step=5&edit=true" styleClass="comment" title="<%=translation%>" >												
									<digi:trn key="aim:addActivityStep5">
									Step 5
									</digi:trn>						
									</digi:link>&nbsp;&gt;&nbsp;	
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAddActivityStep6">Click here to goto Add Activity Step 6</digi:trn>
									</bean:define>
									<digi:link href="/addActivity.do?step=6&edit=true" styleClass="comment" title="<%=translation%>" >						
									<digi:trn key="aim:addActivityStep6">
									Step 6
									</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;			
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAddActivityStep7">Click here to goto Add Activity Step 7</digi:trn>
									</bean:define>
									<digi:link href="/addActivity.do?step=7&edit=true" styleClass="comment" title="<%=translation%>" >
									<digi:trn key="aim:addActivityStep7">
									Step 7
									</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;
									<bean:define id="translation">
										<digi:trn key="aim:clickToViewAddActivityStep8">Click here to goto Add Activity Step 8</digi:trn>
									</bean:define>
									<digi:link href="/addActivity.do?step=8&edit=true" styleClass="comment" title="<%=translation%>" >
									<digi:trn key="aim:addActivityStep8">
									Step 8
									</digi:trn>
									</digi:link>&nbsp;&gt;&nbsp;									
								</c:if>
								<digi:trn key="aim:saveActivity">
								Save Activity
								</digi:trn>							
								</span>
							</td>
						</tr>
					</table>
				</td></tr>			
				<tr><td>
					<table width="100%">
						<tr><td>
							<span class=subtitle-blue>
									<digi:trn key="aim:addNewActivity">Add New Activity</digi:trn>
							</span>
						</td></tr>					
						<tr><td>
							&nbsp;
						</td></tr>	
						<tr><td align="center" class="v-name">
							<digi:trn key="aim:activityAlreadyExist">The activity with the given name already exist
							</digi:trn>
						</td></tr>
						<tr><td>
							&nbsp;
						</td></tr>	
						<tr><td>
							&nbsp;
						</td></tr>							
						<tr><td align="center" class="v-name">
							<digi:trn key="aim:overwriteTheActivity">Overwrite the activity?
							</digi:trn>
						</td></tr>
						<tr><td>
							&nbsp;
						</td></tr>													
						<tr><td align="center">
							<input type="button" value="Overwrite" class="dr-menu" onclick="overwrite()"
							name="backButton">
							<input type="button" value="Cancel" class="dr-menu" onclick="cancel()"
							name="submitButton">
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