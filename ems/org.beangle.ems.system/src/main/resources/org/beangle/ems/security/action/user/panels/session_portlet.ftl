[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">最近的历史登录信息</span></div>
	<div class="portlet-content">
	[#if (sessioninfoLogs?size==0)]没有登录过系统[#else]
	[@b.grid fixPageSize="1" items=sessioninfoLogs var="sessioninfoLog" sortable="false"]
		[@b.row]
		[@b.col width="15%" title="sessioninfo.loginAt" ]<span title="${b.text('sessioninfo.logoutAt')}:${sessioninfoLog.logoutAt?string("yy-MM-dd HH:mm")}">${sessioninfoLog.loginAt?string("yy-MM-dd HH:mm")}</span>[/@]
		[@b.col width="10%" title="sessioninfo.onlineTime" ]<span title="${sessioninfoLog.remark!('')}">${(sessioninfoLog.onlineTime/60000)?int}分${(sessioninfoLog.onlineTime/1000)%60}秒</span>[/@]
		[@b.col width="10%" title="sessioninfo.ip" property="ip"/]
		[/@]
	[/@]
	[/#if]
	</div>
</div>
