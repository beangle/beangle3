[#ftl]
[#include "/template/head.ftl"/]
<link href="${base}/static/themes/default/css/panel.css" rel="stylesheet" type="text/css">
<style>
 .module{margin:0 3 5px;line-height:1.3em;float:left;width:42%;}
</style>
  <div id="userStat" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('userStat');">用户统计</a>
     </h2>
     <div class="modulebody">
     [@table.table width="94%" id="userStatTable"]
	    [@table.thead]
	      [@table.td width="35%" text="类别"/]
	      [@table.td width="10%" text="状态"/]
	      [@table.td width="15%" text="数量"/]
	   [/@]
	   [@table.tbody datas=groupStat;stat]
	      <td>${categories.get(stat[0]).name}</td>
	      <td>${stat[1]?string("激活","禁用")}</td>
	      <td>${stat[2]}</td>
	   [/@]
	  [/@]
    </div>
   </div>
   
   <div id="groupStat" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('groupStat');">用户组统计</a>
     </h2>
     <div class="modulebody">
  	 [@table.table width="94%" id="groupStatTable"]
	    [@table.thead]
	      [@table.td width="35%" text="类别"/]
	      [@table.td width="10%" text="状态"/]
	      [@table.td width="15%" text="数量"/]
	   [/@]
	   [@table.tbody datas=groupStat;stat]
	      <td>${categories.get(stat[0]).name}</td>
	      <td>${stat[1]?string("激活","禁用")}</td>
	      <td>${stat[2]}</td>
	   [/@]
	  [/@]
    </div>
   </div>
   
  <div id="resource" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('resource');">系统资源</a>
     </h2>
     <div class="modulebody">
     	   [@table.table width="94%" id="resourceStat"]
		    [@table.thead]
		      [@table.td width="10%" text="状态"/]
		      [@table.td width="15%" text="资源数"/]
		   [/@]
		   [@table.tbody datas=resourceStat;stat]
		      <td>${stat[0]?string("激活","禁用")}</td>
		      <td>${stat[1]}</td>
		   [/@]
		  [/@]
     </div>
	</div>

  <div id="menu" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('menu');">菜单设置</a>
     </h2>
     <div class="modulebody">
	     [@table.table width="94%" id="menuStatTable"]
		    [@table.thead]
		      [@table.td width="30%" text="类别"/]
		      [@table.td width="10%" text="状态"/]
		      [@table.td width="15%" text="菜单数"/]
		   [/@]
	 		[#list menuProfiles as profile]
		   [@table.tbody datas=menuStats.get(profile.id);stat]
		      <td>${profile.name}</td>
		      <td>${stat[0]?string("激活","禁用")}</td>
		      <td>${stat[1]}</td>
		   [/@]
			[/#list]
		  [/@]
    </div>
   </div>

  <div id="restriction" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('restriction');">数据权限</a>
     </h2>
     <div class="modulebody">
     	 限制模式数量:${patternStat[0]}<br>
     	 模式参数数量:${paramStat[0]}<br>
     </div>
</div>
  <script>
   function _wi_tm(moudleId){
       var id= document.getElementById(moudleId);   
	   if(id.className=="module collapsed"){
	     id.className="module expanded";
	   }else{
	     id.className="module collapsed";
	   }
  }
  </script>
