[#ftl]
[#macro i18nNameTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.name!?trim==""]${entity.title!}[#else]${entity.name!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.name!}[/#if][/#if][/#macro]

[#assign displayed={} /]
[#macro displayMenu menu]
[#if !(displayed[menu.id?string]??)][#assign displayed=displayed+{menu.id?string:menu}/][#else][#return/][/#if]
<li>
[#if menu.entry??]
[@b.a href="${(menu.entry)!}" target="main"][@i18nNameTitle menu/][/@]
[#else]
	[@b.a href="/security/menu-nav?menu.id=${menu.id}" target="main"][@i18nNameTitle menu/][/@]
	<ul style="padding-left: 20px;">
	[#list menu.children as child]
		[#if menus?seq_contains(child)][@displayMenu child/][/#if]
	[/#list]
	</ul>
[/#if]
</li>
[/#macro]

<div id="menu_panel">
[#if menus?size>0]
<ul style="padding-left: 20px;">
[#list menus as menu][@displayMenu menu/][/#list]
</ul>
[#else]
without any menu!
[/#if]
</div>