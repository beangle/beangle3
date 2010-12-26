[#ftl]
[#include "/template/head.ftl"/]
[#include "../status.ftl"/]
<script language="JavaScript" type="text/JavaScript" src="static/scripts/validator.js"></script>
<link href="${base}/static/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/TableTree.js"></script>
<script> defaultColumn=1;</script>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("menuId")));
    }
    function save(){
        document.actionForm.action="authority.action?method=save";
        if(confirm("[@msg.text name="alert.authoritySave" arg0="${ao.name}"/]")){
           document.actionForm.submit();
        }
    }
</script>
<body>
<div>
  <table width="90%" align="center">
  <tr>
  <td valign="top">
  <table id="authorityBar"></table>
  <script>
	var bar = bg.ui.toolbar('authorityBar','<a href="group.action">用户组</a>->菜单和资源权限',null,true,true);
	bar.setMessage('[@getMessage/]');
	bar.addItem("[@text name="action.spread"/]","displayAllRowsFor(2);f_frameStyleResize(self)",'contract.gif');
	bar.addItem("[@text name="action.collapse"/]","collapseAllRowsFor(2);f_frameStyleResize(self)",'expand.gif');
	bar.addItem("[@text  "action.save"/]",save,'save.gif');

   function selectListen(targetId){
   	var tempTarget ;
   	tempTarget = document.getElementById(targetId);
   	if(tempTarget!=null||tempTarget!='undefined'){
	   	var stats = tempTarget.checked;
	   	var num=0;
	   	var tempId = targetId+'_'+num;
	   	while(tempTarget!=null){//||tempTarget!='undefined'
	   		num++;
	   		tempTarget.checked = stats;
	   		tempTarget = document.getElementById(tempId);
	   		tempId = targetId+'_'+num;
	   	}
   	}
   }
  </script>
  
  <table width="100%" class="searchTable" id="meunAuthorityTable">
    <form name="actionForm" method="post" action="authority!edit.action" onsubmit="return false;">
	   <tr>
	    <td>
	    用户组:<select name="group.id" onchange="this.form.submit()" style="width:150px">
             [#assign mngGroups=manager.mngGroups/]
             [#if allGroups??][#assign mngGroups=allGroups/][/#if]
             [#list mngGroups?sort_by("name")! as group]
              <option value="${group.id}" [#if group.id=ao.id]selected[/#if]>${group.name}</option>
             [/#list]
        </select>
	    </td>
	    <td class="title">
	    菜单配置:<select name="menuProfileId" style="width:150px;" onchange="this.form.submit();">
	        [#list menuProfiles as profile]
	        <option value="${profile.id}" [#if profile=menuProfile]selected[/#if]>${profile.name}</optino>
	        [/#list]
	        </select>
	    </td>
	    <td><input name="displayFreezen" [#if Parameters['displayFreezen']??]checked[/#if] onclick="this.form.submit();" id="displayFreezen" type="checkbox"]<label for="displayFreezen">显示冻结菜单</label></td>
	   </tr>
  </table>
  
  <table width="100%" class="grid">
  <tbody>
  <tr class="thead">
    <th width="6%"><input type="checkbox" onClick="treeToggleAll(this)"></th>
    <th width="34%">[@text name="common.name"/]</th>
    <th width="13%">[@text name="common.id"/]</th>
    <th width="40%">可用资源</th> 
    <th width="6%">[@text name="common.status"/]</th>
  </tr>
    [#macro i18nTitle(entity)][#if language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]       	
    [#list menus?sort_by("code") as menu]
	[#assign tdid="1-"]
    [#if menu.code?length!=1]
	    [#list 1..menu.code?length as menuIdChar]
	        [#if menuIdChar%2==1]
	        [#assign tdid = tdid + menu.code[menuIdChar-1..menuIdChar] +"-"]
	        [/#if]
	    [/#list]
    [/#if]
    [#assign tdid = tdid[0..tdid?length-2]]
    
    <tr class="grayStyle" id="${tdid}">
	   <td   class="select">
    	   <input type="checkBox" id="checkBox_${menu_index}" onclick="treeToggle(this);selectListen('checkBox_${menu_index}');"  name="menuId" [#if (aoMenus??)&&(aoMenus?seq_contains(menu))]checked[/#if] value="${menu.id}"]
       </td>
	   <td>
	    <div class="tier${menu.code?length/2}">
           [#if menu.children?size==0]
           <a href="#" class="doc"></a>[#rt]
           [#else]
           <a href="#" class="folder" onclick="toggleRows(this);f_frameStyleResize(self)"></a>[#rt]
           [/#if]
             [@i18nTitle menu/]
	    </div>
	   </td>
       <td >&nbsp;${menu.code}</td>
       <td>
       	[#list menu.resources as resource]
       	   [#if resources?seq_contains(resource)]
       	   <input type="checkBox" name="resourceId" id="checkBox_${menu_index}_${resource_index}" [#if aoResources?seq_contains(resource)]checked[/#if] value="${resource.id}">[#rt]
	       [#if ((resource.objects?size)>0)&&aoResources?seq_contains(resource)]
	       <a href="restriction.action?method=info&forEdit=1&restrictionType=authority&restriction.holder.id=${aoResourceAuthorityMap[resource.id?string]}" target="restictionFrame" ><font color="red">${resource.title}</font></a>[#rt]
	       [#else][#lt]${resource.title}[/#if]
       	   [/#if]
       	   [#if resource_index%2==1]<br>[/#if]
       	[/#list]
       </td>
       <td align="center">[@shortEnableInfo menu.enabled/]</td>
	  </tr>
	 [/#list]
  </tbody>
  </form>
 </table>
</div>
   </td>
   <td id="dataRealmTD" style="width:300px" valign="top" >
     <iframe  src="restriction.action?method=tip" id="restictionFrame" name="restictionFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" 	 frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
[#include "/template/foot.ftl"/]
