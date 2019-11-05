[#ftl/]
[#if tag.href??]
<div id="${tag.id}" class="tab-pane"></div>
<script>bg.ready(function(){bg.Go('${tag.href}','${tag.id}');});</script>
[#else]
<div id="${tag.id}" class="tab-pane">${tag.body}</div>
[/#if]
