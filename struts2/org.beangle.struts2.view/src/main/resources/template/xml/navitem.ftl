[#ftl]
<li><a href="${tag.href}" [#if tag.selected]class="selected"[/#if] [#if tag.onclick??]onclick="${tag.onclick}"[/#if] ${tag.parameterString}>${tag.title!}</a></li>