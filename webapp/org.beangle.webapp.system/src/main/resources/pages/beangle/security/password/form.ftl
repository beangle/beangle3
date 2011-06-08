[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/scripts/md5.js"></script>
[@b.form action="!save" theme="list" title="ui.changePasswordIndex"]
	[@b.password label="user.newPassword" name="password" required="true" maxlength="64" /]
	[@b.password label="user.repeatPassword" name="repeatedPassword" required="true"  maxlength="64"/]
	[@b.textfield label="common.email" name="mail" value="${user.mail}" check="match('email')" maxlength="100" /]
	[@b.formfoot]
		<input type="hidden" name="user.id" value="${user.id}"/>
		[@b.submit value="action.submit"  onsubmit="validatePassword" /]&nbsp;
		<input type="reset" value="${b.text("action.reset")}" name="reset1" />
	[/@]
[/@]
<script type="text/javascript">
function validatePassword(form){
	if(form['password'].value!=form['repeatedPassword'].value){alert("新密码与重复输入的不相同");return false;}
	else{
		form['password'].value=hex_md5(form['password'].value);
		return true;
	}
}
</script>
[@b.foot/]