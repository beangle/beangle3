[#ftl]
[@b.grid width="100%"  id="sessionActivityListTable" target="sessionActivityResult"]
	[@b.gridhead]
		[@b.gridth width="5%" text="序号"/]
		[@b.gridth width="20%" text="登录名(姓名)" sort="sessionActivity.name"/]
		[@b.gridth width="15%" text="登录时间"  sort="sessionActivity.loginAt"/]
		[@b.gridth width="15%" text="退出时间" sort="sessionActivity.logoutAt"/]
		[@b.gridth width="10%" text="在线时间" sort="sessionActivity.onlineTime"/]
		[@b.gridth text="ip" sort="sessionActivity.host"/]
		[@b.gridth text="操作系统" sort="sessionActivity.os"/]
		[@b.gridth text="用户代理" sort="sessionActivity.agent"/]
		[@b.gridth text="备注"/]
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
