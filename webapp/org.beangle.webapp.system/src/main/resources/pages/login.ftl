<#include "/template/simpleHead.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/OnReturn.js"></script>
<script>
   if(this.parent!=this){
      this.top.location="loginForm.action";
   }
</script>
  <#if ((Session['loginFailureCount'])?default(0)>1)>
  <#assign needCaptcha=true>
  <#else>
  <#assign needCaptcha=false>
  </#if>

<body LEFTMARGIN="0" TOPMARGIN="0" scroll=no>
<TABLE WIDTH="100%" HEIGHT="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
 <TR>
  <TD ALIGN="CENTER" VALIGN="MIDDLE" BACKGROUND="${base}/static/images/loginForm/idx_4.gif">
   <TABLE WIDTH="651" BORDER="0" CELLPADDING="0" CELLSPACING="0" onkeypress="ret.focus(event)">
    <tr>
     <td align="center">权限系统</td>
    </tr>
    <TR>
     <TD height="157" ALIGN="CENTER" VALIGN="MIDDLE"  style="background-attachment: fixed;background-repeat: no-repeat;background-position: center;">
      <TABLE width="50%" BORDER="0" CELLSPACING="0" CELLPADDING="0"  width="651"   >
       <tr>
        <td colspan="3"><@s.actionerror/></td>
       </tr>
       <@s.form name="loginForm" action="login">
       <tr>
         <td colspan="2">
         <TABLE WIDTH="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0" style="font-size:16px">       
         <tr>
          <td ALIGN="right" height="30"><STRONG>用户名<#--<@text name="user.name"/>-->:</STRONG></td>
          <td width="45">
           <INPUT NAME="username" TYPE="text" value="${name!}" style="width:150px;background-color:#B0B0B0">
          </td>
         </tr>
         <tr>
          <td ALIGN="right" height="30"><STRONG>密码:</STRONG></td>
          <td><INPUT NAME="password" TYPE="password"   style="width:150px;background-color:#B0B0B0">
          <INPUT NAME="encodedPassword" type="hidden" value=""></td>
         </tr>
         <#if needCaptcha>
         <tr>
          <td ALIGN="right" height="30"><STRONG>验证码:</STRONG></td>
          <td align="bottom"><INPUT NAME="captcha" TYPE="text" style="width:85px;background-color:#B0B0B0">
              <img src="captcha/image.action" title="验证码,点击更换一张" onclick="changeCaptcha(this)" 
              width="60" height="25" style="vertical-align:top;"></td>
         </tr>
         </#if>
         <tr>
          <td ALIGN="right"><INPUT NAME="request_locale" TYPE="radio" value="zh_CN" checked >中文</td>
          <td><INPUT NAME="request_locale"  id="engVersion" TYPE="radio" value="en_US">ENGLISH</td>
         </tr> 
         </table>
        </td>
        <td colspan="2">
        <TABLE WIDTH="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
        	<tr>
          		<td >&nbsp;&nbsp;&nbsp;
          		  <button name="submitButton"  onclick ="submitLogin()" style="height:35pt;width:38pt;" BORDER="0"><B>登录</B></button>
          		</td>
         </tr>
         </table>
        </td>
       </tr>
       </@>
      </TABLE>
     </TD>
    </TR>
 	<form name="formsubmit" method="post" action="" onsubmit="return false;">
    <tr>
     <td ALIGN="right" height="30">
      <span class="menu_blue_14px2">
       <a href="#" onClick="window.open('password.action?method=resetPassword', 'new', 'toolbar=no,top=250,left=250,location=no,directories=no,statue=no,menubar=no,resizable=no,scrollbars=no,width=400,height=200')">
        取回密码
       </a>
      </span>      
     </td>
    </tr>
    </form>
   </TABLE>   
  </TD>
 </TR>
</TABLE>
<script>
  var ret = new OnReturn(document.loginForm);
  ret.add("username");
  ret.add("password");
  <#if needCaptcha>
  ret.add("captcha");
  </#if>
  ret.add("submitButton");
  var form  = document.loginForm;
  
  function submitLogin(){
     if(form['username'].value==""){
        alert("用户名称不能为空");return;
     }
     if(form['password'].value==""){
        alert("密码不能为空");return;
     }
	<#if needCaptcha>
	 if(form['captcha'].value==""){
        alert("验证码不能为空");return;
     }
	</#if>
     form.submit();
  }
  
  if("${language}".indexOf("en")!=-1){
     document.getElementById('engVersion').checked=true;
  }
  
  var username=getCookie("username");
  if(null!=username){
    form['username'].value=username;
  }
  function changeCaptcha(obj) {  
     //获取当前的时间作为参数，无具体意义  
     var timenow = new Date().getTime();  
     //每次请求需要一个不同的参数，否则可能会返回同样的验证码  
     //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。  
     obj.src="captcha/image.action?d="+timenow;  
  }
</script>
</body>
<#include "/template/foot.ftl"/>
