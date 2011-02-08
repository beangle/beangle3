[#ftl]
[@b.head/]
[@b.toolbar id="groupbar"]
	bar.setTitle('[@b.a href='index']权限管理[/@]-->用户组管理');
	bar.addHelp("${b.text("action.help")}");
[/@]
<table  class="indexpanel">
	<tr>
		<td style="width:160px"  class="index_view">
		[@b.qform name="groupSearchForm"  action="!search" target="grouplist" title="ui.searchForm"]
			[@b.qfields names="userGroup.name;common.name,userGroup.owner.name;common.creator"/]
			[@b.qselect items=categories empty="请选择身份" name="userGroup.category.id" title="entity.userCategory"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="grouplist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]