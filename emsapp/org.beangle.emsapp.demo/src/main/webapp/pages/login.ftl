[#ftl]
[@b.head title="Login"/]
<script type="text/javascript">
	if(this.parent!=this){ this.top.location="${b.url('login')}"; }
</script>
[#if ((Session['loginFailureCount'])?default(0)>1)][#assign needCaptcha=true][#else][#assign needCaptcha=false][/#if]
<div style="text-align:center;margin-top:150px;border:4px">权限系统</div>
<div >
	[@b.form name="loginForm" action="login" target="_top"]
	<table style="margin:auto;">
		<tr><td colspan="3">[@b.messages/]</td></tr>
		<tr>
			<td><label for="username"><strong>用户名:</strong></label></td>
			<td><input name="username" id="username" tabindex="1" title="请输入用户名" type="text" value="${(Parameters['username']!)?html}" style="width:150px;background-color:#B0B0B0"/></td>
			<td rowspan="3" valign="top">
			[@b.submit name="submitBtn" tabindex="6" style="height:35pt;width:38pt;" value="登录" onsubmit="checkLogin"][/@]
			</td>
		</tr>
		<tr>
			<td><label for="password"><strong>密码:</strong></label></td>
			<td><input id="password" name="password"  tabindex="2" type="password" style="width:150px;background-color:#B0B0B0"/>
			<input name="encodedPassword" type="hidden" value=""/></td>
		</tr>
		[#if needCaptcha]
		<tr>
			<td><label for="captcha"><strong>验证码:</strong></label></td>
			<td align="right"><input id="captcha" name="captcha"  tabindex="3" type="text" style="width:85px;background-color:#B0B0B0"/>
			<img src="${b.url('/security/captcha')}" title="验证码,点击更换一张" onclick="changeCaptcha(this)" alt="验证码" width="60" height="25" style="vertical-align:top;"/></td>
		</tr>
		[/#if]
		<tr>
			<td colspan="3" align="center">
				<fieldset>
				<legend>语言</legend>
				<input name="session_locale" id="local_zh" type="radio" tabindex="4" value="zh_CN" [#if locale.language?contains('zh')]checked="checked"[/#if]/><label for="local_zh">中文</label>
				<input name="session_locale" id="local_en" type="radio" tabindex="5" value="en_US" [#if locale.language?contains('en')]checked="checked"[/#if]/><label for="local_en">ENGLISH</label>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="right"><a href="#" onclick="window.open('${b.url('password!resetPassword')}', 'new', 'toolbar=no,top=250,left=250,location=no,directories=no,statue=no,menubar=no,resizable=no,scrollbars=no,width=400,height=200')">取回密码</a></td>
		</tr>
	</table>
	[/@]
</div>
<script type="text/javascript">
	var form  = document.loginForm;
	function checkLogin(form){
		if(!form['username'].value){
			alert("用户名称不能为空");return false;
		}
		if(!form['password'].value){
			alert("密码不能为空");return false;
		}
		[#if needCaptcha]
		if(!form['captcha'].value){
			alert("验证码不能为空");return false;
		}
		[/#if]
		return true;
	}
	var username=beangle.cookie.get("username");
	if(null!=username){ form['username'].value=username;}
	function changeCaptcha(obj) {
		//获取当前的时间作为参数，无具体意义
		var timenow = new Date().getTime();
		//每次请求需要一个不同的参数，否则可能会返回同样的验证码
		//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
		obj.src="${b.url('/security/captcha')}?d="+timenow;
	}
</script>

[@b.foot/]
