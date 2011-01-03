[#ftl]
<div>
[#assign online=0][#assign max=0]
[#list onlineProfiles as profile]
[#assign online=online+profile.online/][#assign max=max+profile.capacity/]
[/#list]
[#assign bartitle]在线${online}/上限${max} ([#list onlineProfiles?sort_by(["category"]) as profile]${profile.category.name}${profile.online}/${profile.capacity}  [/#list]) ${now?string("yyyy-MM-dd HH:mm:ss")}[/#assign]
[@b.toolbar id="activityBar" title=bartitle]
	bar.addItem("刷新","refresh()",'refresh.gif');
	bar.addItem("结束会话","invalidateSession()",'delete.gif');
[/@]
[@s.form name="invalidateForm" theme="simple" id="invalidateForm" action="monitor!invalidate"]
[@b.grid width="100%" id="activityListTable"  target="ui-tabs-1"]
	[@b.gridhead]
		[@b.selectAllTh name="sessionId"/]
		[@b.gridth width="15%" text="用户名" sort="authentication.username"/]
		[@b.gridth width="12%" text="登录时间" sort="loginAt"/]
		[@b.gridth width="12%" text="最近访问时间" sort="lastAccessAt"/]
		[@b.gridth width="8%" text="在线时间" sort="onlineTime"/]
		[@b.gridth width="15%" text="地址" sort="details.agent.ip"/]
		[@b.gridth width="10%" text="操作系统" sort="details.agent.os"/]
		[@b.gridth width="10%" text="浏览器" sort="details.agent.name"/]
		[@b.gridth width="8%" text="身份" sort="category.name"/]
		[@b.gridth width="8%" text="状态" sort="expired"/]
	[/@]
	[@b.gridbody datas=onlineActivities;activity]
		[@b.selectTd name="sessionId" value=activity.sessionid/]
		<td><a href="user!dashboard.action?user.id=${activity.authentication.principal.id}" target="_blank">${activity.authentication.principal.fullname!('')}(${activity.authentication.principal.username!})</a></td>
		<td>${activity.loginAt?string("yy-MM-dd HH:mm")}</td>
		<td>${activity.lastAccessAt?string("yy-MM-dd HH:mm")}</td>
		<td>${(activity.onlineTime)/1000/60}min</td>
		<td>${activity.authentication.details.agent.ip!('')}</td>
		<td>${activity.authentication.details.agent.os!('')}</td>
		<td>${activity.authentication.details.agent.browser!('')}</td>
		<td>${activity.authentication.principal.category.name}</td>
		<td>${activity.expired?string("过期","在线")}</td>
	[/@]
[/@]
[@sj.submit type="button" id="invalidateBtn" cssStyle="display:none" targets="ui-tabs-1"/]
[/@]
	[#assign refreshInterval=Parameters['interval']!"4"/]
	定时每${refreshInterval}秒刷新
<script type="text/javascript">
	function refresh(){
		bg.page.goPage("activityListTable");
	}
	function invalidateSession(){
		$('#invalidateBtn').click();
	}
	if(typeof refreshTime != undefined){
		clearTimeout(refreshTime);
	}
	refreshTime=setTimeout(refresh,${refreshInterval}*1000);
</script>
</div>