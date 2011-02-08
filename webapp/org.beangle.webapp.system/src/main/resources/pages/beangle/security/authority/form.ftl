[#ftl]
[@b.head/]
[#include "../status.ftl"/]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
<link href="${base}/static/css/tableTree.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="${base}/static/scripts/common/TableTree.js"></script>
<script type="text/javascript"> defaultColumn=1;</script>
<script type="text/javascript">
	function getIds(){
	   return(getCheckBoxValue(document.getElementsByName("menuId")));
	}
	function save(){
		document.actionForm.action="${b.url('!save')}";
		if(confirm("${b.text("alert.authoritySave",ao.name)}")){
		   document.actionForm.submit();
		}
	}
</script>
<div>
  <table width="90%" align="center">
  <tr>
  <td valign="top">
[@b.toolbar id="authorityBar"]
	bar.setTitle('[@b.a href="group"]用户组[/@]-->菜单和资源权限');
	bar.addItem("${b.text("action.spread")}","displayAllRowsFor(2);",'contract.gif');
	bar.addItem("${b.text("action.collapse")}","collapseAllRowsFor(2);",'expand.gif');
	bar.addItem("${b.text("action.save")}",save,'save.gif');
[/@]
  <script type="text/javascript">
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
	<form name="actionForm" method="post" action="${b.url('!edit')}" onsubmit="return false;">
	   <tr>
		<td>
		用户组:<select name="group.id" onchange="this.form.submit()" style="width:150px">
			 [#assign mngGroups=manager.mngGroups/]
			 [#if allGroups??][#assign mngGroups=allGroups/][/#if]
			 [#list mngGroups?sort_by("name")! as group]
			  <option value="${group.id}" [#if group.id=ao.id]selected="selected"[/#if]>${group.name}</option>
			 [/#list]
		</select>
		</td>
		<td class="title">
		菜单配置:<select name="menuProfileId" style="width:150px;" onchange="this.form.submit();">
			[#list menuProfiles as profile]
			<option value="${profile.id}" [#if profile=menuProfile]selected="selected"[/#if]>${profile.name}</option>
			[/#list]
			</select>
		</td>
		<td><input name="displayFreezen" [#if Parameters['displayFreezen']??]checked="checked"[/#if] onclick="this.form.submit();" id="displayFreezen" type="checkbox"><label for="displayFreezen">显示冻结菜单</label></td>
	   </tr>
  </table>

  <table width="100%" class="gridtable">
  <tbody>
  <tr class="thead">
	<th width="6%"><input type="checkbox" onclick="treeToggleAll(this)"/></th>
	<th width="34%">${b.text("common.name")}</th>
	<th width="13%">${b.text("common.id")}</th>
	<th width="40%">可用资源</th>
	<th width="6%">${b.text("common.status")}</th>
  </tr>
	[#macro i18nTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]
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
	   <td   class="gridselect">
		   <input type="checkbox" id="checkbox_${menu_index}" onclick="treeToggle(this);selectListen('checkbox_${menu_index}');"  name="menuId" [#if (aoMenus??)&&(aoMenus?seq_contains(menu))]checked="checked"[/#if] value="${menu.id}">
	   </td>
	   <td>
		<div class="tier${menu.code?length/2}">
		   [#if menu.children?size==0]
		   <a href="#" class="doc"></a>[#rt]
		   [#else]
		   <a href="#" class="folder" onclick="toggleRows(this);"></a>[#rt]
		   [/#if]
			 [@i18nTitle menu/]
		</div>
	   </td>
	   <td >&nbsp;${menu.code}</td>
	   <td>
	   	[#list menu.resources as resource]
	   	   [#if resources?seq_contains(resource)]
	   	   <input type="checkbox" name="resourceId" id="checkbox_${menu_index}_${resource_index}" [#if aoResources?seq_contains(resource)]checked="checked"[/#if] value="${resource.id}">[#rt]
		   [#if ((resource.entities?size)>0)&&aoResources?seq_contains(resource)]
		   [@b.a href="restriction!info?forEdit=1&restrictionType=authority&restriction.holder.id=${aoResourceAuthorityMap[resource.id?string]}" target="restictionFrame" ]<font color="red">${resource.title}</font>[/@][#rt]
		   [#else][#lt]${resource.title}[/#if]
	   	   [/#if]
	   	   [#if resource_index%2==1]<br/>[/#if]
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
	 [@b.div  href="restriction!tip" id="restictionFrame"/]
	</td>
   </tr>
  </table>
[@b.foot/]