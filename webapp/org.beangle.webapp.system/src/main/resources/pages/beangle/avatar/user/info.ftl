[#ftl]
[@b.xhtmlhead/]
<html>
 <body >
<table width="100%">
	<tr>
	  <td class="infoTitle" width="20%" style="height:22px;">
	   <img src="${base}/static/images/action/info.gif" align="top"/><em>
		  <em>照片信息</em>
	  </td>
	  <td class="infoTitle" width="20%" style="height:22px;">
 		<font color="red">&nbsp;[@s.actionmessage/]</font>
	  </td>
	</tr>
	<tr>
	  <td colspan="5" style="font-size:0px">
		  <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top"/>
	  </td>
   </tr>
	<tr>
	  <td>
		  <img src="${base}/avatar/user.action?user.name=${user.name}" width="100px" align="top"/>
	  </td>
	  <td width="95%">
		 用户名:${user.name}<br/>
		 姓名:${user.fullname}<br/>
		[#if length??]
		 文件大小:${length}KB<br/>
		 更新时间:${lastModified?string("yyyy-MM-dd HH:mm:ss")}
		[#else]
		 <em>尚无照片</em>
		[/#if]
	  </td>
   </tr>
</table>
</body>
[#include "/template/foot.ftl"/]
