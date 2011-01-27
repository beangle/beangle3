[#ftl]
[@b.head/]

[@b.toolbar id="groupbar"]
	bar.setTitle('[@b.a href='dashboard']权限管理[/@]-->用户组管理');
	bar.addHelp("${b.text("action.help")}");
[/@]
<table  class="frameTable">
	<tr>
		<td style="width:160px"  class="frameTable_view">[#include "searchForm.ftl"/]</td>
		<td valign="top">
		<iframe  src="#" id="contentFrame" name="contentFrame"
			marginwidth="0" marginheight="0"
			scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
		</td>
	</tr>
</table>
<script type="text/javascript">
	function search(){
		document.getElementById('groupSearchForm').submit();
	}
	search();
</script>

[@b.foot/]