[#ftl]
[@b.head/]
<style type="text/css">
.column {width: 440px;float: left;padding-bottom: 100px;}
div .ui-widget {margin:5px;}
div .ui-widget-header{margin:2px;}
div .portlet-content{margin:2px;}
.ui-icon {float: right;}
</style>
[@b.toolbar title="我最近的登录情况"]
bar.addClose("${b.text("action.close")}");
[/@]

<div id="column1" class="column" >
	[#include "../user/panels/online_portlet.ftl"/]
	[#include "../user/panels/session_portlet.ftl"/]
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