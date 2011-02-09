[#ftl]
<div [#if tag.id??]id="${tag.id}"[/#if]${tag.parameterString}>${tag.body}</div>
[#if tag.href??]<script>bg.Go('${tag.href}','${tag.id}')</script>[/#if]
[#--<script>jQuery(document).ready(function () {jQuery.struts2_jquery.bind(jQuery('#${tag.id}'),{id:'${tag.id}',jqueryaction: "container",href:'${tag.href}'});});</script>--]