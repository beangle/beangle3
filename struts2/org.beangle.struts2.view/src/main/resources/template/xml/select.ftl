[#ftl/]
[#if tag.option??][#assign optionTemplate=tag.option?interpret][/#if]
<select id="${tag.id}" name="${tag.name}"${tag.parameterString}>
${tag.body}
[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#rt/]
[#list tag.items as item][#assign optionText][#if tag.option??][@optionTemplate/][#else]${item[tag.valueName]!}[/#if][/#assign]
<option value="${item[tag.keyName]}" title="${optionText!}" [#if tag.isSelected(item)]selected="selected"[/#if]>${optionText!}</option>
[/#list]
</select>