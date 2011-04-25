[#ftl]
[@b.css href="code.css"/]
<table width="100%" align="center">
	<tr>
	  <td>
		<img src="${b.theme.iconurl('actions/go-previous.png','48x48')}" width="18px" height="18px"/>
		[@b.a href="!list?path=${file.parent?js_string}"]返回[/@]
		<img src="${base}/static/images/action/download.gif"/>
		<a href="${b.url('!download')}?path=${file.absolutePath?js_string}&download=1">下载</a>
		<hr/>
		${file.absolutePath}/${file.name}
	</td>
	</tr>
</table>
<table class="filecontent CodeRay">
	<tbody>
		[#list lines as line]
		<tr><th class="line-num" id="L${line_index+1}"><a href="#L${line_index+1}">${line_index+1}</a></th>
			<td class="line-code"><pre>${line?html}</pre></td>
		</tr>
		[/#list]
	</tbody>
</table>
