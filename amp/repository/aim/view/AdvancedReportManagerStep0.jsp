<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>


<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/advanceReportManager.js"/>"></script>

<script language="JavaScript">

function gotoStep() {

/*	if(document.aimAdvancedReportForm.removeColumns == null)
		alert(" Please select columns to generate report ");
	else
	{*/
		<digi:context name="step" property="context/module/moduleinstance/advancedReportManager.do?check=SelectCols" />
		document.aimAdvancedReportForm.action = "<%= step %>";
		document.aimAdvancedReportForm.target = "_self";
		document.aimAdvancedReportForm.submit();
//	}
}

</script>

<digi:instance property="aimAdvancedReportForm" />
<digi:form action="/advancedReportManager.do" method="post">


<html:hidden property="moveColumn"/>

<TABLE cellSpacing=0 cellPadding=0 align="center" vAlign="top" border=0 width="100%">
<tr>
	<td>
		<jsp:include page="teamPagesHeader.jsp" flush="true" />
	</td>
</tr>

<tr>

<td width="100%" vAlign="top" align="left">
<table bgColor=#ffffff cellPadding=0 cellSpacing=0 width="770" vAlign="top" align="left" border=0>
	<tr>

	<td class=r-dotted-lg align=left vAlign=top >	&nbsp;</td>
	<td>
		<table>
			<tr>
				<td>
					<table cellPadding=5 cellSpacing=0 width="100%">
						<tr>
							<td height=33><span class=crumb>
								<bean:define id="translation">
									<digi:trn key="aim:clickToViewMyDesktop">Click here to view MyDesktop</digi:trn>
								</bean:define>
								<digi:link href="/viewMyDesktop.do" styleClass="comment" title="<%=translation%>" >
								<digi:trn key="aim:portfolio">
									Portfolio
								</digi:trn>
								</digi:link>&nbsp;&gt;&nbsp;
								<digi:trn key="aim:reportBuilder:selectReportType">
									Report Builder : Select Report Type
								</digi:trn>					
								&gt;</span>		
							</td>
						</tr>
					</table>	
				</td>
			</tr>
		 	<tr>

				<td height=16 vAlign=right align=center>
					<span class=subtitle-blue>
					<digi:trn key="aim:reportBuilder:selectReportType">
						Report Builder : Select Report Type
					</digi:trn>	
					</span>
				</td>
			</tr>
			<tr colspan="2">
				<td class=box-title align="right" valign="top">
					<img src="module/aim/images/arrow-014E86.gif"><digi:trn key="aim:reportBuilder:reportTypePleaseSelect">Report Type : Please Select</digi:trn>
				<td>
			</tr>
			<TR>
			<TD vAlign="top" align="center">
				<TABLE width="100%" cellSpacing=0 cellPadding=0 vAlign="top" align="left" bgcolor="#f4f4f4" class="box-border-nopadding">
					<TR>
						<TD bgcolor="#f4f4f4">
							<TABLE width="100%" cellSpacing=1 cellPadding=0 valign="top" align="left" bgcolor="#f4f4f4">
								<tr width="100%" valign="top">
									<td height="20">
										<table bgcolor="#f4f4f4" align="left" valign="bottom" cellPadding=0 cellspacing=1 height="20">
											<tr>
											<!--this one-->
											<td noWrap align=left> 
													<bean:define id="translation">
														<digi:trn key="aim:clickToSelectReportType">Click here to Select Report Type</digi:trn>
													</bean:define>
													<digi:link href="/advancedReportManager.do~check=forward" styleClass="sub-nav" title="<%=translation%>"  >
														1 :   <digi:trn key="aim:SelectReportType2">Select Report Type</digi:trn>
													</digi:link>
												</td>
											<!--ends here-->
												<td noWrap align=left> 
													<bean:define id="translation">
														<digi:trn key="aim:clickToSelectColumns">Click here to Select Columns</digi:trn>
													</bean:define>
													<digi:link href="/advancedReportManager.do?check=SelectCols" styleClass="sub-nav" title="<%=translation%>" >
														2 :   <digi:trn key="aim:SelectColumns2">Select Columns</digi:trn>
													</digi:link>
												</td>							
												<td noWrap align=left>
													<bean:define id="translation">
														<digi:trn key="aim:clickToselectrows/hierarchies" >Click here to select rows/hierarchies</digi:trn>
													</bean:define>
													<digi:link href="/advancedReportManager.do?check=SelectRows"  styleClass="sub-nav" title="<%=translation%>" >
														3 : <digi:trn key="aim:SelectRows/hierarchies">Select rows/hierarchies</digi:trn>
													</digi:link>
												</td>										
												<td noWrap align=left>
													<bean:define id="translation">
														<digi:trn key="aim:clickToSelectMeasures">Click here to Select Measures</digi:trn>
													</bean:define>
													<digi:link href="/advancedReportManager.do?check=SelectMeasures"  styleClass="sub-nav" title="<%=translation%>" > 
													4 : <digi:trn key="aim:SelectMeasures">Select Measures</digi:trn>
													</digi:link>
												</td>											
												
											</tr>
										</table>	
									</td>
								</tr>
								<TR>

									<td noWrap valign=top align=left>
									 <table cellpadding=0 cellspacing=1 valign=top align=left>	<tr>	
									 <td noWrap align=left> 
													<bean:define id="translation">
														<digi:trn key="aim:clickToViewReportDetails">Click here to view Report Details</digi:trn>
													</bean:define>
													<digi:link href="/advancedReportManager.do?check=4"  styleClass="sub-nav" title="<%=translation%>" >
														5 : <digi:trn key="aim:ReportDetails">Report Details</digi:trn>
													</digi:link>
												</td>
									 <td valign=top>
										<bean:define id="translation">
											<digi:trn key="aim:clickToGenerateReport">Click here to Generate Reports</digi:trn>
										</bean:define>
										<digi:link href="/advancedReportManager.do?check=forward"  styleClass="sub-nav" title="<%=translation%>" onclick="javascript:gotoStep()">
										6 : <digi:trn key="aim:SaveReport">Save Report</digi:trn>
										</digi:link>
										</td>	
										<!--<td noWrap valign=top align=left>
										<bean:define id="translation">
											<digi:trn key="aim:clickToGenerateReport">Click here to Generate Chart</digi:trn>
										</bean:define>
										<digi:link href="/advancedReportManager.do?check=forward"  styleClass="sub-nav" title="<%=translation%>" onclick="javascript:alert('Charts Coming Soon...');">
										6 : <digi:trn key="aim:GenerateChart">Generate Chart</digi:trn>
										</digi:link>
										</td>-->	
										</tr>	</table>
									</td>	
								</tr>

								<TR bgColor=#f4f4f2>
									<TD vAlign="top" align="left" width="100%">
									</TD>
								</TR>				

								<TR bgColor=#f4f4f2>
									<TD vAlign="top" align="center" width="100%" bgColor=#f4f4f2>
										<TABLE width="98%" cellPadding=0 cellSpacing=0 vAlign="top" align="center" bgColor=#f4f4f2 >
											<TR>
												<TD width="100%" align="center"  valign=top>
                                                    <br>
                                                    <br>
                                                    <br>
