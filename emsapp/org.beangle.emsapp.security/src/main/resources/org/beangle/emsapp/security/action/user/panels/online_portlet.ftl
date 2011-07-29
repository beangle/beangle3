[#ftl]
[#if (onlineActivities?size==0)]没有在线[#else]
 <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">在线信息</span>
	</div>
	<div class="portlet-content">
		[@b.grid items=onlineActivities var="activity" sortable="false"]
			[@b.row]
				[@b.col width="35%" title="登录"]<span title="${activity.expired?string('过期','在线')}">${activity.loginAt?string("MM-dd HH:mm")}</span>[/@]
				[@b.col width="30%" title="在线"]${(activity.onlineTime)/1000/60}分[/@]
				[@b.col width="35%" title="地址"]<span title="${activity.category} ${activity.agent!}/${activity.os!('')}">${activity.ip!('')}</span>[/@]
			[/@]
		[/@]
   </div>
  </div>
[/#if]