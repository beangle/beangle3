[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">最近的历史登录信息</span><span class="ui-icon ui-icon-plusthick"></span></div>
	<div class="portlet-content">
	[#if (sessionActivities?size==0)]没有登录过系统[#else]
	[@b.grid   fixPageSize="1" items=sessionActivities var="sessionActivity"]
		[@b.row]
		[@b.col width="15%" title="登录时间" ]${sessionActivity.loginAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="15%" title="退出时间" ]<span title="${sessionActivity.remark!('')}">${sessionActivity.logoutAt?string("yy-MM-dd HH:mm")}</span>[/@]
		[@b.col width="10%" title="在线时间"]${(sessionActivity.onlineTime/60000)?int}分${(sessionActivity.onlineTime/1000)%60}秒[/@]
		[@b.col width="10%" title="主机" property="host"/]
		[/@]
	[/@]
	[/#if]
	</div>
</div>
