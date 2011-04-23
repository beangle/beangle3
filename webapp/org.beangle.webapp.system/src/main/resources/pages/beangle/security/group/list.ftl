[#ftl]
[@b.head/]
[#include "../status.ftl"/]
[@b.grid  items=userGroups var="userGroup"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.export")}",action.exportData(null,"title,description,creator.title,createdAt,updatedAt,users","${b.text("common.name")},${b.text("common.description")},${b.text("common.creator")},${b.text("common.createdAt")},${b.text("common.updatedAt")},${b.text("group.users")}"));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" width="20%" title="common.name"][@b.a href="!info?id=${userGroup.id}"]${userGroup.name}[/@][/@]
		[@b.col width="15%" property="owner.name" title="common.creator"/]
		[@b.col width="15%" property="updatedAt" title="common.updatedAt"]${userGroup.updatedAt?string("yyyy-MM-dd")}[/@]
		[@b.col width="15%" property="category.name" title="entity.userCategory" /]
		[@b.col width="10%" property="enabled" title="common.status"][@enableInfo userGroup.enabled/][/@]
		[@b.col title="设置权限"][@b.a target="_blank" href="authority!edit?group.id=${userGroup.id}"]<img style="border:0px" src="${b.theme.iconurl('actions/config.png')}"/>设置权限[/@][/@]
	[/@]
[/@]
[@b.foot/]