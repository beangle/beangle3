[#ftl]
[@b.head/]
[#include "../status.ftl"/]
[@b.grid items=users var="user"]
	[@b.gridbar]
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.modify")}",action.edit());
	bar.addItem("${b.text("action.freeze")}",activateUser('false'),'${b.theme.iconurl('actions/freeze.png')}');
	bar.addItem("${b.text("action.activate")}",activateUser('true'),'${b.theme.iconurl('actions/activate.png')}');
	bar.addItem("${b.text("action.delete")}",action.remove());
	bar.addItem("${b.text("action.export")}",action.exportData("name,fullname,mail,roles,creator.fullname,effectAt,invalidAt,passwordExpiredAt,createdAt,updatedAt,enabled",null,"&fileName=用户信息"));
	function activateUser(isActivate){return action.multi("activate","确定提交?","isActivate="+isActivate);}
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" width="20%"]&nbsp;[@b.a href="!dashboard?user.id=${user.id}" target="_blank"]${user.name}[/@][/@]
		[@b.col property="fullname" width="13%"/]
		[@b.col property="mail" width="23%"/]
		[@b.col property="effectAt" width="20%"]${user.effectAt?string("yyyy-MM-dd")}~${(user.invalidOn?string("yyyy-MM-dd"))!}[/@]
		[@b.col property="passwordExpiredAt" width="10%"]${(user.passwordExpiredAt?string("yyyy-MM-dd"))!}[/@]
		[@b.col property="enabled" width="9%"][@enableInfo user.enabled/][/@]
	[/@]
[/@]
[@b.foot/]