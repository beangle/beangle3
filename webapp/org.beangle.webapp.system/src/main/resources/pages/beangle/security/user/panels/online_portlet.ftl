[#ftl]
 <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">在线信息</span>
	<span class="ui-icon ui-icon-plusthick"></span></div>
	<div class="portlet-content">
	   [#if (onlineActivities?size==0)]没有在线[#else]
		[@b.grid  items=onlineActivities var="activity" ]
		[@b.row]
		  [@b.col width="30%" title="登录时间(最近访问)"]<span title="${activity.expired?string("过期","在线")}">${activity.loginAt?string("MM-dd HH:mm")}(${activity.lastAccessAt?string("HH:mm")})</span>[/@]
		  [@b.col width="15%" title="在线时间"]${(activity.onlineTime)/1000/60}min[/@]
		  [@b.col width="15%" title="用户代理" property="authentication.details.agent"]${activity.authentication.details.agent.os!('')}<br/>${activity.authentication.details.agent.browser!('')}[/@]
		  [@b.col width="20%" title="地址" property="authentication.details.agent.ip"/]
		  [@b.col width="15%" title="身份" property="authentication.principal.category.name"/]
		[/@]
		[/@]
	  [/#if]
   </div>
  </div>
