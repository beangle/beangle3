<#assign online=0><#assign max=0>
<#list onlineProfiles as profile>
<#assign online=online+profile.online/><#assign max=max+profile.capacity/>
</#list>
<table id="activityBar"></table>
<script>
	var bar = new ToolBar('activityBar','在线${online}/上限${max} (<#list onlineProfiles?sort_by(["category"]) as profile>${profile.category.name}${profile.online}/${profile.capacity}  </#list>) ${now?string("yyyy-MM-dd HH:mm:ss")}',null,true,true);
	bar.setMessage('<@s.actionmessage theme="beangle"/>');
	bar.addItem("刷新","refresh()",'refresh.gif');
	bar.addItem("结束会话","invalidateSession()",'delete.gif');
</script>
<@s.form name="invalidateForm" theme="simple" id="invalidateForm" action="monitor!invalidate">
<@table.table width="100%" id="activityListTable" sortable="true" target="ui-tabs-1">
	<@table.thead>
		<@table.selectAllTd id="sessionId"/>
		<@table.sortTd width="15%" text="用户名" id="authentication.username"/>
		<@table.sortTd width="12%" text="登录时间" id="loginAt"/>
		<@table.sortTd width="12%" text="最近访问时间" id="lastAccessAt"/>
		<@table.sortTd width="8%" text="在线时间" id="onlineTime"/>
		<@table.sortTd width="15%" text="地址" id="details.agent.ip"/>
		<@table.sortTd width="10%" text="操作系统" id="details.agent.os"/>
		<@table.sortTd width="10%" text="浏览器" id="details.agent.agent"/>
		<@table.sortTd width="8%" text="身份" id="category.name"/>
		<@table.sortTd width="8%" text="状态" id="expired"/>
	</@>
	<@table.tbody datas=onlineActivities;activity>
		<@table.selectTd id="sessionId" value=activity.sessionid/>
		<td><A href="user!dashboard.action?user.id=${activity.authentication.principal.id}" target="_blank">&nbsp;${activity.authentication.principal.fullname!('')}(${activity.authentication.principal.username!})</a></td>
		<td>${activity.loginAt?string("yy-MM-dd HH:mm")}</td>
		<td>${activity.lastAccessAt?string("yy-MM-dd HH:mm")}</td>
		<td>${(activity.onlineTime)/1000/60}min</td>
		<td>${activity.authentication.details.agent.ip!('')}</td>
		<td>${activity.authentication.details.agent.os!('')}/${activity.authentication.details.agent.osVersion!('')}</td>
		<td>${activity.authentication.details.agent.agent!('')}/${activity.authentication.details.agent.agentVersion!('')}</td>
		<td>${activity.authentication.principal.category.name}</td>
		<td>${activity.expired?string("过期","在线")}</td>
	</@>
</@>
<@sj.submit type="button" id="invalidateBtn" cssStyle="display:none" targets="ui-tabs-1"/>
</@>
	<#assign refreshInterval=Parameters['interval']!"5"/>
	定时每${refreshInterval}秒刷新
<script>
	function refresh(){
		goPage("activityListTable");
	}
	function invalidateSession(){
		$('#invalidateBtn').click();
	}
	if(typeof refreshTime != undefined){
		clearTimeout(refreshTime);
	}
	refreshTime=setTimeout(refresh,${refreshInterval}*1000);
</script>
