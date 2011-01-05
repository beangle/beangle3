[#ftl]
[#macro text name extra...]
	[#if (extra?size=0)]
		[@s.text name="${name}"/]
	[#elseif (extra?size=1)]
		[@s.text name="${name}"]
			[@s.param]${extra['arg0']}[/@s.param]
		[/@]
	[/#if]
[/#macro]

[#macro messages][@s.actionmessage theme="beangle"/][@s.actionerror theme="beangle"/][/#macro]

[#macro i18name(entity)]
[#if locale.language?index_of("en")!=-1][#if entity.engName!?trim==""]${entity.name!}[#else]${entity.engName!}[/#if][#else][#if entity.name!?trim!=""]${entity.name!}[#else]${entity.engName!}[/#if][/#if]
[/#macro]

[#macro paramStr]
[#list Parameters?keys as key][#if (Parameters[key]?length>0)&&key!="method"]&${key}=${Parameters[key]?js_string}[/#if][/#list][#rt/]
[/#macro]

[#macro gridhead extra...]
[#if !gridstart]${gridHead}[/#if][#assign gridstart=true/]
[#assign head_content][#nested][/#assign]
[#assign head_content=head_content?trim]
<thead class="gridhead" [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]>[#if head_content?starts_with('<tr')]${head_content}[#else]<tr>${head_content}</tr>[/#if]</thead>
[/#macro]

[#macro gridth extra...]
<th [#list extra?keys as attr][#if attr=='sort'] id="${extra[attr]?html}" class="gridhead-item-sort" [#assign sortable=true/][#elseif attr!='name' && attr!='text'] ${attr}="${extra[attr]?html}"[/#if][/#list]>[#if extra['name']??][@b.text name="${extra['name']}"/][#else]${extra['text']}[/#if]</th>
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

[#macro gridbody datas extra...]
	[#if datas?size!=0]
	<tbody>
	[#list datas as data]
		[#if data_index%2==1][#assign class="gray"][/#if]
		[#if data_index%2==0][#assign class="bright"][/#if]
		[@tr class="${class}"][#nested data,data_index][/@tr]
	[/#list]
	</tbody>
	[/#if]
	[#assign curPage=datas]
[/#macro]

[#macro  tr class]
<tr class="${class}" align="center" onmouseover="bg.ui.grid.swapOverTR(this,this.className)" onmouseout="bg.ui.grid.swapOutTR(this)" onclick="bg.ui.grid.onRowChange(event)">[#nested]</tr>
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
action=new bg.entityaction('${id}','${entity}','${action}','[@paramStr/]',[#if !(extra['target']??)&&(ajaxTarget!'')?length>0]"${ajaxTarget}"[#else]null[/#if]);
[/#if]
[#nested]
</script>
[/#macro]

[#macro toolbar id title=""]
<div id="${id}"></div>
<script type="text/javascript">
bar = bg.ui.toolbar("${id}",'${title?replace("'","\"")}');
bar.setMessage('[@messages/]');
[#nested/]
</script>
[/#macro]

[#--[#assign content_type]application/xhtml+xml[/#assign]--]
[#assign content_type]text/html[/#assign]

[#macro xhtmlhead title="" name=""]
[#--<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">--]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="${locale.language}" xml:lang="${locale.language}">
<head>
[#if name?length>0][#local htmltitle][@text name/][/#local][#else][#local htmltitle=title /][/#if]
	<title>${htmltitle}[#if htmltitle?length>0] - [/#if]${(systemVersion.name)!} ${(systemVersion.version)!}</title>
	<meta http-equiv="content-type" content="${content_type};charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="content-style-type" content="text/css"/>
	<meta http-equiv="content-script-type" content="text/javascript"/>
	<link href="${base}/static/themes/default/beangle-ui.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/static/scripts/beangle.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle-ui.js?ver=1"></script>
	[#nested/]
</head>
<body>
[/#macro]

[#macro magicParams]<input name="params" type="hidden" value="${(Parameters['params']!(''))?html}" />[/#macro]

[#macro iframe src extra...]
[#local extraAttrs][#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list][/#if][/#local]
[#if request.getHeader('USER-AGENT')?contains('MSIE')]
<iframe src="${src}" ${extraAttrs}>[#nested/]</iframe>
[#else]
[#--<iframe src="${src}" ${extraAttrs} frameborder="0">[#nested/]</iframe>--]
<object type="${content_type}" data="${src}" ${extraAttrs}>[#nested/]</object>
[/#if]
[/#macro]
