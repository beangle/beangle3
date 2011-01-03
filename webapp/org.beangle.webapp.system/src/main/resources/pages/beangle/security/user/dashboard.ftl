[#ftl]
[@b.xhtmlhead]
<link href="${base}/static/themes/default/css/panel.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.column {width: 440px;float: left;padding-bottom: 100px;}
.column div {margin:5px;}
.ui-icon {float: right;}
</style>
[@sj.head /]
[/@]
<body>
[@b.toolbar id="userInfoBar" title="用户权限面板"]
	bar.addClose("[@b.text name="action.close"/]");
[/@]

[@sj.div id="column1" cssClass="column" sortable="true"
   sortableConnectWith=".column" sortablePlaceholder="ui-state-highlight"
	sortableForcePlaceholderSize="true" sortableHandle="div.ui-widget-header"
	 sortableCursor="crosshair" sortableOnUpdateTopics="onupdate"]
	[#include "panels/info_portlet.ftl"/]
	[#include "panels/group_portlet.ftl"/]
	[#include "panels/online_portlet.ftl"/]
	[#include "panels/session_portlet.ftl"/]
[/@]

  [@sj.div id="column2" cssClass="column" sortable="true"
   sortableConnectWith=".column" sortablePlaceholder="ui-state-highlight"
	sortableForcePlaceholderSize="true" sortableHandle="div.ui-widget-header"
	 sortableCursor="crosshair" sortableOnUpdateTopics="onupdate"]
	[#include "panels/restriction_portlet.ftl"/]
	[#include "panels/menu_portlet.ftl"/]
  [/@]
  <script type="text/javascript">
   function _wi_tm(moudleId){
	   var id= document.getElementById(moudleId);
	   if(id.className=="module collapsed"){
		 id.className="module expanded";
	   }else{
		 id.className="module collapsed";
	   }
   }
   $(function() {
		$(".column div div .ui-icon").click(function() {
			$(this).toggleClass("ui-icon-minusthick");
			$(this).parents(".column div").find(".portlet-content").toggle();
		});
	});
  </script>
 </body>
[#include "/template/foot.ftl"/]
