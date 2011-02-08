[#ftl/]
[#if request.getHeader('USER-AGENT')?contains('MSIE')]
<iframe [#if tag.id??]id="${tag.id}"[/#if] frameborder="0" marginwidth="0" marginheight="0" height="100%" width="100%" src="${tag.src}"${tag.parameterString}>${tag.body}</iframe>
[#else]
<iframe [#if tag.id??]id="${tag.id}"[/#if] frameborder="0" marginwidth="0" marginheight="0" height="100%" width="100%" src="${tag.src}"${tag.parameterString}>${tag.body}</iframe>
[#--<object type="text/html" [#if tag.id??]id="${tag.id}"[/#if] data="${tag.src}"${tag.parameterString}>${tag.body}</object>--]
[/#if]
