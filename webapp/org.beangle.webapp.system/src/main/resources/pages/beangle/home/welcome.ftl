[#ftl]
[@b.head title="Dashboard"/]
<script type="text/javascript">
  function getMessageInfo(id){
	 window.open("${b.url('systemMessage!info')}?systemMessage.id="+id, '', 'scrollbars=yes,left=0,top=0,width=600,height=350,status=yes');
  }
  function getNoticeInfo(id){
	 window.open("notice.action!info?notice.id="+id, '', 'scrollbars=yes,left=0,top=0,width=600,height=350,status=yes');
  }
  function changePassword(){
	  var url = "${b.url('changePassword!changePassword')}";
	  var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	  selector.moveTo(200,200);
	  selector.resizeTo(300,250);
  }
</script>
[@b.module title="欢迎信息"]
	欢迎您:${(user)!},今天是${date}
[/@]

[#if notices?? && notices?size!=0]
[@b.module title="系统公告"]
   <table  style="font-size:10pt" width="90%">
   <tr >
	 <td width="50%">标题</td>
	 <td width="20%">发布时间</td>
   </tr>
   [#list notices as notice]
   <tr >
	<td><a style="color:blue" href="#"  alt="察看详情" onclick="getNoticeInfo('${notice.id}');">${notice.title}</a></td>
	<td>${notice.modifiedAt}</td>
   </tr>
   [/#list]
  </table>
[/@]
[/#if]

[#if downloadFileList??]
[#assign extMap={"xls":'xls.gif',"doc","doc.gif","pdf":"pdf.gif","zip":"zip.gif","":"generic.gif"}]
[@b.module title="文件下载"]
 <table  style="font-size:10pt" width="90%">
   <tr>
	 <td width="80%">文档标题</td>
	 <td width="20%">发布时间</td>
   </tr>
  [#list downloadFileList as file]
   <tr>
	<td><image src="${base}/static/images/file/${extMap[file.fileExt]!("generic.gif")}">&nbsp;[@b.a href="download!download?document.id=${file.id}" style="color:blue"]${file.name}[/@]</td>
	<td>${file.uploadOn?string("yyyy-MM-dd")}</td>
   </tr>
   [/#list]
  </table>
	[@b.a href="download!index"]&nbsp;更多....[/@]
[/@]
[/#if]

[@b.foot/]