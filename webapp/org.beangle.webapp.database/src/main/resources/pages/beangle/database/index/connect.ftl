[#ftl]
[@b.head/]
<div>
[#if datasources??]
[@b.form name="dsform" action="datasource!info" target="datasource_info"]
<table>
<tr>
	<td>
	<label for="datasource.id">Datasource:</label>
	<select name="datasource.id">
		<option value="">...</option>
		[#list datasources as ds]
		<option value="${ds.id}" title="${ds.url!}">${ds.name}</option>
		[/#list]
	</select>
	[@b.submit  value="display" /]
	</td>
</tr>
</table>
[/@]
<hr/>
[@b.div id="datasource_info" /]
[#else]
You has connected to ${QueryContext.datasourceBean.name}!
<hr/>
[#assign ds=QueryContext.datasourceBean]
<table>
	<tr>
	<td>dialect:${ds.provider.dialect!}</td>
	</tr>
	<tr>
	<td>url:${ds.url}</td>
	</tr>
	<tr>
	<td>username:${ds.username}</td>
	</tr>
	<tr>
	<td>password:${ds.password!}</td>
	</tr>
</table>
[/#if]
</div>
[@b.foot/]