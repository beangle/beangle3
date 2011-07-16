[#ftl]
[@b.toolbar title="entity.restrictEntity"/]
<script>
function validateEntity(form){
	if(""==form['entity.name'].value){
		alert("请填写限制实体名称");
		return false;
	}
	return true
}
</script>
[@b.grid items=entities var="entity" sortable="false"]
	[@b.row]
		[@b.col property="" title="序号"]${entity_index+1}[/@]
		[@b.col property="name" title="common.name"]
			[@b.form action="!saveEntity"]
			<input type="hidden" value="${entity.id}" name="entity.id"/>
			<input name="entity.name" value="${entity.name}"/>
			[@b.submit value="保存"/]
			[/@]
		[/@]
		[@b.col property="id" title="操作"]
			[@b.a href="!fields?entity.id=${entity.id}"]查看参数[/@]
			[@b.a href="!removeEntity?entity.id=${entity.id}" onclick="if(confirm('确定删除?')) bg.Go(this);return false;"]删除[/@]
		[/@]
	[/@]
	<tr>
		<td>添加新的实体</td>
		<td>
		[@b.form action="!saveEntity"]
		<input name="entity.name" value=""/>
		[@b.submit value="添加" onsubmit="validateEntity"/]
		[/@]
		</td>
		<td></td>
	</tr>
[/@]
<br/>
[@b.toolbar title="限制参数"/]
<div id="restrict_fields">
[@b.grid items=fields var="field"]
	[@b.gridbar ]
		bar.addItem("${b.text("action.new")}",action.method('editField'),'${b.theme.iconurl("actions/new.png")}');
		bar.addItem("${b.text("action.edit")}",action.single('editField'));
		bar.addItem("${b.text("action.delete")}",action.single('removeField'));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" title="名称" /]
		[@b.col property="remark" title="描述" /]
		[@b.col property="type" title="类型" /]
		[@b.col property="source" title="来源" /]
	[/@]
[/@]
</div>