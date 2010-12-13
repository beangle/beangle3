[#ftl]
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','${labInfo}',null,true,true);
   bar.setMessage('[@msg.messages/]');
   bar.addBack("[@msg.text "action.back"/]");
</script>
