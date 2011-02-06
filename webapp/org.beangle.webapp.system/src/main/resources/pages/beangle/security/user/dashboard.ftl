[#ftl]
[@b.head]
<link href="${base}/static/themes/default/css/panel.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.column {width: 440px;float: left;padding-bottom: 100px;}
div .ui-widget {margin:5px;}
div .ui-widget-header{margin:2px;}
div .portlet-content{margin:2px;}
.ui-icon {float: right;}
</style>
[/@]

[@b.toolbar id="userInfoBar" title="用户权限面板"]
	bar.addClose("${b.text("action.close")}");
[/@]

<div id="column1" class="column">
	[#include "panels/info_portlet.ftl"/]
	[#include "panels/group_portlet.ftl"/]
	[#include "panels/online_portlet.ftl"/]
	[#include "panels/session_portlet.ftl"/]
</div>

<div id="column2" class="column" >
	[#include "panels/restriction_portlet.ftl"/]
	[#include "panels/menu_portlet.ftl"/]
</div>
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
[@b.foot/]
