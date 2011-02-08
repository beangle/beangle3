[#ftl]
[@b.head/]
[@b.toolbar title="info.module.detail"]
bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
   <tr>
	 <td class="title" >&nbsp;${b.text("common.id")}:</td>
	 <td class="content">${menu.code}  </td>
	 <td class="title" >&nbsp;${b.text("menu.entry")}:</td>
	 <td class="content">${menu.entry!}</td>
   </tr>
   <tr>
	 <td class="title" >&nbsp;标题:</td>
	 <td class="content">${menu.title}</td>
	 <td class="title" >&nbsp;英文标题:</td>
	 <td class="content">${menu.engtitle!}</td>
   </tr>
   <tr>
	<td class="title" >&nbsp;${b.text("common.description")}:</td>
	<td  class="content" >${menu.description!}</td>
	<td class="title" >&nbsp;${b.text("common.status")}:</td>
	<td class="content">
		[#if menu.enabled]${b.text("action.activate")}[#else]${b.text("action.unactivate")}[/#if]
	</td>
   </tr>
   <tr>
	 <td class="title" >&nbsp;引用资源:</td>
	 <td class="content">[#list menu.resources as resource](${resource.name})${resource.title}<br/>[/#list]</td>
	 <td class="title">使用组:</td>
	 <td>
	  [#list groups! as group]${group.name}[#if group_has_next]<br/>[/#if][/#list]
	 </td>
   </tr>
</table>
[@b.foot/]