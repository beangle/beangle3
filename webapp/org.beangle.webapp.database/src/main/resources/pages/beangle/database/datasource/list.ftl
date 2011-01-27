[#ftl]
<div id="datasource_grid_div">
[@b.grid id="datasource_grid" width="100%" target="datasource_grid_div"]
	[@b.entitybar id="datasource_bar" title="数据源列表" entity="datasource" action="datasource" ]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
	[/@b.entitybar]
	[@b.row]
	[@b.selectAllTh name="datasource"/]
	[@b.col sort="datasource.name" text="名称"/]
	[@b.col sort="datasource.username" text="用户名"/]
	[@b.col sort="datasource.password" text="密码"/]
	[@b.col sort="datasource.url" text="地址"/]
	[@b.col sort="datasource.driverClassName" text="驱动类型"/]
	[/@b.row]
	[@b.gridbody datas=datasources var="datasource"]
	[@b.selectTd name="datasourceId" value=datasource.id/]
	<td>${datasource.name!}</td>
	<td>${datasource.username!}</td>
	<td>${datasource.password!}</td>
	<td>${datasource.url!}</td>
	<td>${datasource.driverClassName!}</td>
	[/@b.gridbody]
[/@b.grid]
</div>