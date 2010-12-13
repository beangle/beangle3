[#ftl]
[#include "/template/head.ftl"/]
<html>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<table width="100%">
    <tr>
      <td class="infoTitle" width="20%" style="height:22px;">
       <img src="${base}/static/images/action/info.gif" align="top"/><B>
          <B>上传自己的照片</B>
      </td>
      <td class="infoTitle" width="20%" style="height:22px;">
 		<font color="red">&nbsp;[@s.actionerror/]</font>
      </td>
    </tr>
    <tr>
      <td colspan="5" style="font-size:0px">
          <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
</table>
   <table  align='center' width="100%">
     <tr>
      <td>
	    [@s.form name="uploadForm" action="/avatar/my-upload!upload.action" method="POST"  enctype="multipart/form-data"]
	        [@s.file name="avatar" label="文件目录"/][@s.submit value="提交"/]
	    [/@s.form]
     </td>
    </tr>
   </form>
   </table>
  <pre>
     注意:上传的图片格式包括:jpg,jpeg,png,gif.
          上传的图片应遵守照片的比例大小,以便于显示.
          本照片会用于考试和其他输出证件上,请上传本人照片!
  </pre>
<script>
  var picFormat = {
  'jpg':true,
  'png':true,
  'jpeg':true,
  'gif':true
  };
  function validateExtendName(form){
  	var value=form.studentFile.value;
  	var index=value.lastIndexOf(".");
  	var postfix=value.substring(index+1);
  	if(picFormat[postfix.toLowerCase()]){
  	}else{
		alert("请按照提示上传指定格式的图片!");
		return;
	}
	form.submit();
  }
</script>
 </body>
[#include "/template/foot.ftl"/]
