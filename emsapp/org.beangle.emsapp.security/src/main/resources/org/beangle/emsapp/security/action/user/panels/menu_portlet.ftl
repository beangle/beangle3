[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">菜单权限</span></div>
	<div class="portlet-content  grid">
	<table width="100%" class="gridtable">
	  <tbody>
	  <tr class="gridhead">
		<th width="40%">${b.text("common.code")}${b.text("common.name")}</th>
		<th width="60%">可用资源</th>
	  </tr>
		[#macro i18nTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]
		[#list menus?sort_by("code") as menu]
		<tr class="[#if menu_index%2==0]griddata-even[#else]griddata-odd[/#if]">
		   <td align="left" title="[#list groupMenusMap?keys as group][#if groupMenusMap.get(group)?seq_contains(menu)]${group.name}&nbsp;[/#if][/#list]">
		   [#list 1..menu.depth as i]&nbsp;&nbsp;[/#list][#if menu.children?size!=0]<em>${menu.code}[@i18nTitle menu/]</em>[#else]${menu.code}[@i18nTitle menu/][/#if]
		   </td>
		   <td>
		   	[#list menu.resources as resource]
		   	   [#if resources?seq_contains(resource)]${resource.title}[#if resource_has_next]&nbsp;[/#if][/#if]
		   	[/#list]
		   </td>
		  </tr>
		 [/#list]
	  </tbody>
	 </table>
	</div>
</div>