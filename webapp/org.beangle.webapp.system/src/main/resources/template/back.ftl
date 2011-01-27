[#ftl]
<table id="backBar" width="100%"></table>
<script type="text/javascript">
   var bar = bg.ui.toolbar('backBar','${labInfo}');
   bar.setMessage('[@s.actionmessage theme="beangle"/][@s.actionerror theme="beangle"/]');
   bar.addBack("${b.text("action.back")}");
</script>
