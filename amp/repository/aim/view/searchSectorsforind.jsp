<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<digi:ref href="css/styles.css" type="text/css" rel="stylesheet" />
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/addActivity.js"/>"></script>
<script language="JavaScript" type="text/javascript" src="<digi:file src="module/aim/scripts/common.js"/>"></script>

<digi:instance property="aimEditActivityForm" />

<script language="JavaScript">
<!--
	function validate() {
			var length = document.aimEditActivityForm.selSectors.length;
			var flag = 0;

			for (i = 0;i < length;i ++) {
				if (document.aimEditActivityForm.selSectors[i].checked == true) {
					flag ++;
					//break;
				}
			}
			if (flag > 1) {
				alert("Please choose only ONE Sector to add....");
				return false;
			}
		return true;
	}

	function addSector() {

		var flag = validate();
		if (flag == false)
			return false;
		document.aimEditActivityForm.target = window.opener.name;
		document.aimEditActivityForm.submit();
		window.close();
		return true;
	}

	function checkEmpty() {
		var flag=true;
		if(trim(document.aimEditActivityForm.keyword.value) == "")
		{
			alert("Please Enter a Keyword....");
			flag=false;
		}
		if(trim(document.aimEditActivityForm.tempNumResults.value) == 0)
		{
			alert("Invalid value at 'Number of results per page'");
			flag=false;
		}

		return flag;
	}
	function checkNumeric(objName,comma,period,hyphen)
	{
		var numberfield = objName;
		if (chkNumeric(objName,comma,period,hyphen) == false)
		{
			numberfield.select();
			numberfield.focus();
			return false;
		}
		else
		{
			return true;
		}
	}

	function chkNumeric(objName,comma,period,hyphen)
	{
// only allow 0-9 be entered, plus any values passed
// (can be in any order, and don't have to be comma, period, or hyphen)
// if all numbers allow commas, periods, hyphens or whatever,
// just hard code it here and take out the passed parameters
		var checkOK = "0123456789" + comma + period + hyphen;
		var checkStr = objName;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		
		for (i = 0;  i < checkStr.value.length;  i++)
		{
			ch = checkStr.value.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
			if (ch != ",")
			allNum += ch;
		}
		if (!allValid)
		{	
			alertsay = "Please enter only numbers in the \"Number of results per page\"."
			alert(alertsay);
			return (false);
		}
	}
	

	function searchSector() {
		if(checkNumeric(document.aimEditActivityForm.tempNumResults	,'','','')==true) 
			{	
	
				var flg=checkEmpty();
				if(flg)
				{
				if (document.aimEditActivityForm.tempNumResults.value == 0) {
					  alert ("Invalid value at 'Number of results per page'");
					  document.aimEditActivityForm.tempNumResults.focus();
					  return false;
				} else {
				 <digi:context name="searchLoc" property="context/module/moduleinstance/searchSectorsforind.do?edit=true"/>
				    document.aimEditActivityForm.action = "<%= searchLoc %>";
				    document.aimEditActivityForm.submit();
					return true;
				}
			}
		}
		else return false;
	}

	function selectSector() {
		<digi:context name="selectLoc" property="context/module/moduleinstance/selectSectorForind.do?edit=true" />
		document.aimEditActivityForm.action = "<%= selectLoc %>";
		document.aimEditActivityForm.submit();
	}

	function load() {
		document.aimEditActivityForm.keyword.focus();
	}

	function unload() {
	}

	function closeWindow() {
		window.close();
	}

-->
</script>

<digi:instance property="aimEditActivityForm" />
<digi:form action="/addSelectedSectorsForind.do" method="post">
<html:hidden property="locationReset" value="false" />
<html:hidden property="fill" />
<input type="hidden" name="edit" value="true" />


