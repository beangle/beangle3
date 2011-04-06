[#ftl]
<div id="datasource_info">
<table>
	<tr>
	<td>dialect:${datasource.provider.dialect!}</td>
	</tr>
	<tr>
	<td>url:${datasource.url}</td>
	</tr>
	<tr>
	<td>username:${datasource.username}</td>
	</tr>
	<tr>
	<td>password:${datasource.password!}</td>
	</tr>
	<tr>
	<td align="center" colspan="2"><a href="${b.url('index!connect')}?datasource.id=${datasource.id}" button="true">Connect</a>&nbsp;[@sj.a href="${b.url(!test')}?id=${datasource.id}" button="true" targets="testResult"]Test Connect[/@]</td>
	</tr>
</table>
<div id="testResult"></div>
</div>