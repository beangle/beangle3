[#ftl]
[@b.head/]

[@b.toolbar id="menubar"]
	bar.setTitle('[@b.a href="dashboard"]权限管理[/@]-->菜单资源');
	function redirectTo(url){window.open(url);}
	bar.addItem("系统功能","redirectTo('${b.url('resource')}')");
	bar.addItem("菜单配置","redirectTo('${b.url('menu-profile!search')}')");
	bar.addItem("数据限制","redirectTo('${b.url('restrict-meta')}')");
	bar.addHelp();
[/@]
<table  class="indexpanel">
	<tr>
		<td style="width:160px"  class="index_view">[#include "searchForm.ftl"/]</td>
		<td class="index_content">
		<iframe  src="${b.url('!search')}?menu.profile.id=${(profiles?first.id)!}" id="contentFrame" name="contentFrame"
			marginwidth="0" marginheight="0"
			scrolling="no" frameborder="0"  height="100%" width="100%">
		</iframe>
		</td>
	</tr>
</table>

[@b.foot/]
