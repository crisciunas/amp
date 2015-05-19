<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<script type="text/javascript">
    
    function validate(){
        if(document.widgetOrgProfileWidgetForm.type.value=='-1'){
            alert("please select type");
            return false;
        }
        return true;
    }
    
    function cancel(){
       <digi:context name="addEdit" property="/widget/orgProfileManager.do" />
       document.widgetOrgProfileWidgetForm.action = "${addEdit}";
       document.widgetOrgProfileWidgetForm.submit();
    }
                                       
</script>
<digi:instance property="widgetOrgProfileWidgetForm" />
<digi:form action="/orgProfileManager.do~actType=save">
  
    <html:hidden name="widgetOrgProfileWidgetForm" property="id"/>
    <table width="60%" border="0" cellpadding="15">
        <tr>
            <td>
                <span class="crumb">
                    <c:set var="translation">
                        <digi:trn key="aim:clickToViewAdmin">Click here to goto Admin Home</digi:trn>
                    </c:set>
                    <html:link  href="/aim/admin.do" styleClass="comment" title="${translation}" >
                        <digi:trn key="aim:AmpAdminHome">Admin Home</digi:trn>
                    </html:link>&nbsp;&gt;&nbsp;
                    <html:link  href="/widget/orgProfileManager.do~actType=viewAll" styleClass="comment">
                        <digi:trn key="widget:Navigation:orgProfileManager">Org Profile Manager</digi:trn>
                    </html:link>
                    &nbsp;&gt;&nbsp;
                    
                    <digi:trn key="widget:Navigation:createOrgProfile">Create/Edit Org Profile</digi:trn>
                </span>
            </td>
        </tr>
        <tr>
            <td>
                <span class="subtitle-blue">
                    <c:if test="${empty widgetOrgProfileWidgetForm.id}">
                        <digi:trn key="widget:createOrgProfile">Create Org Profile</digi:trn>
                    </c:if>
                    <c:if test="${not empty widgetOrgProfileWidgetForm.id}">
                        <digi:trn key="widget:editOrgProfile">Edit Org Profile</digi:trn>
                    </c:if>
                </span>
            </td>
        </tr>
        <tr>
            <td>
                
                <table>
                    <tr>
                     <td colspan="2">
                            <digi:errors/>
                    </td>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" align="left">
                            <font color="red">*</font>
                            <strong>
                                <digi:trn key="widget:createOrgProfile:type">Type</digi:trn>:&nbsp;
                            </strong>
                        </td>
                        <td  colspan="2">
                            <html:select name="widgetOrgProfileWidgetForm" property="type">
                                <html:option value="1"><digi:trn key="Summary">Summary</digi:trn></html:option>
                                <html:option value="2"><digi:trn key="Type of Aid">Type of Aid</digi:trn></html:option>
                                <html:option value="3"><digi:trn key="Pledges/Comm/Disb">Pledges/Comm/Disb</digi:trn></html:option>
                                <html:option value="4"><digi:trn key="ODA Profile">ODA Profile</digi:trn></html:option>
                                <html:option value="5"><digi:trn key="Sector Breakdown">Sector Breakdown</digi:trn></html:option>
                                <html:option value="6"><digi:trn key="Regional Breakdown">Regional Breakdown</digi:trn></html:option>
                                <html:option value="7"><digi:trn key="Paris Declaration">Paris Declaration</digi:trn></html:option>
                            </html:select>
                        </td>
                        
                    </tr>
                      <tr>
                        <td nowrap="nowrap" align="left">
                            <font color="red">*</font>
                            <strong>
                                <digi:trn key="widget:createOrgProfile:type">Place</digi:trn>:&nbsp;
                            </strong>
                        </td>
                        <td  colspan="2">
                            <html:select name="widgetOrgProfileWidgetForm" property="selPlaces" multiple="true" style="width: 300px">
				<html:optionsCollection name="widgetOrgProfileWidgetForm" property="places" value="id" label="name"/>
                            </html:select>
                        </td>
                        
                    </tr>
                    
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <html:submit onclick="return validate()">
                                <digi:trn key="widget:createOrgProfile:btnSave">Save</digi:trn>
                            </html:submit>
                        </td>
                        <td>
                            <input type="button" value='<digi:trn key="Cancel">Cancel</digi:trn>' onclick="cancel()">
                        </td>
                    </tr>
                </table>
                
            </td>
        </tr>
    </table>
</digi:form>