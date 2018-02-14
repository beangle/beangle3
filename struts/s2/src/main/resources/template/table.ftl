[#ftl]
[#macro sortTd id extra...]
  <td [#list extra?keys as attr][#if attr!='name' && attr!='text']${attr}="${extra[attr]?html}"[/#if][/#list] id="${id}" class="tableHeaderSort">[#if extra['name']??][@msg.text name="${extra['name']}"/][#else]${extra['text']}[/#if]</td>
[/#macro]
[#macro td extra...]
  <td [#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list]>[#if extra['name']??][@msg.text name="${extra['name']}"/][#else]${extra['text']}[/#if]</td>
[/#macro]
[#macro thead extra...]
  <tr align="center" class="darkColumn" [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}"[/#list][/#if]>[#nested]</tr>
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
  [#if !(cssClass??)][#assign cssClass="listTable"/][/#if]
  <table class=${cssClass} [#if (extra?size!=0)][#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list][/#if]>
  [#nested]
  [#if curPage?? && pageId??]
  [@page.bar pageId=pageId curPage=curPage sortable=extra['sortable']?? headIndex=extra['headIndex']!("0") scheme=extra['pageBarScheme']!"xhtml" fixPageSize=extra['fixPageSize']!"0" target=extra['target']!"" cssClass="darkColumn"/]
  [/#if]
  </table>
[/#macro]

[#macro tbody datas extra...]
  <tbody>
  [#list datas as data]
    [#if data_index%2==1][#assign class="grayStyle"][/#if]
    [#if data_index%2==0][#assign class="brightStyle"][/#if]
    [@tr class="${class}"][#nested data,data_index][/@tr]
  [/#list]
  [#assign curPage=datas]
  </tbody>
[/#macro]
