[#ftl/]
<div id="${tag.id}"></div>
<script type="text/javascript">
bar = bg.ui.toolbar("${tag.id}",'${(tag.parameters['title']?default(''))?replace("'","\"")}');
bar.setMessage('[@s.actionmessage theme="beangle"/][@s.actionerror theme="beangle"/]');
${tag.body}
bar.addHr();
</script>