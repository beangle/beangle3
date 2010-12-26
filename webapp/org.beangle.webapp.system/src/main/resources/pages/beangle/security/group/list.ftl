[#ftl]
[#include "/template/head.ftl"/]
[#include "../status.ftl"/]
<BODY LEFTMARGIN="0" TOPMARGIN="0">
[@b.grid width="100%" id="groupListTable" sortable="true"]
[@b.actionbar id="groupbar" title="用户组列表" entity="group" action="group.action"]
	bar.addItem("[@text "action.new"/]",action.add(),'new.gif');
	bar.addItem("[@text "action.modify"/]",action.edit(),'update.gif');
	bar.addItem("[@text "action.delete"/]",action.remove(),'delete.gif');
	bar.addItem("[@text "action.export"/]",action.exportData(null,"name,description,creator.name,createdAt,updatedAt,users","[@text "common.name"/],[@text "common.description"/],[@text "common.creator"/],[@text "common.createdAt"/],[@text "common.updatedAt"/],[@text "group.users"/]"),'excel.png');
[/@]
	[@b.gridhead]
     [@b.selectAllTd name="groupId"/]
     [@b.sortTd id="userGroup.name" width="20%" name="common.name"/]
     [@b.sortTd width="15%" id="userGroup.owner.name" name="common.creator"/]
     [@b.sortTd width="15%" id="userGroup.updatedAt" name="common.updatedAt"/]
     [@b.sortTd width="15%" id="userGroup.remark" text="适用身份"/]
     [@b.sortTd width="10%" id="userGroup.enabled" name="common.status"/]
     [@b.td text="设置权限"/]
	[/@]
	[@b.gridbody datas=groups;group]
		[@table.selectTd type="checkbox" id="groupId" value="${group.id}"/]
		<td><A href="group!info.action?groupId=${group.id}">${group.name} </a></td>
		<td>${group.owner.name}</td>
		<td>${group.updatedAt?string("yyyy-MM-dd")}</td>
		<td>[#if group.category??]${group.category.name}[/#if]</td>
		<td>[@enableInfo group.enabled/]</td>
		<td><a target="_blank" href="authority!edit.action?group.id=${group.id}"><img style="border:0px" src="${base}/static/icons/beangle/16x16/actions/config.png"/>设置权限</a></td>
	[/@]
[/@]
 </body>
[#include "/template/foot.ftl"/]
