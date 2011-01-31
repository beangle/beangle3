[#ftl]
[@b.head/]
[#include "scope.ftl"/]

<table id="resourceInfoBar"></table>
	 <table class="infoTable">
	   <tr>
		 <td class="title">标题:</td>
		 <td class="content">${resource.title}</td>
		 <td class="title">${b.text("common.name")}:</td>
		 <td class="content">${resource.name}</td>
	   </tr>
	   <tr>
		<td class="title">${b.text("common.description")}:</td>
		<td  class="content">${resource.description!}</td>
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
	  [#list resource.entities as entity]
	  <fieldSet  align="center">
 	   <legend>数据权限 限制对象</legend>
	   <table class="infoTable">
	   <tr>
		 <td class="title">名称:</td>
		 <td class="content">${entity.name}</td>
		 <td class="title">类型:</td>
		 <td class="content">${entity.type!}</td>
		 <td class="title">描述:</td>
		 <td class="content">${entity.remark!}</td>
	   </tr>
	   </table>
	  </fieldSet>
	  [/#list]
  <script type="text/javascript">
   var bar = bg.ui.toolbar('resourceInfoBar','${b.text("security.resource.info")}');
   bar.setMessage('[@b.messages/]');
   bar.addBack("${b.text("action.back")}");
  </script>
 
[@b.foot/]
