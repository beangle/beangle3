[#ftl]
<table id="fieldBar"></table>
<table >
	<tr>
		<td>
		限制对象:
		<select name="field.object.id" onchange="" style="width:200px">
			<option value=''>...</option>
			[#list objects?if_exists as g]
			<option value='${g.id}'>${g.name}</option>
			[/#list]
		</select>
		<a href="#" onclick="addObject()">添加</a>
		</td>
	</tr>
</table>

[@b.grid id="listTable2" sortable="true" target="ui-tabs-2"]
	[@b.actionbar id="fieldBar" title="数据权限 对象和参数" entity="field" action="restrict-meta.action" target="ui-tabs-2"]
		bar.addItem("[@b.text "action.new"/]",action.method('editField'));
		bar.addItem("[@b.text "action.edit"/]",action.single('editField'));
		//bar.addItem("[@msg.text "action.delete"/]","remove('fieldForm')");
	[/@]
	[@b.gridhead]
      [@b.selectAllTd name="fieldId"/]  
      [@b.sortTd  width="10%" id="field.name" text="名称" /]
      [@b.sortTd  width="10%" id="field.remark" text="描述" /]
      [@b.sortTd  width="10%" id="field.type" text="类型" /]
      [@b.sortTd  width="10%" id="field.source" text="来源" /]
    [/@]
    [@b.gridbody datas=fields;param]
     [@b.selectTd name="fieldId" value=param.id/]
         <input type="hidden" name="${param.id}" id="${param.id}" />
     </td>
     <td>${(param.name)!}</td>
     <td>${param.remark!}</td>
     <td>${(param.type)!}</td>
     <td>${(param.source)!}</td>
    [/@]
[/@]
