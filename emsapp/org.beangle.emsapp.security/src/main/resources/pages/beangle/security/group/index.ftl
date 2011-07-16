[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="groupSearchForm"  action="!search" target="grouplist" title="ui.searchForm" theme="search"]
			[@b.textfields names="userGroup.name;common.name,userGroup.owner.name;common.creator"/]
			[@b.select items=categories empty="..." name="userGroup.category.id" label="entity.userCategory" option="id,title"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="grouplist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]