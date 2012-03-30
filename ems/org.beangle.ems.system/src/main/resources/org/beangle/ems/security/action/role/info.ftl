[#ftl]
[@b.head/]
[@b.toolbar title='info.role']
bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
	<tr>
	 <td class="title" width="10%">${b.text("common.name")}:</td>
	 <td class="content" width="40%"> ${role.name}</td>
	 <td class="title" width="10%">${b.text("common.creator")}:</td>
	 <td class="content" width="40%">${role.owner.name!}  </td>
	</tr>
	<tr>
	 <td class="title" >${b.text("common.createdAt")}:</td>
	 <td class="content">${role.createdAt?string("yyyy-MM-dd")}</td>
	 <td class="title" >${b.text("common.updatedAt")}:</td>
	 <td class="content">${role.updatedAt?string("yyyy-MM-dd")}</td>
	</tr>
	<tr>
	<td class="title" >是否动态组:</td>
	<td  class="content">${role.dynamic?string("是","否")}</td>
	<td class="title" >&nbsp;${b.text("common.status")}:</td>
	<td class="content">
		[#if role.enabled] ${b.text("action.activate")}
		[#else]${b.text("action.freeze")}
		[/#if]
	</td>
	</tr>
	<tr>
	<td class="title" >${b.text("role.users")}:</td>
	<td  class="content" colspan="3">[#list role.members?sort_by(["user","name"]) as m] [#if m.user.enabled][@ems.userinfo user=m.user/][#else]<s>[@ems.userinfo user=m.user/]</s>[/#if]&nbsp;[/#list]</td>
	</tr>
	<tr>
	<td class="title" >${b.text("common.remark")}:</td>
	<td  class="content" colspan="3">${role.remark!}</td>
	</tr>
	[#--<tr>
		<td colspan="4">[@b.div href="restriction!info?restriction.holder.id=${role.id}&restrictionType=role" /]</td>
	</tr>
	--]
</table>
[@b.foot/]
