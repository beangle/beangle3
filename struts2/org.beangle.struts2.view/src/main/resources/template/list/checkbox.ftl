[#ftl]
<li>[#if tag.label??]<label class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}:</label>[/#if]
<input type="checkbox" id="${tag.id}" style="width:10px" name="${tag.name}" value="${tag.value}"${tag.parameterString} [#if tag.checked]checked="checked"[/#if]/>
<label for="${tag.id}">${tag.title!}</label>
<span id="${tag.id}_span" style="display:none"></span>
[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
</li>
