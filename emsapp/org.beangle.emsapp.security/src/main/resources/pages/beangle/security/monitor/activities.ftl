[#ftl]
[#assign statTitle]${sessionStat.sessions}/${sessionStat.capacity} [#list sessionStat.details?keys as k]${k}(${sessionStat.details.get(k).online}/${sessionStat.details.get(k).capacity}) [/#list][/#assign]
[@b.toolbar title="当前服务器会话 ${statTitle}"][/@]
[@b.form name="invalidateForm" id="invalidateForm" action="!invalidate"]
[@b.grid  id="activityListTable" items=sessioninfos var="sessioninfo"]
	[@b.gridbar]
	bar.addItem("刷新","refresh()",'refresh.png');
	bar.addItem("过期","expireSession()",'edit-delete.png');
	bar.addItem("结束","killSession()",'edit-delete.png');
	[/@]
	[@b.row]
		[@b.boxcol boxname="sessionId" property="sessionid"/]
		[@b.col width="15%" title="sessioninfo.username" property="username"]
			[@b.a href="user!dashboard?user.name=${sessioninfo.username}" target="_blank"]${(sessioninfo.fullname!(''))?html}(${(sessioninfo.username!(''))?html})[/@]
		[/@]
		[@b.col width="12%" title="sessioninfo.loginAt" property="loginAt"]${sessioninfo.loginAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="8%" title="sessioninfo.onlineTime" property="onlineTime" sortable="false"]${(sessioninfo.onlineTime)/1000/60}min[/@]
		[@b.col width="15%" title="sessioninfo.ip" property="ip"/]
		[@b.col width="10%" title="sessioninfo.os" property="os"/]
		[@b.col width="10%" title="sessioninfo.agent" property="agent"/]
		[@b.col width="8%" title="sessioninfo.category" property="category"/]
		[@b.col width="8%" title="sessioninfo.expired" property="expiredAt"]${sessioninfo.expired?string("过期","在线")}[/@]
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