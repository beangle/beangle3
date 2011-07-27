[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<table width="100%">
	<tr>
	  <td class="infoTitle" width="20%" style="height:22px;">
		  <em>我的照片</em>
	  </td>
	  <td class="infoTitle" width="20%" style="height:22px;">
 		<font color="red">&nbsp;[@b.messages/]</font>
	  </td>
	</tr>
	<tr>
	  <td colspan="5" style="font-size:0px">
		  <img src="${b.theme.iconurl("actions/keyline.png")}" height="2" width="100%" align="top"/>
	  </td>
   </tr>
	<tr>
	  <td>
		  <img src="${b.url('my')}" width="100px" align="top"/>
	  </td>
	  <td width="95%">
		用户:${user}<br/>
		[#if avatar??]
		 文件大小:${avatar.size/1024}KB<br/>
		 更新时间:${(avatar.updatedAt?string("yyyy-MM-dd HH:mm:ss"))!}
		[#else]
		 <em>尚无照片</em>
		[/#if]
	  </td>
   </tr>
</table>
[@ems.guard res="/avatar/my-upload"]
[@b.div href="my-upload" astarget="false"/]
[/@]
[@b.foot/]
