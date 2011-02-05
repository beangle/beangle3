[#ftl]
<table id="fieldBar"></table>
<table >
	<tr>
		<td>
		限制对象:
		<select name="field.entity.id" onchange="" style="width:200px">
			<option value=''>...</option>
			[#list entities?if_exists as g]
			<option value='${g.id}'>${g.name}</option>
			[/#list]
		</select>
		<a href="#" onclick="addEntity()">添加</a>
		</td>
	</tr>
</table>

[@b.grid items=fields var="field"  target="限制参数"]
	[@b.gridbar ]
		bar.addItem("${b.text("action.new")}",action.method('editField'));
		bar.addItem("${b.text("action.edit")}",action.single('editField'));
		//bar.addItem("${b.text("action.delete")}","remove('fieldForm')");
	[/@]
	[@b.row]
		[@b.boxcol property="id"/]
		[@b.col property="name" title="名称" /]
		[@b.col property="remark" title="描述" /]
		[@b.col property="type" title="类型" /]
		[@b.col property="source" title="来源" /]
	[/@]
[/@]
