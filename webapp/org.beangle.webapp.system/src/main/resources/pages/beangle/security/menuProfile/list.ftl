[#ftl]
[@b.head/]
[@b.grid width="100%" items=menuProfiles var="menuProfile" sortable="true"]
	[@b.gridbar]
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.delete")}",action.remove('删除时，会级联删除配置对应的所有菜单,确定删除?'));
	bar.addHelp();
	[/@]
	[@b.row]
		[@b.boxcol property="id"/]
		[@b.col name="名称" property="name" sortable="false"/]
		[@b.col name="使用的用户类别" property="category.name"/]
	[/@]
[/@]
[@b.foot/]
