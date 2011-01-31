[#ftl/]
<a href="${b.url(tag.href)}" [#list parameters?keys as k] ${k}="${parameters[k]}"[/#list]>${nested_body!}</a>