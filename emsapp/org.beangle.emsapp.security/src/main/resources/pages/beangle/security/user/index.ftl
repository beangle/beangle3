[#ftl]
[@b.head/]
<div>
[#include "../nav.ftl"/]
<table class="indexpanel">
<tr>
	<td class="index_view">
	[@b.form name="userSearchForm" action="!search" title="ui.searchForm" target="userlist" theme="search"]
		[@b.textfields names="user.name,user.fullname,user.creator.fullname,groupName;entity.group"/]
		[@b.select name="categoryId" label="entity.userCategory" items=categories empty="..." option="id,title"/]
		[@b.select name="user.enabled" label="common.status" value="1" empty="..." items={'1':'${b.text("action.activate")}','0':'${b.text("action.freeze")}'}/]
	[/@]
	</td>
	<td class="index_content">
	[@b.div id="userlist" href="user!search?user.enabled=1" /]
	</td>
</tr>
</table>
</div>
[@b.foot/]