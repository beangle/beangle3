[#ftl]
[@b.xhtmlhead/]
<body>
[@b.toolbar id="menubar" title='<a href="dashboard.action">权限管理</a>-->菜单资源']
	function redirectTo(url){window.open(url);}
	bar.addItem("系统功能","redirectTo('resource.action')");
	bar.addItem("菜单配置","redirectTo('menu-profile.action?method=search')");
	bar.addItem("数据限制","redirectTo('restrict-meta.action')");
	bar.addHelp();
[/@]
<table  class="frameTable">
	<tr>
		<td style="width:160px"  class="frameTable_view">[#include "searchForm.ftl"/]</td>
		<td valign="top">
		<iframe  src="menu!search.action?menu.profile.id=${(profiles?first.id)!}" id="contentFrame" name="contentFrame"
			marginwidth="0" marginheight="0"
			scrolling="no" frameborder="0"  height="100%" width="100%">
		</iframe>
		</td>
	</tr>
</table>
</body>
[#include "/template/foot.ftl"/]
