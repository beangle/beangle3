[#ftl/]
[#if request.getHeader('USER-AGENT')?contains('MSIE')]
<iframe src="${tag.src}" [#list parameters?keys as attr]${attr}="${parameters[attr]?html}" [/#list]>${nested_body!}</iframe>
[#else]
<object type="text/html" data="${tag.src}" [#list parameters?keys as attr]${attr}="${parameters[attr]?html}" [/#list]>[#nested/]</object>
[/#if]
