[#ftl]
[#if tag.label??]<label>${tag.label}:</label>[/#if]
<input type="checkbox" id="${tag.id}" style="width:10px" name="${tag.name}" value="${tag.value}"${tag.parameterString} [#if tag.checked]checked="checked"[/#if]/>
<label for="${tag.id}">${tag.title!}</label>
