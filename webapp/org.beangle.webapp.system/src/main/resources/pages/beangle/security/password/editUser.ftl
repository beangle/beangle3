<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/validator.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/md5.js"></script>
<body LEFTMARGIN="0" TOPMARGIN="0" scroll=no>
<center>
 <TABLE WIDTH="50%" BORDER="0" ALIGN="center" CELLPADDING="0" CELLSPACING="0">
  <TR> 
   <TD HEIGHT="100%" VALIGN="TOP" BACKGROUND="${base}/static/images/loginForm/ifr_mainBg_0.gif">
	<TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR>
      <TD BACKGROUND="${base}/static/images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">              
       <TABLE WIDTH="100%" ALIGN="CENTER" BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR> 
         <TD WIDTH="15%" HEIGHT="42">&nbsp;</TD>
         <TD WIDTH="85%"><FONT COLOR="red"><B><@text name="ui.changePasswordIndex"/></B></FONT></TD>
        </TR>
       </TABLE>
      </TD>
     </TR>
    </TABLE>
    <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR> 
      <TD HEIGHT="100%" VALIGN="TOP">       
       <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
        <form name="commonForm" action="password.action?method=updateUser" method="post" onsubmit="return false;">
         <input type="hidden" name="user.id" value="${user.id}"/> 
        <tr>
         <td>&nbsp;</td>
         <td id="f_newPassword"  valign="top" width="40%"><@text name="user.newPassword"/>:</td>
         <td class="text1"><input type="password" name="password" maxLength="64"/></td>
        </tr>
        <tr> 
         <td>&nbsp;</td>
         <td id="f_repeatedPassword"  valign="top" width="40%"><@text name="user.repeatPassword"/>:</td>
         <td class="text1"><input type="password" name="repeatedPassword" maxLength="64"/></td>
        </tr>
        <tr> 
         <td>&nbsp;</td>
         <td id="f_mail"  valign="top" width="40%"><@text name="common.email"/>:</td>
         <td class="text1"><input type="text" name="mail"  value="${user.mail}" maxLength="100"/></td>
        </tr>
        <tr><td colspan="3" height="5"></td></tr>
        <tr>
         <td colspan="3" align="center"> 
          <input type="hidden" name="method" value="change"/>
	       <input type="button" value="<@text name="action.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset" value="<@text name="action.reset" />" name="reset1"  class="buttonStyle" />
         </td>
        </tr>
        </form>
       </TABLE>
      </TD>
     </TR>          
    </TABLE>
   </TD>
  </TR>
 </TABLE>
</center>
<script>
   function doAction(form){
     var a_fields = {         
         'password':{'l':'<@text name="user.newPassword"/>', 'r':true, 't':'f_newPassword'},
         'repeatedPassword':{'l':'<@text name="user.repeatPassword"/>', 'r':true, 't':'f_repeatedPassword'},
         'mail':{'l':'<@text name="common.email"/>', 'r':true, 'f':'email', 't':'f_mail'}
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
</body>
<#include "/template/foot.ftl"/>
