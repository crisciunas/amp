<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>

<html:javascript formName="aimUserRegisterForm"/>
<digi:form action="/registerUser.do" method="post" onsubmit="return validateAimUserRegisterForm(this);">

<table width="100%" valign="top" align="left" cellpadding=0 cellSpacing=0 border=0>
<tr><td width="100%" valign="top" align="left">
<table bgColor=#ffffff border=0 cellPadding=0 cellSpacing=0 width=772>
	<tr>
		<td width=14>&nbsp;
		</td>
		<td align=left vAlign=top width=520><br>
			<table border=0 cellPadding=5 cellSpacing=0 width="100%">
				<tr>
					<td width="3%">&nbsp;</td>
					<td align=left class=title noWrap colspan="2">
						<b>
						<digi:trn key="aim:newUserConfirmRegSuccess"> 
                                                Registration is Successful! <br/>
                                                A confirmation email has been sent to the address you specified ${aimUserRegisterForm.email}.<br/>
                                                You need to read and respond to this email before you can use your account. If you don't do this, the new account will be deleted automatically after a few days.</digi:trn>
						</b>
					</td>
				</tr>
			</table>
		</td>
		<td bgColor=#f7f7f4 vAlign=top>

		</td>
	</tr>
</table>
</td></tr>
</table>
</digi:form>


