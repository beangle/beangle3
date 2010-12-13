[#ftl]
[#include "/template/head.ftl"/]
<html>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<table width="100%" border="0">
    <tr>
      <td class="infoTitle" width="20%" style="height:22px;">
       <img src="${base}/static/images/action/info.gif" align="top"/><B>
          <B>照片信息</B>
      </td>
      <td class="infoTitle" width="20%" style="height:22px;">
 		<font color="red">&nbsp;[@s.actionmessage/]</font>
      </td>
    </tr>
    <tr>
      <td colspan="5" style="font-size:0px">
          <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <tr>
      <td>
          <img src="${base}/avatar/user.action?user.name=${user.name}" width="100px" align="top">
      </td>
      <td width="95%">
         用户名:${user.name}<br>
         姓名:${user.fullname}<br>
        [#if avatar??]
         文件大小:${avatar.size/1024}KB<br>
         更新时间:${(avatar.updatedAt?string("yyyy-MM-dd HH:mm:ss"))!}
        [#else]
         <B>尚无照片</B>
        [/#if]
      </td>
   </tr>
</table>
   <table  align='center' width="100%">
     <tr>
       <td>
         <pre>
 更新照片
 注意:上传的图片格式包括:jpg,jpeg,png,gif.
      上传的图片应遵守照片的比例大小,以便于显示.
      本照片会用于考试和其他输出证件上,请上传正式照片!
        </pre>
       </td>
     </tr>
     <tr>
      <td>
	    [@s.form name="uploadForm" action="/avatar/board!upload.action" method="POST"  enctype="multipart/form-data"]
	        <input type="hidden" name="user.name" value="${Parameters['user.name']}"/>
	        [@s.file name="avatar" label="文件目录"/][@s.submit value="提交" /]
	    [/@s.form]
     </td>
    </tr>
   </table>

</body>
[#include "/template/foot.ftl"/]
