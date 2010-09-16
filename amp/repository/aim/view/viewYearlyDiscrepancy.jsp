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
<script language="Javascript">
<!--
	
	function formSubmit()
	{
		document.aimYearlyDiscrepancyForm.submit();
	}		
	
	
	

-->
function fnEditProject(id)
{
	<digi:context name="addUrl" property="context/module/moduleinstance/editActivity.do" />
	   document.location.href="<%=addUrl%>?pageId=1&action=edit&step=3&surveyFlag=true&activityId=" + id;
}

function preview(id)
{
	<digi:context name="addUrl" property="context/module/moduleinstance/viewActivityPreview.do" />
	  var urlToGo = "<%=addUrl%>~pageId=2~activityId=" + id;
	    document.location.href = urlToGo;
}

</script>

<digi:errors/>
<digi:instance property="aimYearlyDiscrepancyForm" />
<digi:context name="digiContext" property="context"/>

<logic:equal name="aimYearlyDiscrepancyForm" property="sessionExpired" value="true">
	<jsp:include page="../../../repository/aim/view/sessionExpired.jsp" flush="true" />
</logic:equal>

<logic:equal name="aimYearlyDiscrepancyForm" property="sessionExpired" value="false">

<jsp:useBean id="urlSubTabs" type="java.util.Map" class="java.util.HashMap"/>

<c:set target="${urlSubTabs}" property="ampActivityId">
	<bean:write name="aimYearlyDiscrepancyForm" property="ampActivityId"/>
</c:set>

<c:set target="${urlSubTabs}" property="ampFundingId">
	<bean:write name="aimYearlyDiscrepancyForm" property="ampFundingId"/>
</c:set>
<c:set target="${urlSubTabs}" property="tabIndex" >
	<bean:write name="aimYearlyDiscrepancyForm" property="tabIndex"/>
</c:set>
<c:set target="${urlSubTabs}" property="transactionType" value="0"/>

<jsp:useBean id="urlFinancialOverview" type="java.util.Map" class="java.util.HashMap"/>
<c:set target="${urlFinancialOverview}" property="ampActivityId">
	<bean:write name="aimYearlyDiscrepancyForm" property="ampActivityId"/>
</c:set>
<c:set target="${urlFinancialOverview}" property="ampFundingId">
	<bean:write name="aimYearlyDiscrepancyForm" property="ampFundingId"/>
</c:set>
<c:set target="${urlFinancialOverview}" property="tabIndex">
	<bean:write name="aimYearlyDiscrepancyForm" property="tabIndex"/>
</c:set>

<jsp:useBean id="urlAll" type="java.util.Map" class="java.util.HashMap"/>
<c:set target="${urlAll}" property="ampActivityId">
	<bean:write name="aimYearlyDiscrepancyForm" property="ampActivityId"/>
</c:set>
<c:set target="${urlAll}" property="tabIndex" >
	<bean:write name="aimYearlyDiscrepancyForm" property="tabIndex"/>
</c:set>

<jsp:useBean id="urlDiscrepancy" type="java.util.Map" class="java.util.HashMap"/>
<c:set target="${urlDiscrepancy}" property="ampActivityId">
	<bean:write name="aimYearlyDiscrepancyForm" property="ampActivityId"/>
</c:set>
<c:set target="${urlDiscrepancy}" property="tabIndex" >
	<bean:write name="aimYearlyDiscrepancyForm" property="tabIndex"/>
