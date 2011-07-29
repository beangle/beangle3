[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
	<div class="result ui-widget-content ui-corner-all" style="width:800px;margin:10px 5px;" align="left">
	[@b.messages/]
		<table width="100%">
		 <tr>
		   <td align="right" width="20%">照片库信息:</td><td>${avatarBase.description!}&nbsp;&nbsp;[@b.a href="/system/property"]进入系统配置>>[/@]</td>
		 </tr>
		 <tr>
		   <td align="right">照片总数量:</td><td>${names.total!}</td>
		 </tr>
		 [#if !avatarBase.readOnly]
		 <tr>
		   <td align="right">打包上传</td><td>
			[@b.form action="!uploadBatch" enctype="multipart/form-data"]
				<input type="file" id="avatar" value="" name="avatar">
				[@b.submit value="提交" /]
			[/@]
			</td>
		 </tr>
		 <tr>
		   <td colspan="2" align="center">上传文件需为zip格式，不接受rar格式文件，大小在50M以内。</td>
		 </tr>
		 [/#if]
	   <table>
	</div>
	<div class="result ui-widget-content ui-corner-all" style="width:700px" align="left">
	<table align="center" id="avatarPanel" width="90%">
		[#list names?chunk(5) as nameList]
		<tr>
		  [#list nameList as name]
		  <td style="padding:5px" align="center">
			  <img src="${b.url('user')}?user.name=${name}" title="${name}" alt="${name}" width="120px" align="top"/><br/>
			  <a href="${b.url('board!info')}?user.name=${name}" title="点击查看照片详情">${name}</a>
		  </td>
		  [/#list]
	   </tr>
	   [/#list]
	</table>
	[@b.pagebar id="avatarPanel" page=names/]
	</div>
[@b.foot/]