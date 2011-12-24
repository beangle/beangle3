[#ftl]
[@b.grid  items=providers var="provider"]
	[@b.gridbar  title="数据源提供者列表" entity="provider" action="provider" ]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
	[/@b.gridbar]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" title="提供者名称"]
			[@b.a href="datasource!search?datasource.provider.id=${provider.id}"]${provider.name!}[/@]
		[/@]
		[@b.col property="dialect" title="方言"/]
	[/@]
[/@b.grid]