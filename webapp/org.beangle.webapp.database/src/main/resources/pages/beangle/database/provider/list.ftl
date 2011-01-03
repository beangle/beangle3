[#ftl]
[@b.grid id="provider_grid" width="100%" target="provider_div"]
	[@b.entitybar id="provider_bar" title="数据源提供者列表" entity="provider" action="provider.action" ]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
		menu=bar.addMenu("菜单以");
		menu.addItem("修改",action.edit());
		menu.addItem("修改1",action.edit());
	[/@b.entitybar]
	[@b.gridhead]
	[@b.selectAllTh name="provider"/]
	[@b.gridth sort="provider.name" text="提供者名称"/]
	[@b.gridth sort="provider.dialect" text="方言"/]
	[/@b.gridhead]
	[@b.gridbody datas=providers;provider]
	[@b.selectTd name="providerId" value=provider.id/]
	<td>[@sj.a targets="datasource_div" href="${base}/database/datasource!search.action"]${provider.name!}[/@]</td>
	<td>${provider.dialect!}</td>
	[/@b.gridbody]
[/@b.grid]