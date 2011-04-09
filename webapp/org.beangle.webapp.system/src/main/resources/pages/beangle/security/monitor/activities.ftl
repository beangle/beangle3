[#ftl]
[#assign online=0][#assign max=0]
[#list onlineProfiles as profile]
[#assign online=online+profile.online/][#assign max=max+profile.capacity/]
[/#list]

[#assign bartitle]在线${online}/上限${max} ([#list onlineProfiles?sort_by(["category"]) as profile]${profile.category.name}${profile.online}/${profile.capacity}  [/#list]) ${now?string("yyyy-MM-dd HH:mm:ss")}[/#assign]
[@b.toolbar title=bartitle][/@]
[@b.form name="invalidateForm" id="invalidateForm" action="!invalidate"]
[@b.grid  id="activityListTable" items=onlineActivities var="activity"]
	[@b.gridbar]
	bar.addItem("刷新","refresh()",'refresh.png');
	bar.addItem("过期","expireSession()",'edit-delete.png');
	bar.addItem("结束","killSession()",'edit-delete.png');
	[/@]
	[@b.row]
		[@b.boxcol boxname="sessionId" property="sessionid"/]
		[@b.col width="15%" title="用户名" property="authentication.username"]
		[@b.a href="user!dashboard?user.id=${activity.authentication.principal.id}" target="_blank"]${activity.authentication.principal.fullname!('')}(${activity.authentication.principal.username!})[/@]
		[/@]
		[@b.col width="12%" title="登录时间" property="loginAt"]${activity.loginAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="12%" title="最近访问时间" property="lastAccessAt"]${activity.lastAccessAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="8%" title="在线时间" property="onlineTime"]${(activity.onlineTime)/1000/60}min[/@]
		[@b.col width="15%" title="地址" property="authentication.details.agent.ip"/]
		[@b.col width="10%" title="操作系统" property="authentication.details.agent.os"/]
		[@b.col width="10%" title="浏览器" property="authentication.details.agent.browser"/]
		[@b.col width="8%" title="身份" property="authentication.principal.category.name"/]
		[@b.col width="8%" title="状态" property="expired"]${activity.expired?string("过期","在线")}[/@]
	[/@]
[/@]
[@b.submit id="expireBtn" style="display:none" value=""/]
[@b.submit id="killBtn" style="display:none" action="!invalidate?kill=1" value=""/]
[/@]
[#assign refreshInterval=Parameters['interval']!"4"/]
<div>定时每${refreshInterval}秒刷新</div>
<script type="text/javascript">
	function refresh(){
		if(document.getElementById("activityListTable")){
			page_activityListTable.goPage();
		}else{
			if(typeof refreshTime != undefined){
				clearTimeout(refreshTime);
			}
		}
	}
	
	function killSession(){
		document.getElementById('killBtn').click();
	}
	function expireSession(){
		document.getElementById('expireBtn').click();
	}
	
	if(refreshTime != null){
		clearTimeout(refreshTime);
	}
	refreshTime=setTimeout(refresh,${refreshInterval}*1000);
</script>