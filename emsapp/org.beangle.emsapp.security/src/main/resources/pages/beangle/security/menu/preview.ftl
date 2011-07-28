[#ftl]
[@b.head/]
[@b.toolbar title="菜单列表"]
	function displayXml(){
		jQuery.colorbox({transition : 'none', title : "菜单资源xml", overlayClose : true, width:"1000px", inline:true, href:"#menuxml"});
	}
	bar.addPrint("${b.text("action.print")}");
	bar.addItem("xml",displayXml);
	bar.addClose("${b.text("action.close")}");
[/@]
<div style="display:none">
[@b.div id="menuxml" href="!xml"/]
</div>

<table class="gridtable" align="center" style="width:80%">
	<tr class="thead">
	 <td colspan="${depth}" width="40%">模块标题</td>
	 <td  width="30%">${b.text("common.name")}</td>
	 <td  width="20%">模块描述</td>
	</tr>
	[#list menus as menu]
	<tr>[#if (((menu.code?length)/2)>1)][#list 1..((menu.code?length)/2)-1 as i]<td>&nbsp;</td>[/#list][/#if]
	<td>${menu.title}</td>
	[#if (depth-((menu.code?length)/2)>0)]
	[#list 1..(depth-((menu.code?length)/2)) as i]<td></td>[/#list]
	[/#if]
	<td>${menu.name!("")}</td>
	<td>${menu.remark!("")}</td>
	</tr>
	[/#list]
</table>
[@b.foot/]