[#ftl]
[#include "/template/head.ftl"/]
[#include "../status.ftl"/]
 [@b.grid width="100%" id="listTable" sortable="true"]
   [@b.actionbar id="userBar" title="用户列表" entity="user" action="user.action"]
		bar.addItem("[@text "action.new"/]",action.add(),'new.gif');
		bar.addItem("[@text "action.modify"/]",action.edit(),'update.gif');
		bar.addItem("[@text "action.freeze"/]",activateUser('false'),'${base}/static/icons/beangle/16x16/actions/freeze.png');
		bar.addItem("[@text "action.activate"/]",activateUser('true'),'${base}/static/icons/beangle/16x16/actions/activate.png');
		bar.addItem("[@text "action.delete"/]",action.remove(),'delete.gif');
		bar.addItem("[@text "action.export"/]",exportUserList(),'excel.png');
		function activateUser(isActivate){
			return action.multi("activate","确定提交?","&isActivate="+isActivate);
		}
		function exportUserList(){
			extParams="&keys="+"name,password,status,mail,creator.name,createdAt,updatedAt,groups,mngGroups";
			extParams+="&titles="+"登录名,密码,状态,电子邮件,创建者,创建时间,修改时间,用户组,管理用户组";
			return action.method("export",null,extParams);
		}
	[/@]
   [@b.gridhead]
     [@b.selectAllTd name="userId"/]
     [@b.sortTd width="10%" id="user.name" name="user.name"/]
     [@b.sortTd width="10%" id="user.fullname" name="user.fullname"/]
     [@b.sortTd width="30%" id="user.mail" name="common.email" /]
   	 [@b.sortTd width="10%" id="user.creator.fullname" name="common.creator" /]
  	 [@b.sortTd width="15%" id="user.defaultCategory" text="默认身份" /]
  	 [@b.sortTd width="15%" id="user.updatedAt" name="common.updatedAt" /]
   	 [@b.sortTd width="10%" id="user.status" name="common.status" /]
   [/@]
   [@b.gridbody datas=users;user]
     [@b.selectTd name="userId" value="${user.id}"/]
     <td><A href="user!dashboard.action?user.id=${user.id}" target="_blank">&nbsp;${user.name} </a></td>
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