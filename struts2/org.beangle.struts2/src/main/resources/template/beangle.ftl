[#ftl]
[#macro messages][@s.actionmessage theme="beangle"/][@s.actionerror theme="beangle"/][/#macro]

[#macro i18name(entity)]
[#if locale.language?index_of("en")!=-1][#if entity.engName!?trim==""]${entity.name!}[#else]${entity.engName!}[/#if][#else][#if entity.name!?trim!=""]${entity.name!}[#else]${entity.engName!}[/#if][/#if]
[/#macro]

[#macro gridhead extra...]
[#if !gridstart]${gridHead}[/#if][#assign gridstart=true/]
[#assign head_content][#nested][/#assign]
[#assign head_content=head_content?trim]
<thead class="gridhead" [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]>[#if head_content?starts_with('<tr')]${head_content}[#else]<tr>${head_content}</tr>[/#if]</thead>
[/#macro]

[#macro gridth extra...]
<th [#list extra?keys as attr][#if attr=='sort'] id="${extra[attr]?html}" class="gridhead-item-sort" [#assign sortable=true/][#elseif attr!='name' && attr!='text'] ${attr}="${extra[attr]?html}"[/#if][/#list]>[#if extra['name']??]${b.text("${extra['name']}")}[#else]${extra['text']}[/#if]</th>
[/#macro]

[#macro selectAllTh name extra...]
	<th [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]><input type="checkbox" onclick="bg.input.toggleCheckBox(document.getElementsByName('${name}'),event);" title="select all"/></th>
[/#macro]

[#macro selectTd name value extra...]
	<td class="gridselect"><input class="box" name="${name}" value="${value}" [#if (extra?size!=0)][#list extra?keys as attr][#if attr != "type"] ${attr}="${extra[attr]}"[/#if][/#list][/#if] type="${extra['type']!("checkbox")}" title="select me"/>[#nested]</td>
[/#macro]

[#macro grid extra...]
	[#if extra['id']??][#assign pageId="${extra['id']}"][/#if]
	[#if !(cssClass??)][#assign cssClass="grid"/][/#if]
	
	[#assign gridstart=false/] [#assign sortable=false/]
	[#assign gridHead]<table class="${cssClass}" [#if (extra?size!=0)][#list extra?keys as attr][#if attr!='target'&& attr!='fixPageSize']${attr}="${extra[attr]?html}" [/#if][/#list][/#if]>[/#assign]
	[#if (extra['target']!'')?length>0][#assign ajaxTarget=extra['target'] /][/#if]
	[#assign grid_content][#nested][/#assign]
	
	[#if !gridstart]${gridHead}[/#if]
	${grid_content}
	</table>
	[#if sortable]
	<script type="text/javascript">bg.ui.grid.init('${pageId}',"${Parameters['orderBy']!('null')}");</script>
	[/#if]
	
	[#if pagechecker.isPage(curPage) || sortable]
	[@pagebar pageId=pageId curPage=curPage scheme=extra['pageBarScheme']!"xhtml" fixPageSize=extra['fixPageSize']!"0" target=extra['target']!"" cssClass="pagebar"/]
	[/#if]
	
	[#assign ajaxTarget=""/] [#assign sortable=false/]
[/#macro]

[#-- pageId curPage scheme fixPageSize--]
[#macro pagebar pageId curPage extra...]
[#if !pagechecker.isPage(curPage)][#return/][/#if]
<script type="text/javascript">
	page=bg.page("${pageId}","${requestURI}","${extra['target']!""}");
	[#if pagechecker.isPage(curPage)]page.maxPageNo=${curPage.maxPageNo};[/#if]
	page.addParams('[@paramStr/]');
</script>
[#if extra['fixPageSize']?? && (extra['fixPageSize']=='1' || extra['fixPageSize']=='true')][#assign fixPageSize=true][#else][#assign fixPageSize=false][/#if]
[#local pageBaseTemplate]/template/${extra['scheme']!"xhtml"}/pageBar.ftl[/#local]
[#include pageBaseTemplate/]
[/#macro]


[#macro entitybar id name="" title="" entity="" action="" extra...]
<div id="${id}"></div>
<script type="text/javascript">
[#if name?length>0][#local bartitle][@text name/][/#local][#else][#local bartitle=title /][/#if]
bar = bg.ui.toolbar('${id}','${bartitle?replace("'","\"")}');
bar.setMessage('[@messages/]');
[#if  entity?length>0]
action=new bg.entityaction('${id}','${entity}','${url(action)}','[@paramStr/]',[#if !(extra['target']??)&&(ajaxTarget!'')?length>0]"${ajaxTarget}"[#else]null[/#if]);
[/#if]
[#nested]
</script>
[/#macro]

[#--[#assign content_type]application/xhtml+xml[/#assign]--]
[#assign content_type]text/html[/#assign]

[#macro redirectParams]<input name="params" type="hidden" value="${(Parameters['params']!(''))?html}" />[/#macro]