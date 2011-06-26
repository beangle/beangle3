[#ftl]
[@b.head/]
<style type="text/css">
img {border:0 none;vertical-align:middle}
.module{margin:0px 3px 5px;line-height:1.3em;float:left;width:30%;}
#menu_area ul {padding-left:10px}
#menu_area  .menu  {
	list-style-type:none; 
	background:url('${b.theme.iconurl('places/folder.png')}') no-repeat left;
	padding-left:20px
}

#menu_area .resource  {
	list-style-type:none; 
	background:url('${b.theme.iconurl('tree/sanjiao.png')}') no-repeat left;
	padding-left:20px
}
</style>

[#macro displayMenuAnchor menu]
[#if Parameters['name']??]
[@b.a href="!index?menu.id=${menu.id}&name=${(Parameters['name']!)?url('utf-8')}" title=menu.remark!""][#nested/][/@]
[#else]
[@b.a href="!index?menu.id=${menu.id}" title=menu.remark!""][#nested/][/@]
[/#if]
[/#macro]

[#macro displayMenu(mymenu)]
	[#local i=0]
	[#list mymenu.children as sub]
	[#local moreitem=false/]
	[#if menus?contains(sub)]
		[#if i=maxItem]
		<div style="text-align:right">[#local moreitem=true/][@displayMenuAnchor mymenu]更多项(${mymenu.children?size-maxItem})...[/@]</div>
		[#local i=i+1][#break/]
		[#else]
		<li [#if sub.children?size>0]class="menu"[#else]class="resource"[/#if]>[#if sub.children?size>0][@displayMenuAnchor sub]${sub.title}[/@][#else][@b.a href=sub.entry! title=sub.remark!""]${sub.title}[/@][/#if]</li>
		[#local i=i+1]
		[/#if]
	[/#if]
	[/#list]
	[#if !moreitem][#local i=i+1][/#if]
	[#if i<=maxItem]
		[#list i..maxItem as b]<br>[/#list]
	[/#if]

	[#if !moreitem]
	<div style="text-align:right">[#if mymenu.children?size>0][@displayMenuAnchor mymenu]打开[/@][#else][@b.a href=mymenu.entry!]打开[/@][/#if]</div>
	[/#if]
[/#macro]

[#macro displayResource(mymenu)]
	[#local i=0]
	[#local moreitem=false/]
	[#list mymenu.resources as resource]
	[@bs.guard res=resource.name]
		[#if i=maxItem]
		[#local moreitem=true/]
		[#local i=i+1][#break/]
		[#else]
		<li class="resource">[#if resource.needParams]${resource.title}[#else][@b.a href=resource.name! title=resource.remark!""]${resource.title}[/@][/#if]</li>
		[#local i=i+1]
		[/#if]
	[/@]
	[/#list]
	[#local i=i+1]
	[#if i<=maxItem]
		[#list i..maxItem as b]<br>[/#list]
	[/#if]
	<div style="text-align:right">[@b.a href=mymenu.entry! title=mymenu.remark!""]进入[#if moreitem](更多项(${mymenu.resources?size-maxItem})...)[/#if][/@]</div>
[/#macro]

<div id="menu_area">
[#assign maxItem=5]
[@b.toolbar title="页面导航"/]
<div style="height:25px;width:90%">
<div style="float:left">[@b.a href="!index?name=${(Parameters['name']!)?url('utf-8')}"]全部[/@][#list menuPath as m] >> [@displayMenuAnchor m]${m.title}[/@][/#list]</div>
<div style="float:right">
[@b.form  name="menusearchform" action="!index"]
<input type="hidden" name="menu.id" value="${Parameters['menu.id']!?html}"/>
[@b.textfield name="name" value=Parameters['name']/][@b.submit value="搜索"/][/@]
</div>
</div>

[#list tops as topmenu]
[@b.module title=topmenu.title]
	<ul>
	[#if topmenu.children?size!=0][@displayMenu topmenu/][#else][@displayResource topmenu/][/#if]
	</ul>
[/@]
[/#list]
</div>
[@b.foot/]