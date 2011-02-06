[#ftl/]
<a href="${b.url(tag.href)}" [#if tag.target??]onclick="alert(1)"[/#if] ${tag.parameterString}>${tag.body}</a>