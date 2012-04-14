[#ftl]
[@b.head/]
[@b.grid items=entities var="entity"]
	[@b.gridbar ]
		bar.addItem("${b.text("action.new")}",action.method('editEntity'),'${b.theme.iconurl("actions/new.png")}');
		bar.addItem("${b.text("action.edit")}",action.single('editEntity'));
		bar.addItem("${b.text("action.delete")}",action.single('removeEntity'));
	[/@]
	[@b.row]
		[@b.boxcol width="5%"/]
		[@b.col property="name" title="名称" width="10%"/]
		[@b.col property="type" title="类型" width="20%"/]
		[@b.col property="remark" title="描述"  width="65%"/]
	[/@]
[/@]
[@b.foot/]