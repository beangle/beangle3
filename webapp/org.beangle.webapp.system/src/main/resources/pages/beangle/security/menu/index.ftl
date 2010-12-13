[#ftl]
[#include "/template/head.ftl"/]
<body> 
<table id="menuBar"></table>
<table  class="frameTable">
	<tr>
		<td style="width:160px"  class="frameTable_view">[#include "searchForm.ftl"/]</td>
		<td valign="top">
		<iframe  src="#" id="contentFrame" name="contentFrame" 
			marginwidth="0" marginheight="0"
			scrolling="no" frameborder="0"  height="100%" width="100%">
		</iframe>
		</td>
	</tr>
</table>

<script>
	var form=document.pageGoForm;
	var action="menu.action";
	function search(){
		form.action=action+"?method=search";
		form.submit();
	}
	search();
	function redirectTo(url){
		window.open(url);
	}
	var bar = new ToolBar('menuBar','<a href="dashboard.action">权限管理</a>-->菜单资源',null,true,true);
	bar.setMessage('[@getMessage/]');
	bar.addItem("系统功能","redirectTo('resource.action')");
	bar.addItem("菜单配置","redirectTo('menu-profile.action?method=search')");
	bar.addItem("数据限制","redirectTo('restrict-meta.action')");
	bar.addHelp();
</script>
</body>
[#include "/template/foot.ftl"/]
