[#ftl]
<table id="backBar" width="100%"></table>
<script>
   var bar = bg.ui.toolbar('backBar','${labInfo}',null,true,true);
   bar.setMessage('[@msg.messages/]');
   bar.addBack("[@msg.text "action.back"/]");
</script>
