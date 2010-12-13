[#ftl]
[#include "/template/simpleHead.ftl"/]
<body >
<style  type="text/css">
/* Copyright 2006 Google, Inc.  All Rights Reserved */ 
 .module{margin:1 5 15px;line-height:1.3em;float:left;width:48%;}
 .modulebody{display:block;padding:4px 8px;background:#e0ecff;font-size:10pt} 
 .collapsed .modulebody,.collapsed .collapsed{display:none}
 .title{background:#ccc;margin:0 0 10px;font-size:160%;line-height:1.2em} 
  .title .modulebody{padding:4px}
  .title a.editable:hover,.title input.text{border-color:#666 #ddd #ddd #666} 
  .header{background:#94aef3;margin:0;padding:0;font-size:100%;line-height:1.2em}
   .expanded .header,.collapsed .header{background-image:url("images/arrow_down.gif");
   background-repeat:no-repeat;background-position:4px 45%} 
   .collapsed .header{background-image:url("images/arrow_right.gif")}
  .header .toggle,.header .toggle:visited{display:block;padding:4px;text-decoration:none;color:#000} * html .header .toggle,* html .header .toggle:visited{height:1.2em} .expanded .header .toggle,.collapsed .header .toggle,.expanded .header .toggle:visited,.collapsed .header .toggle:visited{padding-left:18px} .header .toggle:hover{color:#25a;text-decoration:none} .header .toggle em{font-style:normal;font-weight:normal} .header em strong{color:#c00;font-weight:normal} .module h3{margin:0;padding:2px 0;font-size:100%} .module h3 a,.module h3 em{font-weight:normal;font-style:normal} .subtitle{margin:0;padding:3px 4px 2px;font-size:100%}
   .subtitle em{font-style:normal;font-weight:normal} .data .subtitle{padding-left:0}  
 .header{background-image:none} 

 .field{margin-top:2px;padding-top:2px}
 </style>

<script>
   function _wi_tm(moudleId){
       var id= document.getElementById(moudleId);   
	   if(id.className=="module collapsed"){
	     id.className="module expanded";
	   }else{
	     id.className="module collapsed";
	   }
  }
  function getMessageInfo(id){
     window.open("systemMessage.action?method=info&systemMessage.id="+id, '', 'scrollbars=yes,left=0,top=0,width=600,height=350,status=yes');
  }
  function getNoticeInfo(id){
     window.open("notice.action?method=info&notice.id="+id, '', 'scrollbars=yes,left=0,top=0,width=600,height=350,status=yes');
  }
  function changePassword(){
      var url = "changePassword.action?method=changePassword";
      var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	  selector.moveTo(200,200);
	  selector.resizeTo(300,250);
  }
</script>
   <div id="user" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('user');">欢迎信息</a>
     </h2>
     <div class="modulebody">
     	欢迎您:[#if Session['user.key']??]${Session['user.key'].fullname!}[/#if]     
     	今天是${date}	<br>
     	<a target="_blank" href="home.action>home</a>
     	<br>
     </div>
   </div>
 
 [#if notices??]
  [#if notices?size!=0 ]
   <div id="notice" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('notice');">系统公告</a>
     </h2>
     <div class="modulebody">
       <table  style="font-size:10pt" width="90%">
	   <tr >	     
	     <td width="50%">标题</td>
	     <td width="20%">发布时间</td>
	   </tr>
       [#list notices as notice]
	   <tr >
	    <td><A style="color:blue" href="#"  alt="察看详情" onclick="getNoticeInfo('${notice.id}');">${notice.title}</A></td>
        <td>${notice.modifiedAt}</td>
	   </tr>
	   [/#list]
	  </table>
      </div>
   </div>
  [/#if]
  [/#if]
  
[#if downloadFileList??]
  [#assign extMap={"xls":'xls.gif',"doc","doc.gif","pdf":"pdf.gif","zip":"zip.gif","":"generic.gif"}]
   <div id="downloadFileList" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('downloadFileList');">文件下载</a>
     </h2>
     <div class="modulebody">
     <table  style="font-size:10pt" width="90%">
	   <tr>	     
	     <td width="80%">文档标题</td>
	     <td width="20%">发布时间</td>
	   </tr>
      [#list downloadFileList as file]
	   <tr>
	    <td><image src="${base}/static/images/file/${extMap[file.fileExt]!("generic.gif")}">&nbsp;<A style="color:blue" href="download.action?method=download&document.id=${file.id}">${file.name}</A></td>
	    <td>${file.uploadOn?string("yyyy-MM-dd")}</td>
	   </tr>
	   [/#list]
	  </table>
	  <a href="download.action?method=index"/>&nbsp;更多....</A>
   </div>    
   </div>
  [/#if]
  

</body>
[#include "/template/foot.ftl"/]
