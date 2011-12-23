[#ftl/]
限制参数:
[#list entity.fields as field]${field.name}(${field.remark!})[#if field_has_next],[/#if][/#list]
[@b.a href="!editEntity?entity.id=${entity.id}" target="entities"]修改[/@]
&nbsp;[@b.a href="!removeEntity?id=${entity.id}" target="entities"]删除[/@]
<br/>
[@b.toolbar title="限制模式"/]
[@b.grid  items=patternMap.get(entity) var="pattern" sortable="false"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.method('editPattern'),'${b.theme.iconurl("actions/new.png")}');
		bar.addItem("${b.text("action.edit")}",action.single('editPattern'));
		bar.addItem("${b.text("action.delete")}",action.multi('removePattern'));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="remark" title="描述" /]
		[@b.col width="60%" property="content" title="restrictPattern.content" /]
		[@b.col width="20%" property="entity.name" title="entity.restrictEntity"/]
	[/@]
[/@]

<script>
	document.getElementById('restrict_entity_${entity.id}').parentNode.style.height="auto";
</script>
	