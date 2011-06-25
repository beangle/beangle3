[#ftl]
[@b.head/]
[@b.toolbar title='${b.text("entity.user")}${b.text("common.detail")}']bar.addBack("${b.text("action.back")}");[/@]
<table class="infoTable">
	<tr>
	 <td class="title">${b.text("common.name")}:</td>
	 <td class="content"> ${user.name!}</td>
	 <td class="title">${b.text("user.fullname")}:</td>
	 <td class="content">${(user.fullname)!}  </td>
	</tr>
	<tr>
	 <td class="title" >${b.text("common.email")}:</td>
	 <td class="content">${user.mail!} </td>
	 <td class="title" >${b.text("common.createdAt")}:</td>
	 <td class="content">${user.createdAt?string("yyyy-MM-dd")}</td>
	</tr>
	<tr>
	 <td class="title" >${b.text("common.status")}:</td>
	 <td class="content">[#if user.enabled] ${b.text("action.activate")}[#else]${b.text("action.freeze")}[/#if]</td>
	 <td class="title" >${b.text("common.updatedAt")}:</td>
	 <td class="content">${user.updatedAt?string("yyyy-MM-dd")}</td>
	</tr>
	<tr>
	<td class="title" >${b.text("entity.userCategory")}:</td>
	<td  class="content">
		[#list user.categories as category]${category.name}[#if category.id==user.defaultCategory.id](默认)[/#if][#if category_has_next],[/#if][/#list]
	</td>
	<td class="title" >${b.text("common.creator")}:</td>
	<td class="content">${(user.creator.name)!}  </td>
	</tr>
	<tr>
	<td class="title" >${b.text("group")}:</td>
	<td  class="content" colspan="3">[#list user.groups! as g]${g.group.name}([#if g.member]成员[/#if][#if g.manager] 管理[/#if][#if g.granter] 授权[/#if])[/#list]</td>
	</tr>
	<tr>
	<td class="title" >${b.text("user.mngGroups")}:</td>
	<td  class="content" colspan="3">[#list user.mngGroups! as group]${group.name}[/#list]</td>
	</tr>
	<tr>
	<td class="title" >${b.text("common.remark")}:</td>
	<td class="content" colspan="3">${user.remark!}</td>
	</tr>
	<tr>
	<td class="title" >权限控制台:</td>
	<td class="content" colspan="3">[@b.a target="_blank" href="user!dashboard?user.id=${user.id}"]查看权限用户控制台[/@]</td>
	</tr>
</table>
[@b.foot/]