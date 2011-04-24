[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<style type="text/css">
	.file-browser {
		width: 800px;
		margin:5px 5px;
		padding-bottom: 5px;
	}
	.file-name {
	   padding-left: 20px;
	   padding-top: 2px;
	   padding-bottom: 2px;
	   text-align:left
	}
	.file_location {
	  padding-top: 30px;
	  font-weight: bold;
	}
</style>
<div class="result ui-widget-content ui-corner-all file-browser">
[@b.form name="filePathForm" action="!list" target="filelist"]
<table width="90%" align="center">
	<tr>
		<td class="file_location">
		Path:<input type="text"  name="path" value="${path!}" style="width:80%;font-weight: bold"/>
		[@b.submit value="Go"/]
		<hr/>
		</td>
	</tr>
</table>
[/@]
[@b.div id="filelist" href="!list?path=${path?js_string!}" /]
</div>
[@b.foot/]