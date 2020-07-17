[#ftl]
<button type="submit" class="${tag.cssClass!'btn btn-outline-primary btn-sm'}" onclick="bg.form.submit('${tag.formId}',[#if tag.action??]'${tag.action}'[#else]null[/#if],[#if tag.target??]'${tag.target}'[#else]null[/#if],[#if tag.onsubmit??]${tag.onsubmit}[#else]null[/#if]);return false;"${tag.parameterString}>
<i class="fa fa-search fa-sm"></i> ${tag.value!'Submit'}
</button>
