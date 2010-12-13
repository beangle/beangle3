[#ftl]
[#include "/template/head.ftl"/]
<link href="${base}/static/themes/default/css/panel.css" rel="stylesheet" type="text/css">

[@sj.head /]
<body>
  <style>
	.column {
		width: 440px;
		float: left;
		padding-bottom: 100px;
	}
	.column div { 
		margin:5px;
	}
	.ui-icon { 
		float: right;
	}
  </style>
  <table id="userInfoBar"></table>
  
  [@sj.div id="column1" cssClass="column" sortable="true"
   sortableConnectWith=".column" sortablePlaceholder="ui-state-highlight"
    sortableForcePlaceholderSize="true" sortableHandle="div.ui-widget-header"
     sortableCursor="crosshair" sortableOnUpdateTopics="onupdate"]
    [#include "../user/panels/info_portlet.ftl"/]
	[#include "../user/panels/manage_portlet.ftl"/]
	[#include "../user/panels/online_portlet.ftl"/]
	[#include "../user/panels/session_portlet.ftl"/]
  [/@]
  
  [@sj.div id="column2" cssClass="column" sortable="true"
   sortableConnectWith=".column" sortablePlaceholder="ui-state-highlight"
    sortableForcePlaceholderSize="true" sortableHandle="div.ui-widget-header"
     sortableCursor="crosshair" sortableOnUpdateTopics="onupdate"]
	[#include "../user/panels/restriction_portlet.ftl"/]
	[#include "../user/panels/menu_portlet.ftl"/]
  [/@]

  <script type="text/javascript">
   var bar = new ToolBar('userInfoBar','用户权限面板',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addClose("[@text name="action.close"/]");
   
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
