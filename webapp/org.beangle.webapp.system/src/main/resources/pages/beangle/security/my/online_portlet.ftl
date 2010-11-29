 <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
    <div class="ui-widget-header ui-corner-all"><span class="title">在线信息</span>
    <span class="ui-icon ui-icon-plusthick"></span></div>
    <div class="portlet-content">
       <#if (onlineActivities?size==0)>没有在线<#else>
  	    <table width="100%" id="onLineUserTable">
	    <tr>
	      <td width="15%">登录时间</td>
	      <td width="15%">最近访问时间</td>
	      <td width="10%">在线时间</td>
	      <td width="15%">地址</td>
	      <td width="10%">用户身份</td>
	      <td width="10%">状态</td>
	   </tr>
	   <#list onlineActivities as activity>
	   <tr>
	      <td>${activity.loginAt?string("MM-dd HH:mm")}</td>
	      <td>${activity.lastAccessAt?string("MM-dd HH:mm")}</td>
	      <td>${(activity.onlineTime)/1000/60}min</td>
	      <td>${activity.host!('')}</td>
	      <td>${activity.category.name}</td>
	      <td>${activity.expired?string("过期","在线")}</td>
	   </tr>
	   </#list>
	   </table>
	  </#if>
   </div>
  </div>