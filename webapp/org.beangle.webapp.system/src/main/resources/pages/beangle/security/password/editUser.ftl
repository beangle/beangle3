[#ftl]
[@b.head/]
 <script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
<script  type="text/javascript" src="${base}/static/scripts/common/md5.js"></script>

<center>
 <table width="50%" align="center" >
  <tr>
   <td height="100%" valign="TOP" background="${base}/static/images/loginForm/ifr_mainBg_0.gif">
	<table width="100%" align="CENTER" >
	 <tr>
	  <td background="${base}/static/images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">
	   <table width="100%" align="CENTER" >
		<tr>
		 <td width="15%" height="42">&nbsp;</td>
		 <td width="85%"><FONT COLOR="red"><em>${b.text("ui.changePasswordIndex")}</em></FONT></td>
		</tr>
	   </table>
	  </td>
	 </tr>
	</table>
	<table width="100%" align="CENTER" >
	 <tr>
	  <td height="100%" valign="TOP">
	   <table width="100%" align="CENTER" >
		<form name="commonForm" action="${b.url('!updateUser')}" method="post" onsubmit="return false;">
		 <input type="hidden" name="user.id" value="${user.id}"/>
		<tr>
		 <td>&nbsp;</td>
		 <td id="f_newPassword"  valign="top" width="40%">${b.text("user.newPassword")}:</td>
		 <td class="text1"><input type="password" name="password" maxlength="64"/></td>
		</tr>
		<tr>
		 <td>&nbsp;</td>
		 <td id="f_repeatedPassword"  valign="top" width="40%">${b.text("user.repeatPassword")}:</td>
		 <td class="text1"><input type="password" name="repeatedPassword" maxlength="64"/></td>
		</tr>
		<tr>
		 <td>&nbsp;</td>
		 <td id="f_mail"  valign="top" width="40%">${b.text("common.email")}:</td>
		 <td class="text1"><input type="text" name="mail"  value="${user.mail}" maxlength="100"/></td>
		</tr>
		<tr><td colspan="3" height="5"></td></tr>
		<tr>
		 <td colspan="3" align="center">
		  <input type="hidden" name="method" value="change"/>
		   <input type="button" value="${b.text("action.submit")}" name="button1" onclick="doAction(this.form)" class="buttonStyle" />&nbsp;
		   <input type="reset" value="${b.text("action.reset")}" name="reset1"  class="buttonStyle" />
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
</center>
<script type="text/javascript">
   function doAction(form){
	 var a_fields = {
		 'password':{'l':'${b.text("user.newPassword")}', 'r':true, 't':'f_newPassword'},
		 'repeatedPassword':{'l':'${b.text("user.repeatPassword")}', 'r':true, 't':'f_repeatedPassword'},
		 'mail':{'l':'${b.text("common.email")}', 'r':true, 'f':'email', 't':'f_mail'}
	 };

	 var v = new validator(document.commonForm , a_fields, null);
	 if (v.exec()) {
		if(form['password'].value!=form['repeatedPassword'].value){alert("新密码与重复输入的不相同");return;}
		else{
		   form['password'].value=hex_md5(form['password'].value);
		   document.commonForm.submit();
		   //window.close();
		}
	 }
   }
</script>

[@b.foot/]
