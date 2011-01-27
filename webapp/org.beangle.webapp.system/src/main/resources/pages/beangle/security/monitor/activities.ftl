[#ftl]
[#assign online=0][#assign max=0]
[#list onlineProfiles as profile]
[#assign online=online+profile.online/][#assign max=max+profile.capacity/]
[/#list]
[#assign bartitle]在线${online}/上限${max} ([#list onlineProfiles?sort_by(["category"]) as profile]${profile.category.name}${profile.online}/${profile.capacity}  [/#list]) ${now?string("yyyy-MM-dd HH:mm:ss")}[/#assign]
[@b.toolbar id="activityBar" title=bartitle][/@]
[@s.form name="invalidateForm" theme="simple" id="invalidateForm" action="monitor!invalidate"]
[@b.grid width="100%" datas=onlineActivities var="activity" id="activityListTable"  target="当前会话" sortable="true"]
	[@b.gridbar]
	bar.addItem("刷新","refresh()",'refresh.gif');
	bar.addItem("结束会话","invalidateSession()",'delete.gif');
	[/@]
	[@b.row]
		[@b.boxcol boxname="sessionId" property="sessionid"/]
		[@b.col width="15%" name="用户名" property="authentication.username"]
		[@b.a href="user!dashboard?user.id=${activity.authentication.principal.id}" target="_blank"]${activity.authentication.principal.fullname!('')}(${activity.authentication.principal.username!})[/@]
		[/@]
		[@b.col width="12%" name="登录时间" property="loginAt"]${activity.loginAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="12%" name="最近访问时间" property="lastAccessAt"]${activity.lastAccessAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="8%" name="在线时间" property="onlineTime"]${(activity.onlineTime)/1000/60}min[/@]
		[@b.col width="15%" name="地址" property="authentication.details.agent.ip"/]
		[@b.col width="10%" name="操作系统" property="authentication.details.agent.os"/]
		[@b.col width="10%" name="浏览器" property="authentication.details.agent.browser"/]
		[@b.col width="8%" name="身份" property="authentication.principal.category.name"/]
		[@b.col width="8%" name="状态" property="expired"]${activity.expired?string("过期","在线")}[/@]
	[/@]
[/@]
[@sj.submit type="button" id="invalidateBtn" cssStyle="display:none" targets="当前会话"/]
[/@]
	[#assign refreshInterval=Parameters['interval']!"4"/]
<div>定时每${refreshInterval}秒刷新</div>
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
