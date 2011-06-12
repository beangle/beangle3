[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">菜单权限</span><span class="ui-icon ui-icon-plusthick"></span></div>
	<div class="portlet-content  grid">
	<table width="100%" class="gridtable">
	  <tbody>
	  <tr class="gridhead">
		<th width="5%" align="left">${b.text("common.code")}</th>
		<th width="30%">${b.text("common.name")}</th>
		<th width="30%">来源(用户组)</th>
		<th width="35%">可用资源</th>
	  </tr>
		[#macro i18nTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]
		[#list menus?sort_by("code") as menu]
		<tr class="[#if menu_index%2==0]griddata-even[#else]griddata-odd[/#if]">
		   <td align="left">${menu.code}</td>
		   <td align="left">[#list 1..(menu.code?length) as i]&nbsp;[/#list][@i18nTitle menu/]</td>
		   <td>
				[#list groupMap?keys as groupId][#if groupMenusMap[groupId]?seq_contains(menu)]${groupMap[groupId].name}&nbsp;[/#if][/#list]
		   </td>
		   <td>
		   	[#list menu.resources as resource]
		   	   [#if resources?seq_contains(resource)]
			   [#if ((resource.entities?size)>0)&&resources?seq_contains(resource)]
				[@b.a href="restriction!info?restrictionType=user&restriction.holder.id=${user.id}"]<font color="red">${resource.title}</font>[/@][#rt]
			   [#else][#lt]${resource.title}[/#if][#if resource_has_next]&nbsp;[/#if]
		   	   [/#if]
		   	[/#list]
		   </td>
		  </tr>
		 [/#list]
	  </tbody>
	 </table>
	</div>
</div>