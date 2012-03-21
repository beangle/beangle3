[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
[#include "scope.ftl"/]
[#include "../status.ftl"/]
[@b.form name="resourceForm" id="resourceForm" action="!search"]
[@b.grid items=resources var="resource" sortable="true" filterable="true"]
	[@b.gridbar title='系统资源']
	function activate(enabled){return action.multi('activate','确定操作?','&enabled='+enabled);}
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.freeze")}",activate(0),'${b.theme.iconurl('actions/freeze.png')}');
	bar.addItem("${b.text("action.activate")}",activate(1),'${b.theme.iconurl('actions/activate.png')}');
	bar.addItem("${b.text("action.delete")}",action.remove());
	bar.addItem("${b.text("action.export")}",action.exportData("title:common.title,name:common.name,scope:可见范围,enabled:common.status,remark:common.remark",null,"fileName=资源信息"));
	[/@]
	[@b.gridfilter property="scope"]
		<select name="resource.scope" style="width:95%" onchange="bg.form.submit(this.form)">
		<option value="">...</option>
		[#list scopes?keys as i]
		<option value="${i}" [#if (Parameters['resource.scope']!"")="${i}"]selected="selected"[/#if]>[@resourceScope i/]</option>
		[/#list]
		</select>
	[/@]
	[@b.gridfilter property="enabled"]
		<select  name="resource.enabled" style="width:95%;" onchange="bg.form.submit(this.form)">
			<option value="" [#if (Parameters['resource.enabled']!"")=""]selected="selected"[/#if]>..</option>
			<option value="true" [#if (Parameters['resource.enabled']!"")="true"]selected="selected"[/#if]>${b.text("action.activate")}</option>
			<option value="false" [#if (Parameters['resource.enabled']!"")="false"]selected="selected"[/#if]>${b.text("action.freeze")}</option>
		</select>
	[/@]
	[@b.gridfilter property="entry"]
		<select  name="resource.entry" style="width:95%;" onchange="bg.form.submit(this.form)">
			<option value="" [#if (Parameters['resource.entry']!"")=""]selected="selected"[/#if]>..</option>
			<option value="true" [#if (Parameters['resource.entry']!"")="true"]selected="selected"[/#if]>是</option>
			<option value="false" [#if (Parameters['resource.entry']!"")="false"]selected="selected"[/#if]>否</option>
		</select>
	[/@]
	[@b.row]
		[@b.boxcol width="5%"/]
		[@b.col  width="30%" property="name" align="left" style="padding-left:10px"title="common.name"/]
		[@b.col  width="20%" property="title" title="common.title" ][@b.a href="resource!info?resource.id=${resource.id}"]${(resource.title)!}[/@][/@]
		[@b.col  width="10%" property="entry" title="菜单入口"][#if resource.entry][@b.a target="_blank" href=resource.name]打开[/@][#else]否[/#if][/@]
		[@b.col  width="10%" property="scope" title="可见范围"][@resourceScope resource.scope/][/@]
		[@b.col  width="10%" property="enabled" title="common.status"][@enableInfo resource.enabled/][/@]
		[@b.col  width="15%" property="remark" title="common.remark"/]
	[/@]
[/@]
[/@]
[@b.foot/]