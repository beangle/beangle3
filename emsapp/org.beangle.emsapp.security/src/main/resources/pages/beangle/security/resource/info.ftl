[#ftl]
[@b.head/]
[#include "scope.ftl"/]
[@b.toolbar title="security.resource.info"]bar.addBack();[/@]
<table class="infoTable">
	<tr>
	 <td class="title">标题:</td>
	 <td class="content">${resource.title}</td>
	 <td class="title">${b.text("common.name")}:</td>
	 <td class="content">[#if resource.needParams]${resource.name}(不可独立访问)[#else][@b.a href=resource.name]${resource.name}[/@][/#if]</td>
   </tr>
   <tr>
	<td class="title">${b.text("common.remark")}:</td>
	<td  class="content">${resource.remark!}</td>
	<td class="title">&nbsp;${b.text("common.status")}:</td>
	<td class="content">
		[#if resource.enabled]${b.text("action.activate")}[#else]${b.text("action.unactivate")}[/#if]
	</td>
   </tr>
   <tr>
	 <td class="title">引用菜单:</td>
	 <td class="content">[#list menus as menu](${menu.code})${menu.title}<br/>[/#list]</td>
	 <td class="title">&nbsp;可见范围:</td>
	 <td class="content">[@resourceScope resource.scope/]</td>
   </tr>
   <tr>
	 <td class="title">使用组:</td>
	 <td>
	  [#list groups as group]${group.name}[#if group_has_next]&nbsp;[/#if][/#list]
	 </td>
	 <td class="title">适用用户类别:</td>
	 <td>
	  [#list resource.categories as category][${category.name}][#if category_has_next]&nbsp;[/#if][/#list]
	 </td>
	</tr>
	<tr>
	 <td class="title">使用用户:</td>
	 <td colspan="3">
	  [#list users as user]${user.name}[#if user_has_next]&nbsp;[/#if][/#list]
	 </td>
	</tr>
</table>
[#if resource.entities?size>0]
<fieldSet  align="center">
   <legend>数据权限 限制对象</legend>
   <table class="infoTable">
[#list resource.entities as entity]
   <tr>
	 <td class="title">名称:</td>
	 <td class="content">${entity.name}([#list entity.fields as field]${field.name}-${field.remark!}[#if field_has_next],[/#if][/#list])</td>
	 <td class="title">类型:</td>
	 <td class="content">${entity.type!}</td>
	 <td class="title">描述:</td>
	 <td class="content">${entity.remark!}</td>
   </tr>
[/#list]
   </table>
</fieldSet>
[/#if]
[@b.foot/]