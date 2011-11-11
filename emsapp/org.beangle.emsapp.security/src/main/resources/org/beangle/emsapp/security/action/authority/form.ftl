[#ftl]
[@b.head/]
[#include "../status.ftl"/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
<script type="text/javascript">
	function getIds(){
		return(getCheckBoxValue(document.getElementsByName("menuId")));
	}
	function save(){
		document.authorityForm.action="${b.url('!save')}";
		if(confirm("${b.text("alert.authoritySave",ao.name)}")){
			document.authorityForm.submit();
		}
	}
	function checkResource(ele){
		menuDivId=ele.id;
		var tempTarget ;
		tempTarget = document.getElementById(menuDivId);
		if(tempTarget!=null||tempTarget!='undefined'){
			var stats = tempTarget.checked;
			var num=0;
			var tempId = menuDivId+'_'+num;
			while(tempTarget!=null){
				num++;
				tempTarget.checked = stats;
				tempTarget = document.getElementById(tempId);
				tempId = menuDivId+'_'+num;
			}
		}
	}
</script>
<table width="90%" align="center">
<tr>
<td valign="top">
[@b.toolbar]
	bar.setTitle('用户组-->菜单和资源权限');
	bar.addItem("${b.text("action.spread")}","displayAllRowsFor(1);",'${b.theme.iconurl('tree/plus.png')}');
	bar.addItem("${b.text("action.collapse")}","collapseAllRowsFor(1);",'${b.theme.iconurl('tree/minus.png')}');
	bar.addItem("${b.text("action.save")}",save,'save.png');
[/@]
[@b.form name="authorityForm" action="!edit"]
<table width="100%" class="searchTable" id="meunAuthorityTable">
	<tr>
		<td>
		用户组:<select name="group.id" onchange="this.form.submit()" style="width:150px">
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
	<tr class="gridhead">
	<th width="6%"><input type="checkbox" onclick="treeToggleAll(this,checkResource)"/></th>
	<th width="34%">${b.text("common.name")}</th>
	<th width="13%">${b.text("common.id")}</th>
	<th width="40%">可用资源</th>
	<th width="6%">${b.text("common.status")}</th>
	</tr>
	[#macro i18nTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]
	[#list menus?sort_by("code") as menu]

	<tr class="grayStyle [#if !menu.enabled]ui-disabled[/#if]" id="${menu.code}">
		<td	class="gridselect">
			<input type="checkbox" id="checkbox_${menu_index}" onclick="treeToggle(this,checkResource)"  name="menuId" [#if (aoMenus??)&&(aoMenus?seq_contains(menu))]checked="checked"[/#if] value="${menu.id}">
		</td>
		<td>
		<div class="tier${menu.depth}">
			[#if menu.children?size==0]
			<a href="#" class="doc"></a>[#rt]
			[#else]
			<a href="#" class="folder_open" id="${menu.code}_folder" onclick="toggleRows(this);"></a>[#rt]
			[/#if]
			 [@i18nTitle menu/]
		</div>
		</td>
		<td >&nbsp;${menu.code}</td>
		<td>
			[#list menu.resources as resource]
				[#if resources?seq_contains(resource)]
				<input type="checkbox" name="resourceId" id="checkbox_${menu_index}_${resource_index}" [#if aoResources?seq_contains(resource)]checked="checked"[/#if] value="${resource.id}">[#rt]
				${resource.title}
				[/#if]
				[#if resource_index%2==1]<br/>[/#if]
			[/#list]
		</td>
		<td align="center">[@shortEnableInfo menu.enabled/]</td>
	</tr>
	[/#list]
	</tbody>
</table>
[/@]
	</td>
	<td id="dataRealmTD" style="width:300px" valign="top" >
	 [@b.div  href="restriction!tip" id="restictionFrame" style="position:fixed !important;"/]
	</td>
	</tr>
</table>
[@b.foot/]