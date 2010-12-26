[#ftl]
[#macro sortTd id extra...]
  <td [#list extra?keys as attr][#if attr!='name' && attr!='text']${attr}="${extra[attr]?html}"[/#if][/#list] id="${id}" class="gridhead-sort">[#if extra['name']??][@msg.text name="${extra['name']}"/][#else]${extra['text']}[/#if]</td>
[/#macro]

[#macro td extra...]
  <td [#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list]>[#if extra['name']??][@msg.text name="${extra['name']}"/][#else]${extra['text']}[/#if]</td>
[/#macro]

[#macro  tr class]
  <tr class="${class}" align="center" onmouseover="bg.ui.grid.swapOverTR(this,this.className)"onmouseout="bg.ui.grid.swapOutTR(this)" onclick="bg.ui.grid.onRowChange(event)">[#nested]</tr>
[/#macro]

[#macro gridhead extra...]
${gridHead}[#assign gridstart=true/]
<tr class="gridhead" [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]>[#nested]</tr>
[/#macro]

[#macro selectAllTd name extra...]
	<td class="select" [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]><input type="checkBox" id="${name}Box" class="box" onClick="bg.input.toggleCheckBox(document.getElementsByName('${name}'),event);"></td>
[/#macro]

[#macro selectTd name value extra...]
	<td class="select"><input class="box" name="${name}" value="${value}" [#if (extra?size!=0)][#list extra?keys as attr][#if attr != "type"] ${attr}="${extra[attr]}"[/#if][/#list][/#if] type="${extra['type']!("checkbox")}">[#nested]</td>
[/#macro]

[#macro grid extra...]
	[#if extra['id']??]
		[#assign pageId="${extra['id']}"]
	[/#if]
	[#if !(cssClass??)][#assign cssClass="grid"/][/#if]
	[#assign gridstart=false/]
	[#assign gridHead]<table class=${cssClass} [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list][/#if]>[/#assign]
	[#if (extra['target']!'')?length>0][#assign ajaxTarget=extra['target'] /][/#if]
	[#assign gridbody_content][#nested][/#assign]
	[#if !gridstart]${gridHead}[/#if]${gridbody_content}
	[#if curPage?? && pageId??]
	[@pagebar pageId=pageId curPage=curPage sortable=extra['sortable']?? headIndex=extra['headIndex']!("0") scheme=extra['pageBarScheme']!"xhtml" fixPageSize=extra['fixPageSize']!"0" target=extra['target']!"" cssClass="pagebar"/]
	[/#if]
	[#assign ajaxTarget=""/]
	</table>
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

[#-- pageId curPage sortable headIndex  scheme fixPageSize--]
[#macro pagebar pageId curPage extra...]
	<script>
	page=bg.page("${pageId}","${requestURI}","${extra['target']!""}");
	[#if pagechecker.isPage(curPage)]page.maxPageNo=${curPage.maxPageNo};[/#if]
	params=page.params();
	[#list Parameters?keys as key]
	[#if Parameters[key]?? && (Parameters[key]?length>0)]
	params["${key}"]="${Parameters[key]?js_string}";
	[/#if]
	[/#list]
	[#if extra['sortable']!false]
	bg.ui.grid.init('${pageId}',${extra['headIndex']!(0)},"${Parameters['orderBy']!('null')}");
	[/#if]
	</script>
	[#if extra['fixPageSize']?? && (extra['fixPageSize']=='1' || extra['fixPageSize']=='true')]
		[#assign fixPageSize=true]
	[#else]
		[#assign fixPageSize=false]
	[/#if]
	[#if pagechecker.isPage(curPage)]
		[#local pageBaseTemplate]/template/${extra['scheme']!"xhtml"}/pageBar.ftl[/#local]
		[#include pageBaseTemplate/]
	[/#if]
[/#macro]

[#macro queryStr]
[#list Parameters?keys as key][#if (Parameters[key]?length>0)&&key!="method"]&${key}=${Parameters[key]?js_string}[/#if][/#list][#rt/]
[/#macro]

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

[#macro actionbar id name="" title="" entity="" action="" extra...]
	<table id="${id}" width="100%"></table>
	[#if entity?length>0][#local formname=id+"_form"/][/#if]
	[#if formname??]
	<div style="font-size:0pt;border-collapse: collapse">
	<form name="${formname}" id="${formname}" method="post" [#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list] ></form>
	</div>
	[/#if]
	<script>
	[#if formname??]
	action=new bg.entityaction('${formname}','${entity}','${action}','[@queryStr/]',[#if !(extra['target']??)&&(ajaxTarget!'')?length>0]"${ajaxTarget}"[#else]null[/#if]);
	[/#if]
	[#if name?length>0][#local bartitle][@text name/][/#local][#else][#local bartitle=title /][/#if]
	bar = bg.ui.toolbar("${id}",'${bartitle!}',null,true,true);
	bar.setMessage('[@messages/]');
	[#nested]
	</script>
[/#macro]
