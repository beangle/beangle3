[#ftl]
[@b.head/]
[#include "../status.ftl"/]
[@b.grid  items=groups var="group"  sortable="true" ]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add(),'new.gif');
		bar.addItem("${b.text("action.modify")}",action.edit(),'update.gif');
		bar.addItem("${b.text("action.delete")}",action.remove(),'delete.gif');
		bar.addItem("${b.text("action.export")}",action.exportData(null,"title,description,creator.title,createdAt,updatedAt,users","${b.text("common.title")},${b.text("common.description")},${b.text("common.creator")},${b.text("common.createdAt")},${b.text("common.updatedAt")},${b.text("group.users")}"));
	[/@]
	[@b.row]
		[@b.boxcol property="id"/]
		[@b.col property="name" width="20%" name="common.name"]<a href="${b.url('!info')}?groupId=${group.id}">${group.name}</a>[/@]
		[@b.col width="15%" property="owner.name" name="common.creator"/]
		[@b.col width="15%" property="updatedAt" name="common.updatedAt"]${group.updatedAt?string("yyyy-MM-dd")}[/@]
		[@b.col width="15%" property="category.name" name="适用身份" /]
		[@b.col width="10%" property="enabled" name="common.status"][@enableInfo group.enabled/][/@]
		[@b.col name="设置权限"][@b.a target="_blank" href="authority!edit?group.id=${group.id}"]<img style="border:0px" src="${base}/static/icons/beangle/16x16/actions/config.png"/>设置权限[/@][/@]
	[/@]
[/@]
[@b.foot/]