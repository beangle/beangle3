[#ftl/]
[#if tag.href??]
<div id="${tag.target}" class="ajax_container"></div>
<script>bg.ready(function(){bg.Go('${tag.href}','${tag.target}');});</script>
[#else]
<div id="${tag.target}">${tag.body}</div>
[/#if]
