[#ftl/]
<div id="${tag.id}"></div>
<script type="text/javascript">
bar = bg.ui.toolbar("${tag.id}",'${(tag.parameters['title']?default(''))?replace("'","\"")}');
bar.setMessage('[@b.messages/]');
${tag.body}bar.addHr();
</script>