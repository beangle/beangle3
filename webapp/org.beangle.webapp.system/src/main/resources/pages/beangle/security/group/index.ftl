[#ftl]
[#include "/template/head.ftl"/]
<body>
<table id="groupBar"></table>
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
</body>
<script>
	var form=document.groupSearchForm;
	var action="group!search.action";
	function search(){
		form.action=action;
		form.submit();
	}
	search();
	var bar = bg.ui.toolbar('groupBar','<a href="dashboard.action">权限管理</a>-->用户组管理',null,true,true);
	bar.addHelp("[@text name="action.help"/]");
</script>
</html>
