[#ftl]
[#include "/template/head.ftl"/]
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/validator.js"></script>
<body LEFTMARGIN="0" TOPMARGIN="0" scroll=no>
 <TABLE WIDTH="100%" BORDER="0" ALIGN="LEFT" CELLPADDING="0" CELLSPACING="0">
  <TR> 
   <TD HEIGHT="100%" VALIGN="TOP" BACKGROUND="${base}/static/images/loginForm/ifr_mainBg_0.gif">
	<TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR>
      <TD BACKGROUND="${base}/static/images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">              
       <TABLE WIDTH="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR> 
         <TD WIDTH="15%" HEIGHT="42">&nbsp;</TD>
         <TD WIDTH="85%"><FONT COLOR="red">[@text name="field.user.suggestive"/]</FONT></TD>
        </TR>
       </TABLE>
      </TD>
     </TR>
    </TABLE>
    <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR> 
      <TD HEIGHT="100%" VALIGN="TOP">       
       <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
        <form name="commonForm" action="password.action?method=sendPassword" method="post">
        <tr><td colspan="3"><div class="message fade-ffff00"  id="error"></div></td></tr>
        <tr> 
         <td>&nbsp;</td>
         <td id="f_name"  valign="top" width="30%">[@text name="user.name"/]:</td>
         <td class="text1"><input type="text" name="name" value="${Parameters['loginName']!}" maxLength="64"/></td>
        </tr>
        <tr><td>&nbsp;</td>
         <td id="f_mail"  valign="top" width="30%">[@text name="common.email"/]:</td>
         <td class="text1"><input type="text" name="mail" maxLength="100"/></td>
        </tr>
        <tr><td colspan="3">&nbsp;</td></tr>
        <tr>
         <td colspan="3" align="right"> 
          <input type="button" class="buttonStyle" name="button1" value="[@text name="action.submit"/]" onClick="submitForm()" class="form1">
          <input type="reset" class="buttonStyle" name="reset" value="[@text name="action.reset"/]" class="form1">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
<script>
   function submitForm(){
     var a_fields = {
         'name':{'l':'[@text name="user.name"/]', 'r':true, 't':'f_name'},
         'mail':{'l':'[@text name="common.email"/]', 'r':true, 't':'f_mail','f':'email'}
     };

     var v = new validator(document.commonForm , a_fields, null);
     if (v.exec()) {
        document.commonForm.submit();
     }
   }
</script>
</BODY>
</body>
[#include "/template/foot.ftl"/]
