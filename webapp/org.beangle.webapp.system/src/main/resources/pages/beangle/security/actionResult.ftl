[#ftl]
[@b.xhtmlhead/]

<table  width="100%" >
	<tr>
	  <td  class="infoTitle" style="height:22px;" >
	   <img src="${base}/static/images/action/info.gif" align="top"/><em>
		  <em>操作提示</em>
	  </td>
	</tr>
	<tr>
	  <td colspan="4" style="font-size:0px" >
		  <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top"/>
	  </td>
   </tr>
   <tr>
	 <td id="errorTD"><font color="green">
	  [#if ctrlMsg??]
		 [@b.text name="${ctrlMsg}"/]&nbsp;
	  [/#if]
	  </font>
	  </td>
   </tr>
  </table>

<script type="text/javascript">
	if(!self.name){
		window.resizeTo(300,200);
		window.moveTo(300,200);
		setTimeout("window.close()",2000);
	}
</script>
[#include "/template/foot.ftl"/]
