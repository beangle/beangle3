[#ftl]
[@b.grid items=datasources var="datasource" target="datasource_grid_div"]
	[@b.gridbar]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
	[/@b.gridbar]
	[@b.row]
	[@b.boxcol/]
	[@b.col property="name" title="名称"/]
	[@b.col property="username" title="用户名"/]
	[@b.col property="password" title="密码"/]
	[@b.col property="url" title="地址"/]
	[@b.col property="driverClassName" title="驱动类型"/]
	[/@b.row]
[/@b.grid]
