[#ftl]
[#assign moreThan2=(tag.checkboxes?size)>2/]
<tr><td class="search-item" [#if moreThan2]style="text-align:left"[/#if]>[#if tag.label??]<label for="${tag.id}">${tag.label}:</label>[#if moreThan2]<br/>[/#if][/#if]
[#list tag.checkboxes as checkbox]
[#if moreThan2]&nbsp;&nbsp;&nbsp;&nbsp;[/#if]
<input type="checkbox" id="${checkbox.id}" style="width:10px" name="${tag.name}" value="${checkbox.value}"${tag.parameterString} [#if checkbox.checked]checked="checked"[/#if]/>
<label for="${checkbox.id}">${checkbox.title!}</label>[#if moreThan2]<br/>[/#if]
[/#list]
</td></tr>
