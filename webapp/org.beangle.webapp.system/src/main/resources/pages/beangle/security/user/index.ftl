[#ftl]
[@b.head/]
<div>
[@b.toolbar]
	bar.setTitle('[@b.a href='index']权限管理[/@]-->用户管理');
	bar.addHelp("${b.text("action.help")}");
[/@]
<table class="indexpanel">
<tr>
	<td class="index_view">
	[@b.form name="userSearchForm" action="!search" title="ui.searchForm" target="userlist" theme="search"]
		[@b.textfields names="user.name,user.fullname,user.creator.fullname,groupName;entity.group"/]
		[@b.select name="categoryId" label="entity.userCategory" items=categories empty="..." /]
		[@b.select name="user.status" label="common.status" items={'1':'${b.text("action.activate")}','0':'${b.text("action.freeze")}'}/]
	[/@]
	</td>
	<td class="index_content">
	[@b.div id="userlist" href="user!search?user.status=1" /]
	</td>
</tr>
</table>
</div>
[@b.foot/]