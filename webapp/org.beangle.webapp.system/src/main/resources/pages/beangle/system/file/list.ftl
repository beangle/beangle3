[#ftl]
[#if !files??]bad path[#else]
<table  width="80%" align="center">
	<tr>
	  <td colspan="3">
		  <img src="${base}/static/icons/beangle/48x48/actions/go-up.png" width="18px" height="18px"/>
		  [@sj.a id="action_parent" href="${b.url('!list')}?path=${(path?js_string)!}.." targets="filelist"]上级目录[/@sj.a]
		  <img src="${base}/static/icons/beangle/48x48/actions/go-home.png" width="18px" height="18px"/>
		  [@sj.a id="action_home" href="${b.url('!list')}" targets="filelist"]home[/@]
		  [#--<img src="${base}/static/icons/beangle/48x48/actions/folder-new.png" width="18px" height="18px"/]
		  [@sj.a id="action_newFolder" formId="pathForm" targets="filelist"]创建目录[/@]
		  <img src="${base}/static/images/action/new.gif"/>
		  [@sj.a id="action_newFile"  href="${b.url('!newFile')}" targets="filelist"]创建文件[/@]
		  <img src="${base}/static/icons/beangle/48x48/actions/edit-delete.png" width="18px" height="18px"/><a href="?"/>删除</a>
		  <img src="${base}/static/icons/beangle/48x48/actions/edit-rename.png" width="18px" height="18px"/><a href="?"/>重命名</a>
		  <img src="${base}/static/icons/beangle/48x48/actions/edit-copy.png" width="18px" height="18px"/><a href="?"/>复制</a>
		  <img src="${base}/static/icons/beangle/48x48/actions/edit-cut.png" width="18px" height="18px"/><a href="?"/>剪切</a>
		  [#if paste_target??]
		  <img src="${base}/static/icons/beangle/48x48/actions/edit-paste.png" width="18px" height="18px"/><a href="?"/>粘帖</a>
		  [/#if]
		  --]
	  </td>
	</tr>
</table>
<table width="80%" align="center">
	<thead style="font-weight: bold;text-align:center">
		<td class="file-name" width="60%">文件名</td>
		<td>大小</td>
		<td>最后修改时间</td>
	</thead>
	[#setting url_escaping_charset='UTF-8']
	[#list files as file]
	<tr>
		<td class="file-name">
			[#if file.file]
			<img src="${base}/static/icons/beangle/48x48/mimetypes/${mimeType.getMimeType(file)}.png" width="18px" heigth="18px"/>
			[#if mimeType.isTextType(file)][@sj.a href="${b.url('!download')}?path=${file.absolutePath?url}"  targets="filelist" title="download"]${file.name}[/@]
			[#else]
			<a href="${b.url('!download')}?path=${file.absolutePath?url}"  title="download">${file.name}</a>
			[/#if]
			[#else]
			<img src="${base}/static/icons/beangle/22x22/places/folder.png" width="18px" height="18px"/>
			[@sj.a id="file${file_index}" href="${b.url('!list')}?path=${file.absolutePath?url}" targets="filelist"]${file.name}[/@sj.a]
			[/#if]
		</td>
		<td align="right">[#if file.file]${file.length()/1024}KB[/#if]</td>
		<td align="center">${dateformat.format(file.lastModified(), "yyyy-MM-dd  HH:mm:ss")}</td>
	</tr>
	[/#list]
	<tr><td colspan="3">&nbsp;&nbsp;本目录共计${files?size}个项目</td></tr>
</table>
[/#if]
<script type="text/javascript">
	document.pathForm['path'].value='${(path?js_string)!}';
	$.subscribe('beforeNewFolder', function(event,data) {
		var folderName=prompt("new folder name:");
		if(null!=folderName){
		   addInput(document.pathForm,"method","newFolder");
		   addInput(document.pathForm,"newFolder",folderName);
		}
	});
</script>
