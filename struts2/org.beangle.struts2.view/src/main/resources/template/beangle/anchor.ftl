[#ftl/]
<a href="${b.url(tag.href)}" [#if tag.target??]target="${tag.target}"[/#if] [#if tag.onclick??]onclick="${tag.onclick}"[/#if] ${tag.parameterString}>${tag.body}</a>