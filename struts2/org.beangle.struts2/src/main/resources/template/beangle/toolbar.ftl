[#ftl/]
<div id="${tag.id}"></div>
<script type="text/javascript">
bar = bg.ui.toolbar("${tag.id}"[#if tag.title??],'${tag.title?replace("'","\"")}'[/#if]);
bar.setMessage('[@b.messages/]');
${tag.body}
bar.addHr();
</script>