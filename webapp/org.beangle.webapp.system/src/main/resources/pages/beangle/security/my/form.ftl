<#include "/template/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/validator.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/md5.js"></script>
<body>
<br>
   <TABLE WIDTH="80%" align="center" class="formTable">
     <TR  class="thead">
      <TD colspan="2"><FONT COLOR="red"><B>我的账户</B></FONT></TD>
     </TR>
    <form name="commonForm" action="my!save.action" method="post" onsubmit="return false;">
    <tr> 
     <td id="f_oldPassword" class="title" width="40%"><@text name="user.oldPassword"/>:</td>
     <td class="text1"><input type="password" name="oldPassword" maxLength="64"/><input type="hidden" name="oldPassword_encoded" value="${user.password}"></td>
    </tr>
    <tr> 
     <td id="f_newPassword" class="title"><@text name="user.newPassword"/>:</td>
     <td class="text1"><input type="password" name="password"  maxLength="64"/></td>
    </tr>
    <tr> 
     <td id="f_repeatedPassword" class="title"><@text name="user.repeatPassword"/>:</td>
     <td class="text1"><input type="password" name="repeatedPassword" maxLength="64"/></td>
    </tr>
    <tr>
     <td id="f_mail"  class="title" width="40%"><@text name="common.email"/>:</td>
     <td class="text1"><input type="text" name="mail"  value="${user.mail!('')}" maxLength="100"/></td>
    </tr>
    <tr>
     <td colspan="2" align="center" class="tfoot"> 
      <input type="hidden" name="method" value="change" />
       <input type="button" value="<@text name="action.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
       <input type="reset" value="<@text name="action.reset" />" name="reset1"  class="buttonStyle" />
     </td>
    </tr>
    </form>
   </TABLE>
<script>
   function doAction(form){
     var a_fields = {
         'oldPassword':{'l':'<@text name="user.oldPassword"/>', 'r':true, 't':'f_oldPassword'},
         'password':{'l':'<@text name="user.newPassword"/>', 'r':true, 't':'f_newPassword'},
         'repeatedPassword':{'l':'<@text name="user.repeatPassword"/>', 'r':true, 't':'f_repeatedPassword'},
         'mail':{'l':'<@text name="common.email"/>', 'r':true, 'f':'email', 't':'f_mail'}
     };

     var v = new validator(document.commonForm , a_fields, null);
     if (v.exec()) {
        if(form['oldPassword_encoded'].value!=hex_md5(form.oldPassword.value)) {alert ("旧密码输入不正确.");return;}
        if(form['password'].value!=form['repeatedPassword'].value){alert("新密码与重复输入的不相同");return;}
        else{
           form['password'].value=hex_md5(form['password'].value);
           form['password'].value=form['password'].value;
           document.commonForm.submit();
        }
     }
   }
</script> 
</body>
<#include "/template/foot.ftl"/>
