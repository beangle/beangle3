[#ftl]
[@b.head/]
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
		 <td width="85%"><font color="red">${b.text("field.user.suggestive")}</font></td>
		</tr>
	   </table>
	  </td>
	 </tr>
	</table>
	<table width="100%" align="CENTER" >
	 <tr>
	  <td height="100%" valign="TOP">
		[@b.form name="commonForm" action="!sendPassword"]
		<table width="100%" align="CENTER" >
		<tr><td colspan="3"><div class="message fade-ffff00"  id="error"></div></td></tr>
		<tr>
		 <td>&nbsp;</td>
		 <td id="f_name"  valign="top" width="30%">${b.text("user.name")}:</td>
		 <td class="text1"><input type="text" name="name" value="${Parameters['name']!}" maxlength="64"/></td>
		</tr>
		<tr><td>&nbsp;</td>
		 <td id="f_mail"  valign="top" width="30%">${b.text("common.email")}:</td>
		 <td class="text1"><input type="text" name="mail" maxlength="100"/></td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
		 <td colspan="3" align="right">
			[@b.submit value="action.submit" onsubmit="validate"/]
		  <input type="reset" class="buttonStyle" name="reset" value="${b.text("action.reset")}" class="form1">
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 </td>
		</tr>
		</table>
		[/@】
	  </td>
	 </tr>
	</table>
   </td>
  </tr>
 </table>
<script type="text/javascript">
function validate(form){
	var a_fields = {
	 'name':{'l':'${b.text("user.name")}', 'r':true, 't':'f_name'},
	 'mail':{'l':'${b.text("common.email")}', 'r':true, 't':'f_mail','f':'email'}
	};
	var v = new validator(form , a_fields, null);
	return v.exec();
}
</script>
[@b.foot/]