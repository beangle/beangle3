[#ftl]
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
		[@b.col property="name" title="名称" width="10%"/]
		[@b.col property="remark" title="描述"  width="10%"/]
		[@b.col property="type" title="类型" width="40%"/]
		[@b.col property="source" title="来源"  width="40%"/]
	[/@]
[/@]
</div>