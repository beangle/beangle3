[#ftl]
<div id="stats">
[@b.module title="用户统计"]
	[@b.grid  items=userStat?keys?sort var="stat" width="94%" ]
		[@b.row]
			[@b.col width="70%" title="用户组"]${stat}[/@]
			[@b.col width="15%" title="激活"]${userStat[stat].get(true)!}[/@]
			[@b.col width="15%" title="禁用"]${userStat[stat].get(false)!}[/@]
		[/@]
	[/@]
[/@]

[@b.module title="系统资源"]
	[@b.grid width="94%" items=resourceStat var="stat" ]
		[@b.row]
			[@b.col title="状态"]${stat[0]?string("激活","禁用")}[/@]
			[@b.col title="资源数"]${stat[1]}[/@]
		[/@]
	[/@]
[/@]

[@b.module title="菜单设置"]
	[@b.grid width="94%" items=menuProfiles var="profile" sortable="false"]
		[@b.row]
			[@b.col title="类别" property="name"/]
			[@b.col title="状态/菜单数"][#list menuStats.get(profile.id) as stat]${stat[0]?string("激活","禁用")}/${stat[1]}&nbsp;[/#list][/@]
		[/@]
	[/@]
[/@]

[@b.module title="数据权限"]
	限制模式数量:${restrictionStat[0]}<br/>
	属性配置数量:${propertyMetaStat[0]!0}<br/>
[/@]
</div>