<!-- Radios -->
		
			<table cellPadding=0 cellSpacing=0 vAlign="top" align="center" width="400" bgColor=#f4f4f2 border="0">
			<tr height="120">
			<td>&nbsp;&nbsp;&nbsp;
			</td>
			<td>

			<table cellPadding=0 cellSpacing=1 bgColor=#f4f4f2 border="0">
				<tr>
					<td>
						<html:radio property="reportType" value="donor" >
							<digi:trn key="aim:donorReport">
								Donor Report (Donor Funding)
							</digi:trn>
						</html:radio>
					</td>
				</tr>
				<tr>
					<td>
						<html:radio property="reportType" value="regional" >
							<digi:trn key="aim:regionalReport">
								Regional Report (Regional Funding)
							</digi:trn>
						</html:radio>
					</td>
				</tr>
				<tr>
					<td>
						<html:radio property="reportType" value="component" >
							<digi:trn key="aim:componentReport">
								Component Report (Component Funding)
							</digi:trn>
						</html:radio>
					</td>
				</tr>
				<logic:notEmpty name="SA" scope="application">
					<tr>
					<td>
						<html:radio property="reportType" value="contribution" >
							<digi:trn key="aim:contributionReport">
								Contribution Report (Activity Contributions)
							</digi:trn>
						</html:radio>
					</td>
					</tr>
				</logic:notEmpty>				
			</table>
			</td>
			</tr>
			</table>

                                                    <p>&nbsp;</p>
                                                    <p>&nbsp;</p>
												</TD>
											</TR>
											<tr>
												<td align="right">
													<html:button  styleClass="dr-menu" property="submitButton"  onclick="return quitAdvRptMngr()">
														<digi:trn key="btn:cancel">Cancel</digi:trn> 
													</html:button>
													
													<html:button  styleClass="dr-menu" property="submitButton"  onclick="javascript:gotoStep()">
														<digi:trn key="btn:next">Next</digi:trn> 
													</html:button>

												</td>
											</tr>
										</TABLE>
									</TD>
								</TR>	
							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</TD>
			</TR>
		</table>
	</td>	
	<td class=r-dotted-lg align=left vAlign=top >	&nbsp;</td>
</tr>
</table>
</td>	
</TR>
</TABLE>


</digi:form>
