[#ftl]
[@b.head/]
<table width="100%">
	<tr>
	  <td class="infoTitle" width="20%" style="height:22px;">
		  <em>上传自己的照片</em>
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
</table>
   <table  align='center' width="100%">
	 <tr>
	  <td>
		[@b.form name="uploadForm" action="!upload" enctype="multipart/form-data"]
			[@s.file name="avatar" label="文件目录" theme="simple"/]
			[@b.submit value="提交"/]
		[/@]
	 </td>
	</tr>
   </form>
   </table>
  <pre>
	 注意:上传的图片格式包括:jpg,jpeg,png,gif.
		  上传的图片应遵守照片的比例大小,以便于显示.
		  本照片会用于考试和其他输出证件上,请上传本人照片!
  </pre>
<script type="text/javascript">
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
 
[@b.foot/]
