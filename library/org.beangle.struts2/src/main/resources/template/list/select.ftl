[#ftl]
[#if tag.option??][#assign optionTemplate=tag.option?interpret][/#if]
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}:</label>[/#if]
<select id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}"${tag.parameterString}>
[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#rt/]
[#list tag.items as item][#assign optionText][#if tag.option??][@optionTemplate/][#else]${item[tag.valueName]!}[/#if][/#assign]
<option value="${item[tag.keyName]}" title="${optionText!}"[#if tag.isSelected(item)]selected="selected"[/#if]>${optionText!}</option>
[/#list]
</select>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
</li>