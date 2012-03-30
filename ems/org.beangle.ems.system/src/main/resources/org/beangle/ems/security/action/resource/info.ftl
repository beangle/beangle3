[#ftl]
[@b.head/]
[#include "scope.ftl"/]
[@b.toolbar title="security.resource.info"]bar.addBack();[/@]
<table class="infoTable">
	<tr>
	 <td class="title">标题:</td>
	 <td class="content">${resource.title}</td>
	 <td class="title">${b.text("common.name")}:</td>
	 <td class="content">[#if !resource.entry]${resource.name}(不可独立访问)[#else][@b.a href=resource.name]${resource.name}[/@][/#if]</td>
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
	 <td  colspan="3">
	  [#list roles as role]${role.name}[#if role_has_next]&nbsp;[/#if][/#list]
	 </td>
	</tr>
</table>
[@b.foot/]