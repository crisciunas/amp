<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<bean:define id="columnReport" name="viewable" type="org.dgfoundation.amp.ar.ColumnReportData" scope="request" toScope="page"/>
<bean:define id="reportMeta" name="reportMeta" type="org.digijava.module.aim.dbentity.AmpReports" scope="session" toScope="page"/>

<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/arFunctions.js"/>"></script>

<!-- generate total row -->

<bean:define id="viewable" name="columnReport" type="org.dgfoundation.amp.ar.Viewable" scope="page" toScope="request"/>
<jsp:include page="TrailCells.jsp"/>

<%int rowIdx = 2;%>

<!-- generate report data -->

<logic:notEqual name="reportMeta" property="hideActivities" value="true">	
<logic:iterate name="columnReport" property="ownerIds" id="ownerId" scope="page">
<logic:equal name="columnReport" property="canDisplayRow" value="true">
<%
	rowIdx++;	
	String width="0";
%>

<% 
	if(columnReport.getLevelDepth()<=2){
		request.setAttribute("pading","20px");
	}
	if (columnReport.getLevelDepth()==3){
		request.setAttribute("pading","30px");
	}
	if (columnReport.getLevelDepth()==4){
		request.setAttribute("pading","35px");
	}
%>
<%if (rowIdx%2==0){ %>
	<tr bgcolor="#FFFFFF" height="16px"  onmousedown="setPointerhtml(this, <%=rowIdx%>, 'click', '#FFFFFF', '#FFFFFF', '#A5BCF2');" onMouseover="this.style.backgroundColor='#A5BCF2'" onMouseout="this.style.background='#FFFFFF'">
<%}else{%>
	<tr bgcolor="#DBE5F1" height="16px" onmousedown="setPointerhtml(this, <%=rowIdx%>, 'click', '#DBE5F1', '#DBE5F1', '#A5BCF2');" onMouseover="this.style.backgroundColor='#A5BCF2'" onMouseout="this.style.background='#DBE5F1'">
<%}%>
	<logic:iterate name="columnReport" property="items" id="column" scope="page">
		<bean:define id="viewable" name="column" type="org.dgfoundation.amp.ar.Viewable" scope="page" toScope="request"/>
		<bean:define id="ownerId" name="ownerId" type="java.lang.Long" scope="page" toScope="request"/>
		<jsp:include page="<%=viewable.getViewerPath()%>"/>
	</logic:iterate>	
</tr>
</logic:equal>
</logic:iterate>
</logic:notEqual>
