[#ftl]
[#if tag.label??]<label for="${tag.id}">${tag.label}:</label>[/#if]
[#list tag.checkboxes as checkbox]
<input type="checkbox" id="${checkbox.id}" style="width:10px" name="${tag.name}" value="${checkbox.value}"${tag.parameterString} [#if checkbox.checked]checked="checked"[/#if]/>
<label for="${checkbox.id}">${checkbox.title!}</label>
[/#list]
