[#ftl]
<li class="nav-item"><a href="${tag.href}" class="nav-link [#if tag.selected]active[/#if]" [#if tag.onclick??]onclick="${tag.onclick}"[/#if] ${tag.parameterString}>${tag.title!}</a></li>
