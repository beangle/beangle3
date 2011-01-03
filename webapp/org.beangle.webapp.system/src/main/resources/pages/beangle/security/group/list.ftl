[#ftl]
[@b.xhtmlhead/]
[#include "../status.ftl"/]
<body >
[@b.grid width="100%" id="groupListTable" ]
[@b.entitybar id="groupbar" title="用户组列表" entity="group" action="group.action"]
	bar.addItem("[@b.text "action.new"/]",action.add(),'new.gif');
	bar.addItem("[@b.text "action.modify"/]",action.edit(),'update.gif');
	bar.addItem("[@b.text "action.delete"/]",action.remove(),'delete.gif');
	bar.addItem("[@b.text "action.export"/]",action.exportData(null,"name,description,creator.name,createdAt,updatedAt,users","[@b.text "common.name"/],[@b.text "common.description"/],[@b.text "common.creator"/],[@b.text "common.createdAt"/],[@b.text "common.updatedAt"/],[@b.text "group.users"/]"),'excel.png');
[/@]
	[@b.gridhead]
		[@b.selectAllTh name="groupId"/]
		[@b.gridth sort="userGroup.name" width="20%" name="common.name"/]
		[@b.gridth width="15%" sort="userGroup.owner.name" name="common.creator"/]
		[@b.gridth width="15%" sort="userGroup.updatedAt" name="common.updatedAt"/]
		[@b.gridth width="15%" sort="userGroup.remark" text="适用身份"/]
		[@b.gridth width="10%" sort="userGroup.enabled" name="common.status"/]
		[@b.gridth text="设置权限"/]
	[/@]
	[@b.gridbody datas=groups;group]
		[@b.selectTd type="checkbox" name="groupId" value="${group.id}"/]
		<td><a href="group!info.action?groupId=${group.id}">${group.name} </a></td>
		<td>${group.owner.name}</td>
		<td>${group.updatedAt?string("yyyy-MM-dd")}</td>
		<td>[#if group.category??]${group.category.name}[/#if]</td>
		<td>[@enableInfo group.enabled/]</td>
		<td><a target="_blank" href="authority!edit.action?group.id=${group.id}"><img style="border:0px" src="${base}/static/icons/beangle/16x16/actions/config.png"/>设置权限</a></td>
	[/@]
[/@]
</body>
[#include "/template/foot.ftl"/]
