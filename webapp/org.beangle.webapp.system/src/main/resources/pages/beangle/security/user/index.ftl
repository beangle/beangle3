[#ftl]
[@b.head/]
<div>
[@b.toolbar id="userbar"]
	bar.setTitle('[@b.a href='index']权限管理[/@]-->用户管理');
	bar.addHelp("${b.text("action.help")}");
[/@]
<table class="indexpanel">
<tr>
	<td style="width:180px"  class="index_view">
	[@b.qform name="userSearchForm" action="!search" title="ui.searchForm" target="userlist"]
		[@b.qfields names="user.name,user.fullname,user.creator.fullname,groupName;entity.group"/]
		[@b.qselect name="categoryId" title="entity.userCategory" items=categories empty="..." /]
		[@b.qselect name="user.status" title="common.status" items={'1':'${b.text("action.activate")}','0':'${b.text("action.freeze")}'}/]
	[/@]
	</td>
	<td class="index_content">
	[@b.div id="userlist" href="user!search?user.status=1" /]
	</td>
</tr>
</table>
</div>
[@b.foot/]