[#ftl]
 <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
    <div class="ui-widget-header ui-corner-all"><span class="title">在线信息</span>
    <span class="ui-icon ui-icon-plusthick"></span></div>
    <div class="portlet-content">
       [#if (onlineActivities?size==0)]没有在线[#else]
  	    [@table.table width="100%" id="onLineUserTable"]
	    [@table.thead]
	      [@table.td width="15%" text="登录时间" id="loginAt"/]
	      [@table.td width="15%" text="最近访问时间" id="lastAccessAt"/]
	      [@table.td width="10%" text="在线时间" id="onlineTime"/]
	      [@table.td width="15%" text="地址" id="host"/]
	      [@table.td width="10%" text="用户身份" id="category"/]
	      [@table.td width="10%" text="状态" id="expired"/]
	   [/@]
	   [@table.tbody datas=onlineActivities;activity]
	      <td>${activity.loginAt?string("MM-dd HH:mm")}</td>
	      <td>${activity.lastAccessAt?string("MM-dd HH:mm")}</td>
	      <td>${(activity.onlineTime)/1000/60}min</td>
	      <td>${activity.host!('')}</td>
	      <td>${activity.category.name}</td>
	      <td>${activity.expired?string("过期","在线")}</td>
	   [/@]
	  [/@]
	  [/#if]
   </div>
  </div>
