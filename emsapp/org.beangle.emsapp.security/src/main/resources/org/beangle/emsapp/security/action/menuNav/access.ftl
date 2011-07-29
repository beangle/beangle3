[#ftl]
[@b.head/]
<link href="${base}/static/themes/${b.theme.ui}/menu.css" rel="stylesheet" type="text/css" />
[#include "menuMacros.ftl"/]

<div id="menu_area">
	<div>
		<div style="float:left;height:25px;vertical-align:middle;">页面导航:[@b.a href="!index?name=${(Parameters['name']!)?url('utf-8')}"]全部[/@][#list paths as m] >> [#if m_index<paths?size-2][@displayMenuAnchor m]${m.title}[/@][#else]${m.title}[/#if][/#list]</div>
		<div style="float:right">
			[@b.form  name="menusearchform" action="!index"]
			<input type="hidden" name="menu.id" value="${Parameters['menu.id']!?html}"/>
			[@b.textfield name="name" value=Parameters['name']/][@b.submit value="搜索"/][/@]
		</div>
		<div><img width="100%" height="1" align="top" src="${b.theme.iconurl("actions/keyline.png")}"></div>
	</div>

	[@b.div href=menu.entry style="float:left;width:85%"/]
	<div style="float:left;width:14%">
	[@b.module title=menu.parent.title style="width:200px;"]
		<ul>
		[#list (menu.parent.children)! as m]
		<li class="resource[#if m==menu] current[/#if]">[@displayCommandAnchor m]${m.title}[/@]</li>
		[/#list]
		</ul>
	[/@]
	</div>
</div>
[@b.foot/]