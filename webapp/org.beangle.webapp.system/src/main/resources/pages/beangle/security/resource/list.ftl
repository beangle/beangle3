[#ftl]
[@b.head/]
[#include "scope.ftl"/]
[#include "../status.ftl"/]
[@b.form name="resourceForm" action="!search"]
[@b.grid items=resources var="resource" sortable="true" ]
	[@b.gridbar title='系统资源']
	function activate(enabled){return action.multi('activate','确定操作?','&amp;enabled='+enabled);}
	bar.addItem("${b.text("action.add")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.freeze")}",activate(0),'${base}/static/icons/beangle/16x16/actions/freeze.png');
	bar.addItem("${b.text("action.activate")}",activate(1),'${base}/static/icons/beangle/16x16/actions/activate.png');
	bar.addItem("${b.text("action.delete")}",action.remove(),'delete.gif');
	bar.addItem("${b.text("action.export")}",action.exportData(null,"title,name,description,enabled","标题,名称,描述,状态"));
	[/@]
	<tr>
		<td><img src="${base}/static/images/action/search.png" onclick="document.getElementById('resourceForm').submit()"/></td>
		<td><input type="text" name="resource.title" value="${Parameters['resource.title']!}" style="width:95%;"/></td>
		<td><input type="text" name="resource.name" value="${Parameters['resource.name']!}" style="width:95%;"/></td>
		<td>
		<select name="resource.scope" style="width:95%" onchange="this.form.submit()">
		<option value="">...</option>
		[#list 0..2 as i]
		<option value="${i}" [#if (Parameters['resource.scope']!"")="${i}"]selected="selected"[/#if]>[@resourceScope i/]</option>
		[/#list]
		</select>
		</td>
		<td>
		<select  name="resource.enabled" style="width:95%;" onchange="this.form.submit()">
			<option value="" [#if (Parameters['resource.enabled']!"")=""]selected="selected"[/#if]>..</option>
			<option value="true" [#if (Parameters['resource.enabled']!"")="true"]selected="selected"[/#if]>${b.text("action.activate")}</option>
			<option value="false" [#if (Parameters['resource.enabled']!"")="false"]selected="selected"[/#if]>${b.text("action.freeze")}</option>
		</select>
		</td>
	</tr>
	[@b.row]
		[@b.boxcol/]
		[@b.col  width="20%" property="title" title="标题" ][@b.a href="resource!info?resource.id=${resource.id}"]${(resource.title)!}[/@][/@]
		[@b.col  width="55%" property="name" align="left" title="名称"]<span title="${resource.description!}">${resource.name!}</span>[/@]
		[@b.col  width="10%" property="scope" title="可见范围"][@resourceScope resource.scope/][/@]
		[@b.col  width="10%" property="enabled" title="状态"][@enableInfo resource.enabled/][/@]
	[/@]
[/@]
[/@]
[@b.foot/]