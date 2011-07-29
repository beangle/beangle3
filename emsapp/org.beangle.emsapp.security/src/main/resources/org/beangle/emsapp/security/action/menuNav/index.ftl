[#ftl]
[@b.head/]
<link href="${base}/static/themes/${b.theme.ui}/menu.css" rel="stylesheet" type="text/css" />
[#include "menuMacros.ftl"/]

<div id="menu_area">
	<div style="height:25px">
		<div style="float:left">页面导航:[@b.a href="!index?name=${(Parameters['name']!)?url('utf-8')}"]全部[/@][#list menuPath as m] >> [@displayMenuAnchor m]${m.title}[/@][/#list]</div>
		<div style="float:right">
		[@b.form  name="menusearchform" action="!index"]
		<input type="hidden" name="menu.id" value="${Parameters['menu.id']!?html}"/>
		[@b.textfield name="name" value=Parameters['name']/][@b.submit value="搜索"/][/@]
		</div>
		<div><img width="100%" height="1" align="top" src="${b.theme.iconurl("actions/keyline.png")}"></div>
	</div>
	[#assign alonemenus=[]]
	[#list tops as topmenu]
	[#if topmenu.children?size=0]
		[#assign parentMenu=topmenu.parent/]
		[#assign alonemenus=alonemenus+[topmenu]/]
	[#else]
		[@b.module title=topmenu.title style="width:30%"]
			<ul>
			[#if topmenu.children?size!=0][@displayMenu topmenu/][/#if]
			</ul>
		[/@]
	[/#if]
	[/#list]
	[#if parentMenu??]
	[@b.module title=parentMenu.title style="width:30%"]
		<ul>
		[@displayMenus alonemenus/]
		</ul>
	[/@]
	[/#if]
</div>
[@b.foot/]