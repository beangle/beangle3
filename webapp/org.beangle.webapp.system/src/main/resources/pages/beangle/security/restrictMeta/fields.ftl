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

[@b.grid id="listTable2" items=fields var="field" target="ui-tabs-2"]
	[@b.gridbar id="fieldBar"]
		bar.addItem("${b.text("action.new")}",action.method('editField'));
		bar.addItem("${b.text("action.edit")}",action.single('editField'));
		//bar.addItem("${b.text("action.delete")}","remove('fieldForm')");
	[/@]
	[@b.row]
		[@b.boxcol property="id"/]
		[@b.col width="10%" property="name" name="名称" /]
		[@b.col width="10%" property="remark" name="描述" /]
		[@b.col width="10%" property="type" name="类型" /]
		[@b.col width="10%" property="source" name="来源" /]
	[/@]
[/@]
