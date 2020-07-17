[#ftl]
<button type="submit" class="${tag.cssClass!'btn btn-outline-primary btn-sm'}" onclick="if(bg.form.submit('${tag.formId}',[#if tag.action??]'${tag.action}'[#else]null[/#if],[#if tag.target??]'${tag.target}'[#else]null[/#if],[#if tag.onsubmit??]${tag.onsubmit}[#else]null[/#if])){this.innerHTML='正在提交...',this.disabled=true;};return false;"${tag.parameterString}>
<i class="fa fa-arrow-circle-right fa-sm"></i> ${tag.value!'Submit'}
</button>
