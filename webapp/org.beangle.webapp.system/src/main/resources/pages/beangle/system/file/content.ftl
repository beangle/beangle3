<link href="${base}/static/css/code.css" rel="stylesheet" type="text/css">
	<table width="100%" align="center">
	    <tr>
	      <td>
	          文件:${file.absolutePath}/${file.name}</a>
	      </td>
	    </tr>
	    <tr>
	      <td>
	          <img src="${base}/static/icons/beangle/48x48/actions/go-previous.png" width="18px" height="18px"/>
	          <@sj.a href="${base}/system/file!list.action?path=${file.parent?js_string}" targets="filelist">返回</@sj.a>
	          <img src="${base}/static/images/action/download.gif"/>
	          <a href="${base}/system/file!download.action?path=${file.absolutePath?js_string}&download=1">下载</a>
	      </td>
	    </tr>
		<tr>
	      <td  colspan="2" style="font-size:0px">
	          <img src="${base}/static/icons/default/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	    </tr>
	</table>

<table class="filecontent CodeRay">
	<tbody>
		<#list lines as line>
		<tr><th class="line-num" id="L${line_index+1}"><a href="#L${line_index+1}">${line_index+1}</a></th>
			<td class="line-code"><pre>${line?html}</pre></td>
		</tr>
		</#list>
	</tbody>
</table>
