[#ftl]
[@b.xhtmlhead/]
[#include "../status.ftl"/]
<body class="autoadapt">
[@b.grid width="100%" id="listTable"]
	[@b.entitybar id="userBar" title="用户列表" entity="user" action="user.action"]
		bar.addItem("[@b.text "action.new"/]",action.add(),'new.gif');
		bar.addItem("[@b.text "action.modify"/]",action.edit(),'update.gif');
		bar.addItem("[@b.text "action.freeze"/]",activateUser('false'),'${base}/static/icons/beangle/16x16/actions/freeze.png');
		bar.addItem("[@b.text "action.activate"/]",activateUser('true'),'${base}/static/icons/beangle/16x16/actions/activate.png');
		bar.addItem("[@b.text "action.delete"/]",action.remove(),'delete.gif');
		bar.addItem("[@b.text "action.export"/]",exportUserList(),'excel.png');
		function activateUser(isActivate){
			return action.multi("activate","确定提交?","&amp;isActivate="+isActivate);
		}
		function exportUserList(){
			extParams="&amp;keys="+"name,password,status,mail,creator.name,createdAt,updatedAt,groups,mngGroups";
			extParams+="&amp;titles="+"登录名,密码,状态,电子邮件,创建者,创建时间,修改时间,用户组,管理用户组";
			return action.method("export",null,extParams);
		}
	[/@]
	[@b.gridhead]
		[@b.selectAllTh name="userId"/]
		[@b.gridth sort="user.name" name="user.name"/]
		[@b.gridth sort="user.fullname" name="user.fullname"/]
		[@b.gridth sort="user.mail" name="common.email" /]
		[@b.gridth sort="user.creator.fullname" name="common.creator" /]
		[@b.gridth sort="user.defaultCategory" text="默认身份" /]
		[@b.gridth sort="user.updatedAt" name="common.updatedAt" /]
		[@b.gridth sort="user.status" name="common.status" /]
	[/@]
	[@b.gridbody datas=users;user]
		[@b.selectTd name="userId" value="${user.id}"/]
		<td><a href="user!dashboard.action?user.id=${user.id}" target="_blank">&nbsp;${user.name} </a></td>
		<td>${user.fullname!("")}</td>
		<td>${user.mail}</td>
		<td>${(user.creator.fullname)!}</td>
		<td>${(user.defaultCategory.name)!}</td>
		<td>${user.updatedAt?string("yyyy-MM-dd")}</td>
		<td>[@enableInfo user.enabled/]</td>
	[/@]
[/@]
</body>
</html>