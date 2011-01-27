[#ftl]
[@b.grid id="provider_grid" width="100%" target="provider_div"]
	[@b.entitybar id="provider_bar" title="数据源提供者列表" entity="provider" action="provider" ]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
	[/@b.entitybar]
	[@b.row]
	[@b.selectAllTh name="provider"/]
	[@b.col sort="provider.name" text="提供者名称"/]
	[@b.col sort="provider.dialect" text="方言"/]
	[/@b.row]
	[@b.gridbody datas=providers;provider]
	[@b.selectTd name="providerId" value=provider.id/]
	<td>[@sj.a targets="datasource_div" href="${b.url('datasource!search')}?datasource.provider.id=${provider.id}"]${provider.name!}[/@]</td>
	<td>${provider.dialect!}</td>
	[/@b.gridbody]
[/@b.grid]