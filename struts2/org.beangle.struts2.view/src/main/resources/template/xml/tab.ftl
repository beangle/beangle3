[#ftl/]
[#if tag.href??]
<div id="${tag.target}" class="_ajax_target"></div><script>bg.Go('${tag.href}','${tag.target}')</script>
[#else]
<div id="${tag.target}">${tag.body}</div>
[/#if]