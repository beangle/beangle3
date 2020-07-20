[#ftl]
<div class="search-item" >[#if tag.label??]<label style="font-weight:inherit">${tag.label}:</label>[/#if]
<input type="checkbox" id="${tag.id}" style="width:10px" name="${tag.name}" value="${tag.value}"${tag.parameterString} [#if tag.checked]checked="checked"[/#if]/>
<label for="${tag.id}">${tag.title!}</label>
</div>
