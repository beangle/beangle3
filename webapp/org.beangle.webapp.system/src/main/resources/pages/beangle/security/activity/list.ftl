[#ftl]
[@b.grid width="100%" sortable="true" id="sessionActivityListTable" target="sessionActivityResult"]
	[@b.gridhead]
		[@b.td width="5%" text="序号"/]
		[@b.sortTd width="20%" text="登录名(姓名)" id="sessionActivity.name"/]
		[@b.sortTd width="15%" text="登录时间"  id="sessionActivity.loginAt"/]
		[@b.sortTd width="15%" text="退出时间" id="sessionActivity.logoutAt"/]
		[@b.sortTd width="10%" text="在线时间" id="sessionActivity.onlineTime"/]
		[@b.sortTd text="ip" id="sessionActivity.host"/]
		[@b.sortTd text="操作系统" id="sessionActivity.os"/]
		[@b.sortTd text="用户代理" id="sessionActivity.agent"/]
		[@b.td text="备注"/]
	[/@]
	[@b.gridbody datas=sessionActivitys;sessionActivity,sessionActivity_index]
		<td>${sessionActivity_index+1}</td>
		<td>${sessionActivity.name}(${sessionActivity.fullname})</td>
		<td>${sessionActivity.loginAt?string("yy-MM-dd HH:mm")}</td>
		<td>${sessionActivity.logoutAt?string("yy-MM-dd HH:mm")}</td>
		<td>${(sessionActivity.onlineTime/60000)?int}分${(sessionActivity.onlineTime/1000)%60}秒</td>
		<td>${sessionActivity.host}</td>
		<td>${sessionActivity.os!}</td>
		<td>${sessionActivity.agent!}</td>
		<td>${sessionActivity.remark!('')}</td>
	[/@]
[/@]
