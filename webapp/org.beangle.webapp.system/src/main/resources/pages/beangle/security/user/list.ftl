[#ftl]
[@b.head/]
[#include "../status.ftl"/]
[@b.grid items=users var="user"  target="userlist"]
	[@b.gridbar]
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.modify")}",action.edit());
	bar.addItem("${b.text("action.freeze")}",activateUser('false'),'${base}/static/icons/beangle/16x16/actions/freeze.png');
	bar.addItem("${b.text("action.activate")}",activateUser('true'),'${base}/static/icons/beangle/16x16/actions/activate.png');
	bar.addItem("${b.text("action.delete")}",action.remove());
	bar.addItem("${b.text("action.export")}",exportUserList());
	function activateUser(isActivate){
		return action.multi("activate","确定提交?","isActivate="+isActivate);
	}
	function exportUserList(){
		extParams="&amp;keys="+"name,password,status,mail,creator.name,createdAt,updatedAt,groups,mngGroups";
		extParams+="&amp;titles="+"登录名,密码,状态,电子邮件,创建者,创建时间,修改时间,用户组,管理用户组";
		return action.method("export",null,extParams);
	}
	[/@]
	[@b.row]
		[@b.boxcol property="id"/]
		[@b.col property="name"][@b.a href="!dashboard?user.id=${user.id}" target="_blank"]${user.name}[/@][/@]
		[@b.col property="fullname"/]
		[@b.col property="mail" title="common.email" /]
		[@b.col property="creator.fullname"/]
		[@b.col property="defaultCategory.name" title="entity.userCategory"][#list user.categories as uc][#if uc!=user.defaultCategory]<em>${uc.name}</em>[#else]${uc.name}[/#if][#if uc_has_next]&nbsp;[/#if][/#list][/@]
		[@b.col property="updatedAt" title="common.updatedAt"]${user.updatedAt?string("yyyy-MM-dd")}[/@]
		[@b.col property="status" title="common.status"][@enableInfo user.enabled/][/@]
	[/@]
[/@]
[@b.foot/]