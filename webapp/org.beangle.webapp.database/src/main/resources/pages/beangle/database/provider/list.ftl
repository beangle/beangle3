[#ftl]
[@b.grid  items=providers var="provider" target="provider_div"]
	[@b.gridbar  title="数据源提供者列表" entity="provider" action="provider" ]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
	[/@b.gridbar]
	[@b.row]
		[@b.boxcol property="id"/]
		[@b.col property="name" name="提供者名称"]
			[@sj.a targets="datasource_div" href="${b.url('datasource!search')}?datasource.provider.id=${provider.id}"]${provider.name!}[/@]
		[/@]
		[@b.col property="dialect" name="方言"/]
	[/@]
[/@b.grid]