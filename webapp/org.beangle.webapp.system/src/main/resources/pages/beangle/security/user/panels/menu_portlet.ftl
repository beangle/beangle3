[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">菜单权限</span><span class="ui-icon ui-icon-plusthick"></span></div>
	<div class="portlet-content">
	<table width="100%" class="grid">
	  <tbody>
	  <tr class="gridhead">
		<th width="5%">[@b.text name="common.id"/]</th>
		<th width="30%">[@b.text name="common.name"/]</th>
		<th width="30%">来源(用户组)</th>
		<th width="20%">可用资源</th>
		<th width="8%">[@b.text name="common.status"/]</th>
	  </tr>
		[#macro i18nTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]
		[#list menus?sort_by("code") as menu]
		<tr class="gray">
		   <td>${menu.code}</td>
		   <td>[#list 1..(menu.code?length) as i]&nbsp;[/#list][@i18nTitle menu/]</td>
		   <td>
				[#list groupMap?keys as groupId][#if groupMenusMap[groupId]?seq_contains(menu)]${groupMap[groupId].name}&nbsp;[/#if][/#list]
		   </td>
		   <td>
		   	[#list menu.resources as resource]
		   	   [#if resources?seq_contains(resource)]
			   [#if ((resource.objects?size)>0)&&resources?seq_contains(resource)]
			   <a href="restriction!info.action?forEdit=1&amp;restrictionType=user&amp;restriction.holder.id=${user.id}" target="restictionFrame" ><font color="red">${resource.title}</font></a>[#rt]
			   [#else][#lt]${resource.title}[/#if][#if resource_has_next]<br/>[/#if]
		   	   [/#if]
		   	[/#list]
		   </td>
		   <td> [#if menu.enabled][@b.text name="action.activate" /][#else][@b.text name="action.unactivate" /][/#if]</td>
		  </tr>
		 [/#list]
	  </tbody>
	 </table>
	</div>
</div>
