<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<script language="JavaScript">
	function load() {
		window.print();
	}

	function unload() {}

</script>


<digi:instance property="aimCommitmentbyDonorForm" />

<table width="900" cellspacing=1 cellpadding=1 valign=top align=left>

	<%! int stYr = 0, ind = 0; %> 
	<%! int temp = 0, count = 0; %> 
  <!-- Report Name -->
  <tr>
   <td class="head1-name" align=center>
	<digi:trn key="aim:TrendAnalysisTitle">
	<bean:write name="aimCommitmentbyDonorForm" property="reportName" />
	</digi:trn>
   </td>
  </tr> 


  <!-- Table name-->
  <tr>
   <td class="head2-name" align=center>
	 <bean:write name="aimCommitmentbyDonorForm" property="workspaceType" />&nbsp; <bean:write name="aimCommitmentbyDonorForm" property="workspaceName" />
   </td>
  </tr> 

  <!-- Report data -->
  <tr>
	<td>
		<table width="100%" cellspacing=1 cellpadding=1 valign=top align=left style="border-collapse: collapse">
			<tr><td align="left"><b>
				<digi:trn key="aim:TrendAnalysis">
				   Trend Analysis
				</digi:trn></b>    
			</td></tr> 

			<logic:iterate name="aimCommitmentbyDonorForm"  property="fiscalYearRange" id="fiscalYearRange">
			<% count++ ;%>
			<% 
			   if (stYr == 0) {
				Integer fy = (Integer) fiscalYearRange; 
				stYr = fy.intValue();
			} %>
			</logic:iterate>

			<logic:notEmpty name="aimCommitmentbyDonorForm" property="report"> 
			<logic:iterate name="aimCommitmentbyDonorForm"  property="report" id="report" 
			type="org.digijava.module.aim.helper.Report">
			 <tr colspan=2>
			  <td colspan=2>
			  <table width="100%" cellspacing=1 cellpadding=1 valign=top align=left style="border-collapse: collapse" border=1 >

				<tr>
				 <td  colspan=2>
				  <table>
				   <tr>
				    <td align=left colspan=2>  <b> Donor(s) :</b> 
						<logic:empty name="report" property="donors">&nbsp;
						</logic:empty>
						<logic:notEmpty name="report" property="donors">
						<logic:iterate name="report" id="donors" property="donors"> <%=donors%>	
						</logic:iterate>
						</logic:notEmpty>
					</td>
				   </tr>
	   				<tr>
				     <td align=left colspan=2 >  <b> Title : </b>
					  <bean:write name="report" property="title" />
					 </td>
					</tr>
				  </table>
				 </td>
				</tr>

				<tr>
				 <td width=500>
				  <table style="border-collapse: collapse" border=0 width="100%" cellspacing=1 cellpadding=1 border=1>
					<tr>
					  <td width=200> <b>
						Status : </b><bean:write name="report" property="status" />
					  </td>
					  <td width=200><b>
						Sector :</b>
							<logic:empty name="report" property="sectors">&nbsp;</logic:empty>
							<logic:notEmpty name="report" property="sectors">
							<logic:iterate name="report" id="sectors" property="sectors"> <%=sectors%>	
							</logic:iterate>
							</logic:notEmpty>
					  </td>
					</tr>
					<tr>
					   <td width=200><b>
							Type of Assistance : </b>
							<logic:empty name="report" property="assistance">&nbsp;	</logic:empty>
							<logic:notEmpty name="report" property="assistance">
							<logic:iterate name="report" id="assistance" property="assistance"> <%=assistance%>	
							</logic:iterate>
							</logic:notEmpty>
					   </td>
					   <td>
					     <b>	Level : </b>
						 <bean:write name="report" property="level" />
					   </td>
					</tr>

					<tr>
					  <td width=200> <b>
						Instrument of Funding : </b>
						<logic:empty name="report" property="modality">&nbsp;	</logic:empty>
						<logic:notEmpty name="report" property="modality">
						<logic:iterate name="report" id="modality" property="modality"> <%=modality%>	
						</logic:iterate>
						</logic:notEmpty>
					  </td>
					  <td width=200><b>
						Region :</b>
						<logic:empty name="report" property="regions">&nbsp;</logic:empty>
						<logic:notEmpty name="report" property="regions">
						<logic:iterate name="report" id="regions" property="regions"> <%=regions%>	
						</logic:iterate>
						</logic:notEmpty>
					  </td>
					</tr>

					<tr>
					  <td width=200> <b>
						Start Date : </b> <bean:write name="report" property="startDate" />  </td>
					  <td width=200><b>
						Close Date : </b> <bean:write name="report" property="closeDate" />					  </td>
					</tr>

					<tr>
					  <td width=200> <b>
						Commitment Date : </b> 
						<logic:empty name="report" property="commitmentDate">&nbsp;	</logic:empty>
						<logic:notEmpty name="report" property="commitmentDate">
						<logic:iterate name="report" id="commitmentDate" property="commitmentDate"> <%=commitmentDate%>	
						</logic:iterate>
						</logic:notEmpty>
					  </td>
					  <td width=200><b>
						Total Commit. : </b> 						
						<logic:notEqual name="report" property="acCommitment" value="0">
						<bean:write name="report" property="acCommitment" />
						</logic:notEqual>
					  </td>
					</tr>

				  </table>
				 </td>

				 <td width=500 valign=top >
				 <table>
				  <tr>
	  				 <td align=left>
					 <table cellpadding=2 cellspacing=1 border=1 style="border-collapse: collapse">
					   <tr>
						 <td width=70 align=center height=32>
							Year
						 </td>
						 <td width=120 align=center height=32>
							<digi:trn key="aim:commit">Commit</digi:trn>
						 </td>
						 <td width=120 align=center height=32>
							<digi:trn key="aim:plannedDisb">Planned Disb</digi:trn>
						 </td>
						 <td width=100 align=center height=32>
							<digi:trn key="aim:disbursement">Disbursement</digi:trn>
						 </td>
						 <td width=100 align=center height=32>
							<digi:trn key="aim:expenditure">Expenditure</digi:trn>
						 </td>
					   </tr>
					 </table>			
				   </td>
					   <td align=center width=90 valign=bottom height=35>
				     <table height=35 cellspacing=1 cellpadding=1 valign=top align=center style="border-collapse: collapse" border=1 width=100%>
					   <tr>
					     <td valign=center>
							<digi:trn key="aim:undisbursed">Undisbursed</digi:trn>
						 </td>
					   </tr>
					 </table>
				   </td>

				 </tr>
				</tr>

				<tr>
				<td>
				 <% temp = stYr; %>
			     <table cellpadding=2 cellspacing=1 border=1 style="border-collapse: collapse" valign=top>
			 		<logic:iterate name="report"  property="ampFund" id="ampFund" 	type="org.digijava.module.aim.helper.AmpFund">
					<% ind++; %>

					<% if(ind > count ) {  ind=0;%>
 					 <td width=50> <b>
						Total	</b>
					</td>
					<% } else { %>
	 					 <td width=40>
						<%=temp%>
						<%temp++;%>
					 </td>

					<%} %>
					  <td align=right width=90>
					  <logic:notEqual name="ampFund" property="commAmount" value="0">
						<bean:write name="ampFund" property="commAmount" />
						</logic:notEqual>
					 </td>
					  <td align=right width=90>
						<logic:notEqual name="ampFund" property="plannedDisbAmount" value="0">
						<bean:write name="ampFund" property="plannedDisbAmount" />
						</logic:notEqual>
					  </td>
					  <td align=right width=90>
					   <logic:notEqual name="ampFund" property="disbAmount" value="0">
						<bean:write name="ampFund" property="disbAmount" />
					   </logic:notEqual>
					  </td>
					  <td align=right width=90>
						<logic:notEqual name="ampFund" property="expAmount" value="0">
						<bean:write name="ampFund" property="expAmount" />
						</logic:notEqual>
					  </td>
					</tr>
					</logic:iterate>
				  </table>
				 </td>
 				 <td width=90 valig=bottom>
			      <table cellpadding=2 cellspacing=1 border=1 style="border-collapse: collapse" valign=top>
					<logic:iterate name="report"  property="ampFund" id="ampFund" 	type="org.digijava.module.aim.helper.AmpFund">
					  <tr>
					    <td align=right width=90 height=17>
							<logic:notEqual name="ampFund" property="unDisbAmount" value="0">
							<bean:write name="ampFund" property="unDisbAmount" />
							</logic:notEqual>
						</td>
					  </tr>
					</logic:iterate>
					</table>
				 </td>
				</tr>
				</table>
				 </td>
			 </tr>
			 </table>
			 </td>
			</tr>
			</logic:iterate>
			<tr>
			  <td>
				<table width="900" cellspacing=1 cellpadding=1 valign=top align=left style="border-collapse: collapse" border=1 >
				 <tr>
				 	<td width=100>
					 <b>	Total		</b>
				    </td>
					<td align=right width=330> <b>	Total Commitment :	</b>
					<logic:notEqual name="aimCommitmentbyDonorForm" property="totComm" value="0">
					<bean:write name="aimCommitmentbyDonorForm" property="totComm" />
					</logic:notEqual>
					</td>
					<td width=400  align=right>
					  <% temp = stYr; %>
					 <table cellpadding=2 cellspacing=1 border=1 style="border-collapse: collapse" valign=top >
					 <logic:iterate name="aimCommitmentbyDonorForm"  property="totFund" id="totFund" type="org.digijava.module.aim.helper.AmpFund">
						<tr>
					<% ind++; %>

					<% if(ind > count ) {  ind=0;%>
 					 <td width=50> <b>
						Total	</b>
					</td>
					<% } else { %>
	 					 <td width=40>
						<%=temp%>
						<%temp++;%>
					 </td>
					<%} %>

					  <td align=right width=90>	<b>
						<logic:notEqual name="totFund" property="commAmount" value="0">
						<bean:write name="totFund" property="commAmount" />
						</logic:notEqual>
						</b>
					 </td>
						  <td align=right width=90>	<b>
							<logic:notEqual name="totFund" property="plannedDisbAmount" value="0">
							<bean:write name="totFund" property="plannedDisbAmount" />
							</logic:notEqual>
							</b>
						</td>
						<td align=right width=90>	<b>
							<logic:notEqual name="totFund" property="disbAmount" value="0">
							<bean:write name="totFund" property="disbAmount" />
							</logic:notEqual>
							</b>
						 </td>
						<td align=right width=90>	<b>
							<logic:notEqual name="totFund" property="expAmount" value="0">
							<bean:write name="totFund" property="expAmount" />
							</logic:notEqual>
							</b>
						 </td>

						</tr>
					</logic:iterate>
					  </table>
					</td>
 				 <td width=90 valig=bottom>
			      <table cellpadding=2 cellspacing=1 border=1 style="border-collapse: collapse" valign=top>
					<logic:iterate name="aimCommitmentbyDonorForm"  property="totFund" id="totFund" type="org.digijava.module.aim.helper.AmpFund">
					  <tr>
					    <td align=right width=90 height=17>		<b>
							<logic:notEqual name="totFund" property="unDisbAmount" value="0">
							<bean:write name="totFund" property="unDisbAmount" />
							</logic:notEqual>	</b>
						</td>
					  </tr>
					</logic:iterate>
					</table>
				 </td>

				   </tr>
				 </table>

					  </td>
					    
				  </tr>
				</table>
			  <td>
			</tr>
		   </logic:notEmpty>
		</table> 
	</td>
  </tr> 
  </table>