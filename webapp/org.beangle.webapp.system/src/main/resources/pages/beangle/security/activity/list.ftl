<@table.table width="100%" sortable="true" id="sessionActivityListTable" target="sessionActivityResult">
	<@table.thead>
		<@table.sortTd width="20%" text="登录名(姓名)" id="sessionActivity.name"/>
		<@table.sortTd width="17%" text="登录时间"  id="sessionActivity.loginAt"/>
		<@table.sortTd width="17%" text="退出时间" id="sessionActivity.logoutAt"/>
		<@table.sortTd width="10%" text="在线时间" id="sessionActivity.onlineTime"/>
		<@table.sortTd text="ip" id="sessionActivity.host"/>
		<@table.td text="备注"/>
	</@>
	<@table.tbody datas=sessionActivitys;sessionActivity>
		<td>${sessionActivity.name}(${sessionActivity.fullname})</td>
		<td>${sessionActivity.loginAt?string("yy-MM-dd HH:mm")}</td>
		<td>${sessionActivity.logoutAt?string("yy-MM-dd HH:mm")}</td>
		<td>${(sessionActivity.onlineTime/60000)?int}分${(sessionActivity.onlineTime/1000)%60}秒</td>
		<td>${sessionActivity.host}</td>
		<td>${sessionActivity.remark!('')}</td>
	</@>
</@>
