[#ftl]
[@b.form name="datasourceForm" action="!save" ]
<table>
	<tr>
		<td>
		<input type="hidden" name="datasource.id" value="${datasource.id!}"/>
		<input type="hidden" name="datasource.provider.id" value="${(datasource.provider.id)}"/>
		<label for="datasource.name">name</label>:<input name="datasource.name" id="datasource.name" value="${datasource.name!}"/>
		</td>
	</tr>
	<tr>
		<td>
		<label for="datasource.url">url</label>:<input name="datasource.url" id="datasource.url" value="${datasource.url!}"/>
		</td>
	</tr>
	<tr>
		<td>
		<label for="datasource.username">username</label>:<input name="datasource.username" id="datasource.username" value="${datasource.username!}"/>
		</td>
	</tr>
	<tr>
		<td>
		<label for="datasource.password">password</label>:<input name="datasource.password" id="datasource.password" value="${datasource.password!}"/>
	<tr>
		<td>
		<label for="datasource.driverClassName">driverClassName</label>:<input name="datasource.driverClassName" id="datasource.driverClassName" value="${datasource.driverClassName!}"/>
		</td>
	</tr>
	<tr>
		<td>
		[@b.submit value="保存"/]
		</td>
	</tr>
</table>
[/@]