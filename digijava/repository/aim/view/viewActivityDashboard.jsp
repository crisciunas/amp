<%@ page pageEncoding="UTF-8" %>
<%@ page import = "org.digijava.module.aim.helper.ChartGenerator" %>
<%@ page import = "java.io.PrintWriter" %>

<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<script language="Javascript">
	function showPrinterFriendly(actId,chartType) {
		<digi:context name="ptUrl" property="context/module/moduleinstance/printableActivityChart.do" />
		var url = "<%=ptUrl%>?ampActivityId="+actId+"&cType="+chartType;
	 	openURLinWindow(url,650,450);
	}

</script>


<%
	Long actId = (Long) request.getAttribute("actId");
	
	String actPerfChartFileName = ChartGenerator.getActivityPerformanceChartFileName(
						 actId,session,new PrintWriter(out),370,350);

	String actPerfChartUrl = null;
	if (actPerfChartFileName != null) {
		actPerfChartUrl = request.getContextPath() + "/aim/DisplayChart.img?filename=" + actPerfChartFileName;
	}

	
	String actRiskChartFileName = ChartGenerator.getActivityRiskChartFileName(
						 actId,session,new PrintWriter(out),370,350);

	String actRiskChartUrl = null;

	if (actRiskChartFileName != null)  {
		actRiskChartUrl = request.getContextPath() + "/aim/DisplayChart.img?filename=" + actRiskChartFileName;
	}
%>




<TABLE cellSpacing=0 cellPadding=0 align="center" vAlign="top" border=0 width="100%">
<TR>
	<TD vAlign="top" align="center">
		<!-- contents -->

			<TABLE width="99%" cellSpacing=0 cellPadding=0 vAlign="top" align="center" class="box-border-nopadding">
			<TR><TD>

			<table width="100%" cellspacing="2" cellpadding="2" valign="top" align="left" border=0>
			
			<tr>
				<td width="50%" align="center" class="textalb" height="20" bgcolor="#336699">
					Activity - Performance
				</td>
				<td width="50%" align="center" class="textalb" height="20" bgcolor="#336699">
					Activity - Risk
				</td>
			</tr>
			
	<tr>
		<td width="50%">
			<% if (actPerfChartUrl != null) { %>
				<img src="<%= actPerfChartUrl %>" width=370 height=350 border=0 usemap="#<%= actPerfChartFileName %>"><br><br>
				<div align="center">		  
				<input type="button" class="buton" value="Printer Friendly Version" 
				onclick="javascript:showPrinterFriendly('<%=actId%>','P')">
				</div>
			<% } else { %>
				<br><span class="red-log"><digi:trn key="aim:noDataPresentFor">No data present for</digi:trn>
						  <digi:trn key="aim:activityPerformanceChart">Activity-Performance chart</digi:trn>
						  </span><br><br>
			<% } %>
		</td>
		<td width="50%">
			<% if (actRiskChartUrl != null) { %>
				<img src="<%= actRiskChartUrl %>" width=370 height=350 border=0 usemap="#<%= actRiskChartFileName %>">
				<br><br>
				<div align="center">
				<input type="button" class="buton" value="Printer Friendly Version" 
				onclick="javascript:showPrinterFriendly('<%=actId%>','R')">
				</div>		  
			<% } else { %>
				<br><span class="red-log"><digi:trn key="aim:noDataPresentFor">No data present for</digi:trn>
						  <digi:trn key="aim:activityRiskChart">Activity-Risk chart</digi:trn>
						  </span><br><br>
			<% } %>		
		</td>
	</tr>
	</tr>
</table>
			
			
			</TD></TR>
			</TABLE>
		<!-- end -->
	</TD>
</TR>
<TR><TD>&nbsp;</TD></TR>
</TABLE>
