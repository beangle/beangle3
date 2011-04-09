[#ftl]
[#if !files??]bad path[#else]
<table  width="80%" align="center">
	<tr>
	<td colspan="3">
		<img src="${b.theme.iconurl('actions/go-up.png','48x48')}" width="18px" height="18px"/>
		[@b.a href="!list?path=${(path?js_string)!}.."]上级目录[/@]
		<img src="${b.theme.iconurl('actions/go-home.png','48x48')}" width="18px" height="18px"/>
		[@b.a href="!list"]home[/@]
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
			<img src="${b.theme.iconurl('mimetypes/' + mimeType.getMimeType(file) + '.png','48x48')}" width="18px" heigth="18px"/>
			[#if mimeType.isTextType(file)][@b.a href="!download?path=${file.absolutePath?url}" title="download"]<span[#if file.hidden] style="color:#999999"[/#if]>${file.name}</span>[/@]
			[#else]
			[@b.a href="!download?path=${file.absolutePath?url}" title="download" target="_self"]<span[#if file.hidden] style="color:#999999"[/#if]>${file.name}</span>[/@]
			[/#if]
			[#else]
			<img src="${b.theme.iconurl('places/folder.png','22x22')}" width="18px" height="18px"/>
			[@b.a href="!list?path=${file.absolutePath?url}"]<span[#if file.hidden] style="color:#999999"[/#if]>${file.name}</span>[/@]
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
	document.filePathForm['path'].value='${(path?js_string)!}';
</script>
