[#ftl]
[@b.head/]
<div>
[@b.toolbar id="userbar"]
	bar.setTitle('[@b.a href='dashboard']权限管理[/@]-->用户管理');
	bar.addHelp("${b.text("action.help")}");
[/@]
<table class="indexpanel">
<tr>
	<td style="width:180px"  class="index_view">[#include "searchForm.ftl"/]</td>
	<td class="index_content">
	[@b.div id="userlist" href="user!search?user.status=1" /]
	</td>
</tr>
</table>
</div>
[@b.foot/]