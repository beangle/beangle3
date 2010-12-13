[#ftl]
[#include "/template/head.ftl"/]
<body>
<table  width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td  class="infoTitle" style="height:22px;" >
       <img src="${base}/static/images/action/info.gif" align="top"/><B>
          <B>操作提示</B>
      </td>
    </tr>
    <tr>
      <td colspan="4" style="font-size:0px" >
          <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
   <tr>
     <td id="errorTD">[@s.actionmessage/] [@s.actionerror/]</td>
   </tr>
  </table>
<body>
<script>
    if(!self.name){
		setTimeout("window.close()",2000);
	}
</script>
[#include "/template/foot.ftl"/]
