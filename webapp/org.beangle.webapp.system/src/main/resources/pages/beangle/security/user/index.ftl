[#ftl]
[@b.head/]
<div>
[@b.toolbar id="userbar"]
	bar.setTitle('[@b.a href='dashboard']权限管理[/@]-->用户管理');
	bar.addHelp("${b.text("action.help")}");
[/@]
<table class="frameTable">
<tr>
	<td style="width:180px"  class="frameTable_view">[#include "searchForm.ftl"/]</td>
	<td valign="top">
	[@sj.div id="userlist" href="${b.url('user!search?user.status=1')}" /]
	</td>
</tr>
</table>
</div>
[@b.foot/]
