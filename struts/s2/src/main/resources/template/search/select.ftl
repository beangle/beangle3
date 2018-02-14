[#ftl/]
[#if tag.option??][#assign optionTemplate=tag.option?interpret][/#if]
<tr><td class="search-item"><label for="${tag.id}">${tag.label}:</label>
[#assign selected=false/]
<select id="${tag.id}" name="${tag.name}"${tag.parameterString}>
${tag.body}
[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#rt/]
[#list tag.items as item][#assign optionText][#if tag.option??][@optionTemplate/][#else]${item[tag.valueName]!}[/#if][/#assign]
<option value="${item[tag.keyName]}"  title="${optionText!}"[#if !selected && tag.isSelected(item)] selected="selected" [#assign selected=true/][/#if]>${optionText!}</option>
[/#list]
[#if tag.value?? && !selected]<option value="${tag.value}" selected="selected">${tag.value}</option>[/#if]
</select></td></tr>
