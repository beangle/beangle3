[#ftl]
[@b.grid id="datasource_grid" width="100%" target="datasource_div"]
	[@b.entitybar id="datasource_bar" title="数据源列表" entity="datasource" action="datasource.action" ]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
	[/@b.entitybar]
	[@b.gridhead]
	[@b.selectAllTh name="datasource"/]
	[@b.gridth sort="datasource.name" text="名称"/]
	[@b.gridth sort="datasource.username" text="用户名"/]
	[@b.gridth sort="datasource.password" text="密码"/]
	[@b.gridth sort="datasource.url" text="地址"/]
	[@b.gridth sort="datasource.driverClassName" text="驱动类型"/]
	[/@b.gridhead]
	[@b.gridbody datas=datasources;datasource]
	[@b.selectTd name="datasourceId" value=datasource.id/]
	<td>${datasource.name!}</td>
	<td>${datasource.username!}</td>
	<td>${datasource.password!}</td>
	<td>${datasource.url!}</td>
	<td>${datasource.dirverClassName!}</td>
	[/@b.gridbody]
[/@b.grid]