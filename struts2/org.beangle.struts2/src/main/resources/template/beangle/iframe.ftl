[#ftl/]
[#if request.getHeader('USER-AGENT')?contains('MSIE')]
<iframe src="${tag.src}" [#list tag.parameters?keys as attr]${attr}="${tag.parameters[attr]?html}" [/#list]>${nested_body!}</iframe>
[#else]
<object type="text/html" data="${tag.src}" [#list tag.parameters?keys as attr]${attr}="${tag.parameters[attr]?html}" [/#list]>[#nested/]</object>
[/#if]
