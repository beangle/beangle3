[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="groupSearchForm"  action="!search" target="grouplist" title="ui.searchForm" theme="search"]
			[@b.textfields names="userGroup.name;common.name,userGroup.owner.name;common.creator"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="grouplist" href="!search?orderBy=userGroup.code" /]</td>
	</tr>
</table>
[@b.foot/]