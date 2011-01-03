[#ftl]
[@b.xhtmlhead/]
[#include "scope.ftl"/]
[#include "../status.ftl"/]
<body>
	[@b.entitybar id="resourcebar" title='<a href="dashboard.action">权限管理</a>->系统资源' entity="resource" action="resource.action"]
	function activate(enabled){
		return action.multi('activate','确定操作?','&amp;enabled='+enabled);
	}
	bar.addItem("[@b.text "action.add"/]",action.add());
	bar.addItem("[@b.text "action.edit"/]",action.edit());
	bar.addItem("[@b.text "action.freeze"/]",activate(0),'${base}/static/icons/beangle/16x16/actions/freeze.png');
	bar.addItem("[@b.text "action.activate"/]",activate(1),'${base}/static/icons/beangle/16x16/actions/activate.png');
	bar.addItem("[@b.text "action.delete"/]",action.remove(),'delete.gif');
	bar.addItem("[@b.text "action.export"/]",action.exportData(null,"title,name,description,enabled","标题,名称,描述,状态"));
	[/@]

<form name="pageGoForm" id="pageGoForm" method="post" action="resource!search.action">
[@b.grid id="listTable" width="100%" ]
	[@b.gridhead]
	<tr>
		<td class="select"><img src="${base}/static/images/action/search.png" onclick="document.getElementById('pageGoForm').submit()"/></td>
		<td><input type="text" name="resource.title" value="${Parameters['resource.title']!}" style="width:95%;"/></td>
		<td><input type="text" name="resource.name" value="${Parameters['resource.name']!}" style="width:95%;"/></td>
		<td>
		<select name="resource.scope" style="width:95%;" onchange="this.form.submit()">
		<option value="">...</option>
		[#list 0..2 as i]
		<option value="${i}" [#if (Parameters['resource.scope']!"")="${i}"]selected="selected"[/#if]>[@resourceScope i/]</option>
		[/#list]
		</select>
		</td>
		<td>
		<select  name="resource.enabled" style="width:95%;" onchange="this.form.submit()">
		   <option value="" [#if (Parameters['resource.enabled']!"")=""]selected="selected"[/#if]>..</option>
		   <option value="true" [#if (Parameters['resource.enabled']!"")="true"]selected="selected"[/#if]>[@b.text "action.activate"/]</option>
		   <option value="false" [#if (Parameters['resource.enabled']!"")="false"]selected="selected"[/#if]>[@b.text "action.freeze"/]</option>
		  </select>
		</td>
	</tr>
	<tr>
		[@b.selectAllTh name="resourceId"/]
		[@b.gridth  width="20%" sort="resource.title" text="标题" /]
		[@b.gridth  width="55%" sort="resource.name" text="名称" /]
		[@b.gridth  width="10%" sort="resource.scope" text="可见范围" /]
		[@b.gridth  width="10%" sort="resource.enabled" text="状态" /]
	</tr>
	[/@]
	[@b.gridbody datas=resources;resource]
	 [@b.selectTd name="resourceId" value=resource.id/]
	 <td><a href="resource!info.action?resource.id=${resource.id}">${(resource.title)!}</a></td>
	 <td align="left" title="${resource.description!}">${(resource.name)!}</td>
	 <td>[@resourceScope resource.scope/]</td>
	 <td>[@enableInfo resource.enabled/]</td>
	[/@]
  [/@]
</form>
</body>
[#include "/template/foot.ftl"/]
