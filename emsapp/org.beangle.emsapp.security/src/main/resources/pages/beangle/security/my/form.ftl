[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/scripts/md5.js"></script>
[@b.form action="!save" title="我的账户" theme="list"]
	[@b.password label="user.oldPassword" name="oldPassword"  value="" required="true" maxlength="40" /]
	[@b.password label="user.newPassword" name="password" required="true"  /]
	[@b.password label="user.repeatPassword" name="repeatedPassword" required="true" /]
	[@b.email label="common.email" name="mail" value="${user.mail}" /]
	[@b.formfoot]
		<input type="hidden" name="user.id" value="${user.id}"/>
		[@b.reset /]&nbsp;&nbsp;[@b.submit value="action.submit"  onsubmit="validateMyAccount" /]
		<input type="hidden" name="oldPassword_encoded" value="${user.password}"/>
	[/@]
[/@]
<script type="text/javascript">
function validateMyAccount(form){
	if(form['oldPassword_encoded'].value!=hex_md5(form.oldPassword.value)) {alert ("旧密码输入不正确.");return false;}
	if(form['password'].value!=form['repeatedPassword'].value){alert("新密码与重复输入的不相同");return false;}
	else{
		form['password'].value=hex_md5(form['password'].value);
		form['password'].value=form['password'].value;
		return true;
	}
	return false;
}
</script>
[@b.foot/]