</c:set>
<c:set target="${urlDiscrepancy}" property="transactionType" value="0"/>
				<tr> 
			        <td width="100%" nowrap >
			        	<table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="box-border-nopadding">
							<tr bgcolor="#222E5D"> 
						  		<td height="20" style="color: #C9C9C7">&nbsp; &nbsp; &nbsp;
					  <c:set var="translation">
							<digi:trn key="aim:clickToViewFinancialOverview">Click here to view Financial Overview</digi:trn>
						</c:set>
						  			<digi:link href="/viewFinancialOverview.do" name="urlFinancialOverview" styleClass="sub-nav2" title="${translation}" >
						  				<digi:trn key="aim:overview">OVERVIEW</digi:trn> 
						  			</digi:link> | 
						<c:set var="translation">
							<digi:trn key="aim:clickToViewCommitments">Click here to view Commitments</digi:trn>
						</c:set>
						  			<digi:link href="/viewYearlyInfo.do" name="urlSubTabs" styleClass="sub-nav2" title="${translation}" >
						  				<digi:trn key="aim:commitments">COMMITMENTS</digi:trn>
						  			</digi:link> | 
						  			<field:display feature="Disbursement Orders" name="Disbursement Orders Tab">
                                        <c:set target="${urlSubTabs}" property="transactionType" value="4"/>
					<c:set var="translation">
						<digi:trn key="aim:clickToViewDisbursementOrders">Click here to view Disbursement Orders</digi:trn>
					</c:set>
					<digi:link href="/viewYearlyInfo.do" name="urlSubTabs" styleClass="sub-nav2" title="${translation}" >
					<digi:trn key="aim:disbursementOrdersTab">DISBURSEMENT ORDERS</digi:trn>
					</digi:link>|
                                        </field:display>
						  			<c:set target="${urlSubTabs}" property="transactionType" value="1"/>
						<c:set var="translation">
							<digi:trn key="aim:clickToViewDisbursements">Click here to view Disbursements</digi:trn>
						</c:set>
						  			<digi:link href="/viewYearlyInfo.do" name="urlSubTabs" styleClass="sub-nav2" title="${translation}" >
						  					<digi:trn key="aim:disbursements">DISBURSEMENTS</digi:trn>
						  			</digi:link> |
						  			<c:set target="${urlSubTabs}" property="transactionType" value="2"/>
					<c:set var="translation">
						<digi:trn key="aim:clickToViewExpenditures">Click here to view Expenditures</digi:trn>
					</c:set>
					  		<feature:display module="Funding" name="Expenditures">
					  			
					  				<digi:link href="/viewYearlyInfo.do" name="urlSubTabs" styleClass="sub-nav2" title="${translation}" >
					  					<digi:trn key="aim:expenditures">EXPENDITURES</digi:trn>
					  				</digi:link> | 
						</feature:display>					
									<span class="sub-nav2-selected">
					  					<digi:trn key="aim:discrepancy">DISCREPANCY</digi:trn>
					  				</span>|
					  			 	
								<!-- <c:set var="translation">
									<digi:trn key="aim:clickToViewAllYearlyDiscrepancy">Click here to view All Yearly Discrepancy</digi:trn>
								</c:set> -->
					  				<!-- <digi:link href="/viewYearlyDiscrepancyAll.do" name="urlDiscrepancy" styleClass="sub-nav2" title="${translation}" >
										<digi:trn key="aim:discrepancyAll">DISCREPANCY ALL</digi:trn>
									</digi:link> -->
									 
									<c:set var="translation">
										<digi:trn key="aim:clickToViewYearlyComparisons">Click here to view Yearly Comparisons</digi:trn>
									</c:set>
					  				<digi:link href="/viewYearlyComparisons.do" name="urlAll" styleClass="sub-nav2" title="${translation}" >
										<digi:trn key="aim:all">ALL</digi:trn>
									</digi:link>
									
			    				</td>
		    			  </tr>
			    		</table>
			    	</td>		
				</tr>		
				<tr bgcolor="#F4F4F2" > 
					<td>
					<jsp:useBean id="urlFinancingBreakdown" type="java.util.Map" class="java.util.HashMap"/>
					<c:set target="${urlFinancingBreakdown}" property="ampActivityId">
					<bean:write name="aimYearlyDiscrepancyForm" property="ampActivityId"/>
					</c:set>
					<c:set target="${urlFinancingBreakdown}" property="tabIndex" >
						<bean:write name="aimYearlyDiscrepancyForm" property="tabIndex"/>
					</c:set>
					<c:set var="translation">
						<digi:trn key="aim:clickToViewFinancialProgress">Click here to view Financial Progress</digi:trn>
					</c:set>
					<digi:link href="/viewFinancingBreakdown.do" name="urlFinancingBreakdown" styleClass="comment" title="${translation}" >
						<digi:trn key="aim:financialProgress">Financial Progress</digi:trn>
					</digi:link> &gt; 
					<digi:trn key="aim:discrepancyPerspective">Discrepancy Perspective</digi:trn>  
				   	</td>
				</tr>
				<tr bgcolor="#F4F4F2" > 
					<td align="right" >
						<table width="90%"  border="0" align="right" cellpadding="0" cellspacing="0" bgcolor="#F4F4F2">
				      		<tr bgcolor="#F4F4F2"> 
				        		<td  bgcolor="#F4F4F2">&nbsp; </td>
				        		<td align="right" bgcolor="#F4F4F2">
				        			<digi:form action="/viewYearlyDiscrepancyFilter.do" name="aimYearlyDiscrepancyForm" type="org.digijava.module.aim.form.QuarterlyDiscrepancyForm" method="post">
				        			<table  border="0" cellspacing="0" cellpadding="0">
				            			<tr> 
				              				<td>&nbsp;</td>
				              				<td height="30">
				              					<strong>
				              					<digi:trn key="aim:transactionType">Transaction Type</digi:trn>
				              					:</strong>
				              				</td>
				              				<td>&nbsp;</td>
				              				<td> 
				              					<html:select property="transactionType" styleClass="dr-menu" onchange="formSubmit()">
																	<html:option value="0"><digi:trn key="aim:commitments">Commitments</digi:trn> </html:option>
																	<html:option value="1"><digi:trn key="aim:disbursements">Disbursements</digi:trn> </html:option>
																	<html:option value="2"><digi:trn key="aim:expedintures">Expenditures</digi:trn> </html:option>
																</html:select>
																<html:hidden property="ampActivityId" />
																<html:hidden property="tabIndex" />
				                			</td>
				              				<td>&nbsp;</td>
				            			</tr>
				          			</table>
				          			
				          		</td>
				      		</tr>
				        </table>
				        <div align="right"></div>
				    </td>
				</tr>
				<tr bgcolor="#F4F4F2" > 
					<td valign="top" >
						<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F4F4F2">
				      		<tr bgcolor="#F4F4F2"> 
				        		<td bgcolor="#F4F4F2">
				        			<table border="0" cellpadding="0" cellspacing="0" bgcolor="#F4F4F2">
				            			<tr bgcolor="#F4F4F2"> 
				              				<td height="17" nowrap bgcolor="#C9C9C7" class="box-title">
				              					&nbsp;<digi:trn key="aim:yearlyAmounts">Yearly Amounts</digi:trn>
				              				</td>
				              				<td width="17" height="17" 
											background="<%= digiContext %>/repository/aim/images/corner-r.gif">
				            			</tr>
				          			</table>
				          		</td>
				      		</tr>
				      		<tr> 
				        		<td bgcolor="#FFFFFF" class="box-border"> 
				          			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				            			<tr> 
				              				<td valign="middle"> 
				              					<logic:equal name="aimYearlyDiscrepancyForm" property="currencyPresent" value="true">
			                           			<strong><digi:trn key="aim:currency">Currency</digi:trn>:</strong>
			                           			</logic:equal>
				                			</td>
				              				<td>
				              					<logic:equal name="aimYearlyDiscrepancyForm" property="currencyPresent" value="true">
				                      			<html:select property="currency" styleClass="dr-menu">
				                      				<html:optionsCollection name="aimYearlyDiscrepancyForm" property="currencies" value="currencyCode" label="currencyName"/>
												</html:select>
												</logic:equal>
				               				</td>
				              				<td>
				              					<logic:equal name="aimYearlyDiscrepancyForm" property="calendarPresent" value="true">
														<strong>
															<digi:trn key="aim:calendarType">Calendar Type</digi:trn> :
														</strong>
												</logic:equal>
				              				</td>
				              				<td> 
								               <logic:equal name="aimYearlyDiscrepancyForm" property="calendarPresent" value="true">
				                      			<html:select property="fiscalCalId" styleClass="dr-menu">
													<html:option value="4">Gregorian</html:option>
													<html:option value="1">Eth. Calendar</html:option>
													<html:option value="-1">Eth. Fiscal Year</html:option>
												</html:select>
												</logic:equal>
				              				</td>
				              				<td>
				              					<logic:equal name="aimYearlyDiscrepancyForm" property="yearRangePresent" value="true">
	                              					<strong>
	                              						<digi:trn key="aim:year">Year</digi:trn>&nbsp;
	                              					</strong>
	                              				</logic:equal>
				              				</td>
				              				<td>
				              					<logic:equal name="aimYearlyDiscrepancyForm" property="yearRangePresent" value="true">
							                      			<strong><digi:trn key="aim:from">From</digi:trn>:</strong>
							                     </logic:equal>
				              				</td>
							              	<td> 
							                	<logic:equal name="aimYearlyDiscrepancyForm" property="yearRangePresent" value="true">
					                      			<b>
					                      			<html:select property="fromYear" styleClass="dr-menu">
					                      				<html:optionsCollection name="aimYearlyDiscrepancyForm" property="years" value="year" label="year"/>
													</html:select>
													</b>
												</logic:equal>
							              	</td>
							              	<td>
							              		<logic:equal name="aimYearlyDiscrepancyForm" property="yearRangePresent" value="true">
					                      			<strong><digi:trn key="aim:to">To</digi:trn>:</strong>
					                      		</logic:equal>
							              	</td>
							              	<td>
							              		<logic:equal name="aimYearlyDiscrepancyForm" property="yearRangePresent" value="true">
					                      			<b> 
					                        		<html:select property="toYear" styleClass="dr-menu">
														<html:optionsCollection name="aimYearlyDiscrepancyForm" property="years" value="year" label="year"/>
													</html:select>
					                        		</b>
					                        	</logic:equal> 
							              	</td>
							              	<td> 
							              	<logic:equal name="aimYearlyDiscrepancyForm" property="goButtonPresent" value="true">
							              		<c:set var="translation">
							              			<digi:trn key="btn:go">
							              				GO
							              			</digi:trn>
							              		</c:set>
												<html:submit value="${translation}" styleClass="dr-menu"/>
											</logic:equal> 
							                </td>
							              	<td>&nbsp;</td>
				            			</tr>
				          			</table>
				          			<table width="100%"  border="0" cellpadding="3" cellspacing="1" class="box-border">
				            			<tr bgcolor="#DDDDDB" > 
				              				<td height="30" bgcolor="#DDDDDB">
				              					<div align="center">
				              						<digi:trn key="aim:fiscalYear">Fiscal Year </digi:trn>
				              					</div>
				                  			</td>
							              	
							              	<td bgcolor="#DDDDDB">
							              		<div align="center"> 
							                  		<p><digi:trn key="aim:donorPlanned">Donor Planned</digi:trn></p>
							                	</div>
							                </td>
							              	<td bgcolor="#DDDDDB">
							              		<div align="center"><digi:trn key="aim:implAgencyPlanned">Impl. Agency Planned</digi:trn></div>
							              	</td>
							              	<td bgcolor="#DDDDDB">
							              		<div align="center">
							              			<digi:trn key="aim:MOFED">Mofed</digi:trn> <digi:trn key="aim:Planned">Planned </digi:trn>
							              		</div>
							              	</td>
							              	<td bgcolor="#DDDDDB">
							              		<div align="center">
							              			<digi:trn key="aim:donorActuals">Donor Actuals</digi:trn>
							              		</div>
							              	</td>
							              	<td bgcolor="#DDDDDB">
							              		<div align="center"><digi:trn key="aim:implAgencyActuals">Impl. Agency Actuals</digi:trn></div>
							              	</td>
							              	<td bgcolor="#DDDDDB">
							              		<div align="center">
							              			<digi:trn key="aim:MOFED">Mofed</digi:trn> <digi:trn key="aim:actuals">Actuals </digi:trn>
							              		</div>
							              	</td>
				            			</tr>
				            			<logic:empty name="aimYearlyDiscrepancyForm" property="discrepancies" >
			                        		<tr valign="top"> 
			                          			<td colspan="8" align="center"><span class="note"><digi:trn key="aim:noRecords">No records!</digi:trn></td>
			                          		</tr>
			                        	</logic:empty>
				            			<logic:notEmpty name="aimYearlyDiscrepancyForm" property="discrepancies">
										<logic:iterate name="aimYearlyDiscrepancyForm" property="discrepancies" id="discrepancy" type="org.digijava.module.aim.helper.YearlyDiscrepancy">
							            <tr valign="top"> 
							              <td height="30">
							              	<logic:equal name="discrepancy" property="fiscalYear" value="0">
				                          		NA
				                          	</logic:equal>
				                          	<logic:notEqual  name="discrepancy" property="fiscalYear" value="0">
														<bean:write name="discrepancy" property="fiscalYear" />
											</logic:notEqual>
							              
							              </td>
							              
							              <td>
							              	<div align="right">
							              		<bean:write name="discrepancy" property="donorPlanned"/>
							              	</div>
							              </td>
							              <td>
							              	<div align="center">
							              		<bean:write name="discrepancy" property="implAgencyPlanned"/>
							              	</div>
							              </td>
							              <td>
							              	<div align="right"> 
							                  <bean:write name="discrepancy" property="mofedPlanned"/>
							                </div></td>
							              <td>
							              	<div align="right">
							              		<bean:write name="discrepancy" property="donorActual"/>
							              	</div>
							              </td>
							              <td>
							              	<div align="center">
							              		<bean:write name="discrepancy" property="implAgencyActual"/>
							              	</div>
							              </td>
							              <td>
							              	<div align="right"> 
							                  <bean:write name="discrepancy" property="mofedActual"/>
							                </div>
							              </td>
							            </tr>
							            </logic:iterate>
							            </logic:notEmpty>
				       				</table>
				       			</td>
				      		</tr>
				      		
				    	</table>
				    </td>
				</tr>
				<tr bgcolor="#F4F4F2" > 
                	<td valign="top">
						<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F4F4F2">
                     		<tr bgcolor="#F4F4F2"> 
                        		<td width="43%" bgcolor="#F4F4F2">
								</td>
                        		<td width="57%" align="right" bgcolor="#F4F4F2">
									<digi:img src="module/aim/images/arrow-014E86.gif" width="15" height="10"/>
									<c:set target="${urlSubTabs}" property="transactionType">
		        						<bean:write name='aimYearlyDiscrepancyForm' property='transactionType'/>
		        					</c:set>
									<c:set var="translation">
										<digi:trn key="aim:clickToViewQuarterlyDiscrepancy">Click here to view Quarterly Discrepancy</digi:trn>
									</c:set>
		        					<digi:link href="/viewQuarterlyDiscrepancy.do" name="urlSubTabs" title="${translation}" >
		        						<strong>
											<digi:trn key="aim:showQuarterly">Show Quarterly </digi:trn>
										</strong>
		        					</digi:link>
								</td>
                      		</tr>
                      		<tr bgcolor="#F4F4F2"> 
                        		<td  bgcolor="#F4F4F2">&nbsp;</td>
                        		<td align="right" bgcolor="#F4F4F2">&nbsp;</td>
                      		</tr>
                    	</table>
					</td>
                </tr>
            </digi:form>
</logic:equal>



