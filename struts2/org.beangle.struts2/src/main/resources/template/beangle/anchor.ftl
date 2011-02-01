[#ftl/]
<a href="${b.url(tag.href)}" [#list tag.parameters?keys as k] ${k}="${tag.parameters[k]}"[/#list]>${nested_body!}</a>