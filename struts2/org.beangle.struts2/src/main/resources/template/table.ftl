[#ftl]
[#macro sortTd id extra...]
  <td [#list extra?keys as attr][#if attr!='name' && attr!='text']${attr}="${extra[attr]?html}"[/#if][/#list] id="${id}" class="gridhead-sort">[#if extra['name']??][@msg.text name="${extra['name']}"/][#else]${extra['text']}[/#if]</td>
[/#macro]
[#macro td extra...]
  <td [#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list]>[#if extra['name']??][@msg.text name="${extra['name']}"/][#else]${extra['text']}[/#if]</td>
[/#macro]
[#macro thead extra...]
  <tr class="gridhead" [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]>[#nested]</tr>
[/#macro]
[#macro  tr class]
  <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)"onmouseout="swapOutTR(this)" onclick="onRowChange(event)">[#nested]</tr>
[/#macro]
[#macro selectAllTd id extra...]
  <td class="select" [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]><input type="checkBox" id="${id}Box" class="box" onClick="toggleCheckBox(document.getElementsByName('${id}'),event);"></td>
[/#macro]
[#macro selectTd id value extra...]
  <td class="select"><input class="box" name="${id}" value="${value}" [#if (extra?size!=0)][#list extra?keys as attr][#if attr != "type"] ${attr}="${extra[attr]}"[/#if][/#list][/#if] type="${extra['type']!("checkbox")}">[#nested]</td>
[/#macro]
 
[#macro table extra...]
	[#if extra['id']??]
		[#assign pageId="${extra['id']}"]
	[/#if]
	[#if !(cssClass??)][#assign cssClass="grid"/][/#if]
	<table class=${cssClass} [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list][/#if]>
	[#nested]
	[#if curPage?? && pageId??]
	[@page.bar pageId=pageId curPage=curPage sortable=extra['sortable']?? headIndex=extra['headIndex']!("0") scheme=extra['pageBarScheme']!"xhtml" fixPageSize=extra['fixPageSize']!"0" target=extra['target']!"" cssClass="pagebar"/]
	[/#if]
	</table>
[/#macro]

[#macro tbody datas extra...]
	[#if datas?size!=0]
	<tbody>
	[#list datas as data]
		[#if data_index%2==1][#assign class="gray"][/#if]
		[#if data_index%2==0][#assign class="bright"][/#if]
		[@tr class="${class}"][#nested data,data_index][/@tr]
	[/#list]
	[#assign curPage=datas]
	</tbody>
	[/#if]
[/#macro]
