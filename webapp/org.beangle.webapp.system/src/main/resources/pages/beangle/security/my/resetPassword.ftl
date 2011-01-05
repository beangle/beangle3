[#ftl]
[@b.xhtmlhead/]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>

 <table width="100%">
  <tr>
   <td height="100%" valign="TOP" background="${base}/static/images/loginForm/ifr_mainBg_0.gif">
	<table width="100%" align="center" >
	 <tr>
	  <td background="${base}/static/images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">
	   <table width="100%" >
		<tr>
		 <td width="15%" height="42">&nbsp;</td>
		 <td width="85%"><FONT COLOR="red">[@b.text name="field.user.suggestive"/]</FONT></td>
		</tr>
	   </table>
	  </td>
	 </tr>
	</table>
	<table width="100%" align="CENTER" >
	 <tr>
	  <td height="100%" valign="TOP">
	   <table width="100%" align="CENTER" >
		<form name="commonForm" action="password.action?method=sendPassword" method="post">
		<tr><td colspan="3"><div class="message fade-ffff00"  id="error"></div></td></tr>
		<tr>
		 <td>&nbsp;</td>
		 <td id="f_name"  valign="top" width="30%">[@b.text name="user.name"/]:</td>
		 <td class="text1"><input type="text" name="name" value="${Parameters['loginName']!}" maxlength="64"/></td>
		</tr>
		<tr><td>&nbsp;</td>
		 <td id="f_mail"  valign="top" width="30%">[@b.text name="common.email"/]:</td>
		 <td class="text1"><input type="text" name="mail" maxlength="100"/></td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
		 <td colspan="3" align="right">
		  <input type="button" class="buttonStyle" name="button1" value="[@b.text name="action.submit"/]" onclick="submitForm()" class="form1">
		  <input type="reset" class="buttonStyle" name="reset" value="[@b.text name="action.reset"/]" class="form1">
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 </td>
		</tr>
		</form>
	   </table>
	  </td>
	 </tr>
	</table>
   </td>
  </tr>
 </table>
<script type="text/javascript">
   function submitForm(){
	 var a_fields = {
		 'name':{'l':'[@b.text name="user.name"/]', 'r':true, 't':'f_name'},
		 'mail':{'l':'[@b.text name="common.email"/]', 'r':true, 't':'f_mail','f':'email'}
	 };

	 var v = new validator(document.commonForm , a_fields, null);
	 if (v.exec()) {
		document.commonForm.submit();
	 }
   }
</script>


[#include "/template/foot.ftl"/]
