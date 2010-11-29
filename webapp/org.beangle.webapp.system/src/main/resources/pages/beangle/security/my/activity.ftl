<#include "/template/head.ftl"/>
<link href="${base}/static/themes/default/css/panel.css" rel="stylesheet" type="text/css">
<@sj.head />

<body>
  <style>
    .column {
    	width: 600px;
		float: left;
		padding-bottom: 100px;
	}
	.column div { 
		margin:5px;
	}
	.column div div .ui-icon { 
		float: right;
	}
  </style>
  <table id="userInfoBar"></table>
  
  <@sj.div id="column1" cssClass="column" sortable="true"
   sortableConnectWith=".column" sortablePlaceholder="ui-state-highlight"
    sortableForcePlaceholderSize="true" sortableHandle="div.ui-widget-header"
     sortableCursor="crosshair" sortableOnUpdateTopics="onupdate">
	<#include "online_portlet.ftl"/>
	<#include "../user/panels/session_portlet.ftl"/>
  </@sj.div>

  <script type="text/javascript">
   var bar = new ToolBar('userInfoBar','我最近的登录情况',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addClose("<@text name="action.close"/>");
   
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
<#include "/template/foot.ftl"/>
