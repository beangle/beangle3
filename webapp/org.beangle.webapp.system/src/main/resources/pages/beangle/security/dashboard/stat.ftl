[#ftl]
<div id="stats">
<div id="userStat" class="module expanded">
	<h2 class="header">
	<a href="#" class="toggle" onclick="_wi_tm('userStat');">用户统计</a>
	</h2>
	<div class="modulebody">
	[@b.grid  items=groupStat var="stat" width="94%" id="userStatTable"]
		[@b.row]
			[@b.col width="35%" name="类别"]${categories.get(stat[0]).name}[/@]
			[@b.col width="10%" name="状态"]${stat[1]?string("激活","禁用")}[/@]
			[@b.col width="15%" name="数量"]${stat[2]}[/@]
		[/@]
	[/@]
	</div>
</div>

<div id="groupStat" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('groupStat');">用户组统计</a>
	</h2>
	<div class="modulebody">
	[@b.grid width="94%" items=groupStat var="stat" id="groupStatTable"]
		[@b.row]
			[@b.col width="35%" name="类别"]${categories.get(stat[0]).name}[/@]
			[@b.col width="10%" name="状态"]${stat[1]?string("激活","禁用")}[/@]
			[@b.col width="15%" name="数量"]${stat[2]}[/@]
		[/@]
	[/@]
	</div>
</div>

<div id="resource" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('resource');">系统资源</a>
	</h2>
	<div class="modulebody">
	[@b.grid width="94%" items=resourceStat var="stat" id="resourceStat"]
		[@b.row]
			[@b.col width="10%" name="状态"]${stat[0]?string("激活","禁用")}[/@]
			[@b.col width="15%" name="资源数"]${stat[1]}[/@]
		[/@]
	[/@]
	</div>
</div>

<div id="menu" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('menu');">菜单设置</a>
	</h2>
	<div class="modulebody">
		[@b.grid width="94%" id="menuStatTable" items=menuProfiles var="profile"]
			[@b.row]
				[@b.col width="30%" name="类别" property="name"/]
				[@b.col width="10%" name="状态/菜单数"][#list menuStats.get(profile.id) as stat]${stat[0]?string("激活","禁用")}/${stat[1]}&nbsp;[/#list][/@]
			[/@]
		[/@]
	</div>
</div>

<div id="restriction" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('restriction');">数据权限</a>
	</h2>
	<div class="modulebody">
	限制模式数量:${patternStat[0]}<br/>
	模式参数数量:${paramStat[0]}<br/>
	</div>
</div>
<script type="text/javascript">
function _wi_tm(moudleId){
	var id= document.getElementById(moudleId);
	if(id.className=="module collapsed"){
		id.className="module expanded";
	}else{
		id.className="module collapsed";
	}
}
</script>
</div>