<table width="100%" cellSpacing=5 cellPadding=5 vAlign="top" border=0>
	<tr><td vAlign="top">
		<table bgcolor=#f4f4f2 cellPadding=5 cellSpacing=5 width="100%" class=box-border-nopadding>
			<tr>
				<td align=left vAlign=top>
					<table bgcolor=#f4f4f2 cellPadding=0 cellSpacing=0 width="100%" class=box-border-nopadding>
						<tr bgcolor="#006699">
							<td vAlign="center" width="100%" align ="center" class="textalb" height="20">
								<digi:trn key="aim:searchSectors">
								Search Sectors</digi:trn>
							</td></tr>

						<tr>
							<td align="center" bgcolor=#ECF3FD>
								<table cellSpacing=2 cellPadding=2>
									<tr>
										<td>
											<digi:trn key="aim:enterKeyword">
											Enter a keyword</digi:trn>
										</td>
										<td>
											<html:text property="keyword" styleClass="inp-text" />
										</td>
									</tr>
									<tr>
										<td>
											<digi:trn key="aim:numResultsPerPage">
											Number of results per page</digi:trn>
										</td>
										<td>
											<html:text property="tempNumResults" size="2" styleClass="inp-text" />
										</td>
									</tr>
									<tr>
										<td align="center" colspan=2>
											<html:button  styleClass="dr-menu" property="submitButton" onclick="return searchSector()">
												<digi:trn key="btn:search">Search</digi:trn> 
											</html:button>
										&nbsp;&nbsp;
											<html:button  styleClass="dr-menu" property="submitButton" onclick="return selectSector()">
												<digi:trn key="btn:back">Back</digi:trn> 
											</html:button>
										&nbsp;&nbsp;
										<html:button  styleClass="dr-menu" property="submitButton" onclick="closeWindow()">
											<digi:trn key="btn:close">Close</digi:trn> 
										</html:button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align=left vAlign=top>
					<table bgcolor=#f4f4f2 cellPadding=0 cellSpacing=0 width="100%" class=box-border-nopadding>
						<tr bgcolor="#006699">
							<td vAlign="center" width="100%" align ="center" class="textalb" height="20">
								<digi:trn key="aim:SectorList">
								List of Sectors</digi:trn>
					
							</td>
						</tr>
<!-- 1 -->
						<logic:notEmpty name="aimEditActivityForm" property="pagedCol">
						<tr>
							<td align=left vAlign=top>
							<table width="100%" cellPadding=3 cellspacing=0>
							<logic:iterate name="aimEditActivityForm" id="searchedSectors" property="pagedCol"
									type="org.digijava.module.aim.helper.ActivitySector">
										<tr>
											<td bgcolor=#ECF3FD>
												&nbsp;&nbsp;
												<html:multibox property="selSectors">
													<bean:write name="searchedSectors" property="sectorId"/>
												</html:multibox>&nbsp;
												[<bean:write name="searchedSectors" property="sectorName"/>]
											</td>
										</tr>
									</logic:iterate>
									<tr>
										<td align="center">
											<table cellPadding=5>
												<tr>
													<td>
														<html:button  styleClass="dr-menu" property="submitButton" onclick="return addSector()">
															<digi:trn key="btn:add">Add</digi:trn> 
														</html:button>
													</td>
													<td>
														<html:reset  styleClass="dr-menu" property="submitButton">
															<digi:trn key="btn:clear">Clear</digi:trn> 
														</html:reset>
													</td>
													<td>
														<html:button  styleClass="dr-menu" property="submitButton" onclick="closeWindow()">
															<digi:trn key="btn:close">Close</digi:trn> 
														</html:button>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</logic:notEmpty>
					<logic:empty name="aimEditActivityForm" property="searchedSectors">
					<tr><td><br><br>&nbsp;&nbsp;&nbsp;
					<digi:trn key="aim:noRecordsFoundMatching">No records found, matching to your query......</digi:trn>
					<br><br>
					</td></tr>
					</logic:empty>

					<logic:notEmpty name="aimEditActivityForm" property="pages">
							<tr>
								<td align="center">
									<table width="90%">
									<tr><td>
									<digi:trn key="aim:pages">Pages</digi:trn>
									<logic:iterate name="aimEditActivityForm" property="pages" id="pages" type="java.lang.Integer">
										<jsp:useBean id="urlParams1" type="java.util.Map" class="java.util.HashMap"/>
										<c:set target="${urlParams1}" property="page">
											<%=pages%>
										</c:set>
										<c:set target="${urlParams1}" property="locSelReset" value="false"/>
										<c:set target="${urlParams1}" property="edit" value="true"/>

										<c:if test="${aimEditActivityForm.currentPage == pages}">
											<font color="#FF0000"><%=pages%></font>
										</c:if>
										<c:if test="${aimEditActivityForm.currentPage != pages}">
											<bean:define id="translation">
												<digi:trn key="aim:clickToViewNextPage">Click here to goto Next Page</digi:trn>
											</bean:define>
											<digi:link href="/searchSectors.do" name="urlParams1" title="<%=translation%>" >
												<%=pages%>
											</digi:link>
										</c:if>
										|&nbsp;
									</logic:iterate>
									</td></tr>
									</table>
								</td>
							</tr>
					</logic:notEmpty>
					</table>
				</td>
			</tr>
		</table>
	</td></tr>
</table>


<bean:write name="aimEditActivityForm" property="orgType" />
</digi:form>