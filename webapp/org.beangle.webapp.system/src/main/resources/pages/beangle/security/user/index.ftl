[#ftl]
[@b.xhtmlhead/]

[@b.toolbar id="userbar" title="<a href='dashboard.action'>权限管理</a>-->用户管理"]
	bar.addHelp("[@b.text name="action.help"/]");
[/@]
<table class="frameTable">
<tr>
	<td style="width:180px"  class="frameTable_view">[#include "searchForm.ftl"/]</td>
	<td valign="top">
	[@b.iframe src="user!search.action?user.status=1" id="contentFrame" name="contentFrame" width="100%" scrolling="no"]list[/@]
	</td>
</tr>
</table>

</html>
