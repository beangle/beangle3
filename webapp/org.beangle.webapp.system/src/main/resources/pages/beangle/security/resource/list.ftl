<#include "/template/head.ftl"/>
<#include "scope.ftl"/>
<#include "../status.ftl"/>
<body>
 <table id="resourceBar"></table>
<@table.table id="listTable" width="100%" sortable="true" headIndex="1">
    <tr class="thead">
    <form name="pageGoForm" method="post" action="resource!search.action">
    <td class="select"><img src="${base}/static/images/action/search.png" onclick="document.pageGoForm.submit()"/></td>
    <td><input type="text" name="resource.title" value="${Parameters['resource.title']!}" style="width:100%;"/></td>
    <td><input type="text" name="resource.name" value="${Parameters['resource.name']!}" style="width:100%;"/></td>
    <td>
	<select name="resource.scope" style="width:100px" onchange="this.form.submit()">
	<option value="">...</option>
	<#list 0..2 as i>
	  <option value="${i}"><@resourceScope i/></option>
	</#list>
	</select>
	</td>
    <td>
    <select  name="resource.enabled" style="width:100%;" onchange="this.form.submit()">
	   <option value="" <#if (Parameters['resource.enabled']!"")="">selected</#if>>..</option>
	   <option value="true" <#if (Parameters['resource.enabled']!"")="true">selected</#if>><@text "action.activate"/></option>
	   <option value="false" <#if (Parameters['resource.enabled']!"")="false">selected</#if> ><@text "action.freeze"/></option>
	  </select>
	</td>
	</form>
    </tr>
	<@table.thead>
      <@table.selectAllTd id="resourceId"/>   
      <@table.sortTd  width="20%" id="resource.title" text="标题" />
      <@table.sortTd  width="55%" id="resource.name" text="名称" />
      <@table.sortTd  width="10%" id="resource.scope" text="可见范围" />
      <@table.sortTd  width="10%" id="resource.enabled" text="状态" />
    </@>
    <@table.tbody datas=resources;resource>
     <@table.selectTd id="resourceId" value=resource.id/>
         <input type="hidden" name="${resource.id}" id="${resource.id}" />
     </td>
     <td><a href="resource.action?method=info&resource.id=${resource.id}">${(resource.title)!}</a></td>
     <td align="left" title="${resource.description!}">${(resource.name)!}</td>
     <td><@resourceScope resource.scope/></td>
     <td><@enableInfo resource.enabled/></td>
    </@>
  </@>
  </body>
 <@htm.actionForm name="resourceForm" entity="resource" action="resource.action"/>
  <script>
   function activate(enabled){
       addInput( document.resourceForm,"enabled",enabled);
       multiAction("activate","确定操作?");
   }
   function exportData(){
       addInput(form,"titles","标题,名称,描述,状态");
       addInput(form,"keys","title,name,description,enabled");
       exportList();
   }
   function preview(){
      window.open(action+"?method=preview");
   }
   var bar = new ToolBar('resourceBar','<a href="dashboard.action">权限管理</a>->系统资源',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@text "action.add"/>","add()");
   bar.addItem("<@text "action.edit"/>","edit()");
   bar.addItem("<@text "action.freeze"/>","activate(0)",'${base}/static/icons/beangle/16x16/actions/freeze.png');
   bar.addItem("<@text "action.activate"/>","activate(1)",'${base}/static/icons/beangle/16x16/actions/activate.png');
   bar.addItem("<@text "action.delete"/>","multiAction('remove')",'delete.gif');
   bar.addItem("<@text "action.export"/>","exportData()");
  </script>
  </body>
 <#include "/template/foot.ftl"/>
