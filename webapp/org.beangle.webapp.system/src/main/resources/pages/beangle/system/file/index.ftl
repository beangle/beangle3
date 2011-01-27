[#ftl]
  <style type="text/css">
	.file-browser {
		width: 800px;
		margin:0 auto;
		padding-bottom: 5px;
	}
	.file-name {
	   padding-left: 20px;
	   padding-top: 2px;
	   padding-bottom: 2px;
	   text-align:left
	}
	.banner {
	  padding-top: 30px;
	  font-weight: bold;
	}
  </style>
  <div class="result ui-widget-content ui-corner-all file-browser">
	<table width="80%" align="center">
		<tr>
		  <td class="banner">
			[@s.form  id="pathForm" action="file" theme="simple"]
			Path:<input type="text"  name="path" value="${path!}" style="width:80%;font-weight: bold"/>
			<input name="method" type="hidden" value="list"/>
			[@sj.submit id="fileSubmit" targets="filelist" value="Go"/]
			[/@s.form]
		  </td>
		</tr>
		<tr>
		  <td  colspan="2" style="font-size:0px">
			  <img src="${base}/static/icons/default/action/keyline.gif" height="2" width="100%" align="top"/>
		  </td>
		</tr>
	</table>

	[@sj.div id="filelist" href="${b.url('!list')}?path=${path?js_string!}" indicator="indicator" ]
		<img id="indicator" src="images/indicator.gif" alt="Loading..." style="display:none"/>
	[/@]
	</div>