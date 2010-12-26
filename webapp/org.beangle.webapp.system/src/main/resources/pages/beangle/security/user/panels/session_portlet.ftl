[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
    <div class="ui-widget-header ui-corner-all"><span class="title">最近的历史登录信息</span><span class="ui-icon ui-icon-plusthick"></span></div>
    <div class="portlet-content">
      [#if (sessionActivities?size==0)]没有登录过系统[#else]
  	  [@b.grid width="100%" sortable="true" id="listTable" fixPageSize="1"]
  	  [@b.gridhead]
  		[@b.td width="15%" text="登录时间" /]
  		[@b.td width="15%" text="退出时间" /]
  		[@b.td width="10%" text="在线时间"/]
 		[@b.td width="10%" text="主机"/]
      [/@]
   	  [@b.gridbody datas=sessionActivities;sessionActivity]
   		 <td title="${sessionActivity.remark!('')}">${sessionActivity.loginAt?string("yy-MM-dd HH:mm")}</td>
   		 <td>${sessionActivity.logoutAt?string("yy-MM-dd HH:mm")}</td>
   		 <td>${(sessionActivity.onlineTime/60000)?int}分${(sessionActivity.onlineTime/1000)%60}秒</td>
   		 <td>${sessionActivity.host}</td>
  	  [/@]
  	 [/@]
  	 [/#if]
    </div>
</div>
