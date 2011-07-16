[#ftl]
[@b.head/]
[#include "../status.ftl"/]
[@b.grid  items=userGroups var="userGroup"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.export")}",action.exportData("name:${b.text("common.name")},remark:${b.text("common.remark")},owner.name:${b.text("common.creator")},createdAt:${b.text("common.createdAt")},updatedAt:${b.text("common.updatedAt")},users:${b.text("group.users")}",null,"&fileName=用户组"));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" width="20%" title="common.name"][@b.a href="!info?id=${userGroup.id}"]${userGroup.name}[/@][/@]
		[@b.col width="15%" property="owner.name" title="common.creator"/]
		[@b.col width="15%" property="category.title" title="entity.userCategory" /]
		[@b.col width="10%" property="enabled" title="common.status"][@enableInfo userGroup.enabled/][/@]
		[@b.col width="15%" property="updatedAt" title="common.updatedAt"]${userGroup.updatedAt?string("yyyy-MM-dd")}[/@]
		[@b.col title="设置权限"][@b.a target="_blank" href="authority!edit?group.id=${userGroup.id}"]<img style="border:0px" src="${b.theme.iconurl('actions/config.png')}"/>设置权限[/@][/@]
	[/@]
[/@]
[@b.foot/]