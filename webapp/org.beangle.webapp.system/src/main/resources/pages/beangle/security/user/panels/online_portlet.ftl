[#ftl]
 <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">在线信息</span>
	<span class="ui-icon ui-icon-plusthick"></span></div>
	<div class="portlet-content">
	   [#if (onlineActivities?size==0)]没有在线[#else]
  		[@b.grid  items=onlineActivities var="activity" ]
		[@b.row]
		  [@b.col width="15%" title="登录时间"]${activity.loginAt?string("MM-dd HH:mm")}[/@]
		  [@b.col width="15%" title="最近访问时间"]${activity.lastAccessAt?string("MM-dd HH:mm")}[/@]
		  [@b.col width="10%" title="在线时间"]${(activity.onlineTime)/1000/60}min[/@]
		  [@b.col width="15%" title="地址" property="host"/]
		  [@b.col width="10%" title="用户身份" property="category.name"/]
		  [@b.col width="10%" title="状态"]${activity.expired?string("过期","在线")}[/@]
	   [/@]
	  [/@]
	  [/#if]
   </div>
  </div>
