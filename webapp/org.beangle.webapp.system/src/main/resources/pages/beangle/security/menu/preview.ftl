[#ftl]
[@b.head/]

[@b.toolbar title="菜单列表"]
	bar.addPrint("${b.text("action.print")}");
	bar.addClose("${b.text("action.close")}");
[/@]
<table class="grid" align="center" width="90%">
	<tr class="thead">
	 <td colspan="${depth}">模块标题</td>
	 <td>英文名</td>
	 <td>模块描述</td>
	</tr>
	[#list menus as menu]
	<tr>[#if (((menu.code?length)/2)>1)][#list 1..((menu.code?length)/2)-1 as i]<td>&nbsp;</td>[/#list][/#if]
	<td>${menu.title}</td>
	[#if (depth-((menu.code?length)/2)>0)]
	[#list 1..(depth-((menu.code?length)/2)) as i]<td></td>[/#list]
	[/#if]
	<td>${menu.engTitle!("")}</td>
	<td>${menu.description!("")}</td>
	</tr>
	[/#list]
</table>
 
[@b.foot/]
