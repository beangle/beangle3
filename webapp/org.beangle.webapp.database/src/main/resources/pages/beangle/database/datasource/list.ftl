[#ftl]
[@b.grid width="100%" items=datasources var="datasource" target="datasource_grid_div"]
	[@b.gridbar]
		bar.addItem("添加",action.add());
		bar.addItem("修改",action.edit());
		bar.addItem("删除",action.remove());
	[/@b.gridbar]
	[@b.row]
	[@b.boxcol property="id"/]
	[@b.col property="name" name="名称"/]
	[@b.col property="username" name="用户名"/]
	[@b.col property="password" name="密码"/]
	[@b.col property="url" name="地址"/]
	[@b.col property="driverClassName" name="驱动类型"/]
	[/@b.row]
[/@b.grid]
