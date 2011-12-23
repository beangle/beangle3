[#ftl/]
<a href="${tag.href}" [#if tag.id??]id="${tag.id}"[/#if] [#if tag.target??]target="${tag.target}"[/#if] [#if tag.onclick??]onclick="${tag.onclick}"[/#if] ${tag.parameterString}>${tag.body}</a>