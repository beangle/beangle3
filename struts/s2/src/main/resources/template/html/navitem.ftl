[#ftl]
<li [#if tag.selected]class="active"[/#if]><a href="${tag.href}" [#if tag.onclick??]onclick="${tag.onclick}"[/#if] ${tag.parameterString}>${tag.title!}</a></li>
