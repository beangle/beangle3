[#ftl/]
[#if request.getHeader('USER-AGENT')?contains('MSIE')]
<iframe src="${tag.src}"${tag.parameterString}>${tag.body}</iframe>
[#else]
<object type="text/html" data="${tag.src}"${tag.parameterString}>[#nested/]</object>
[/#if]
