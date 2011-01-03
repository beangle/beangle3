[#ftl]
[@b.grid width="100%"  id="listTable"]
	[@b.gridhead]
		[@b.gridth text="登录名" sort="sessionActivity.name"/]
		[@b.gridth text="姓名" sort="sessionActivity.fullname"/]
		[@b.gridth text="登录次数" sort="count(sessionActivity.name)"/]
	[/@]
	[#assign total=0]
	[@b.gridbody datas=loginCountStats;logonStat]
		<td>${logonStat[0]}</td>
		<td>${logonStat[1]}</td>
		<td>[#if logonStat[2]!=0]<a href="session-activity.action?method=search&sessionActivity.name=${logonStat[0]}&startTime=${Parameters['startTime']!}&endTime=${Parameters['endTime']!}" ]${logonStat[2]}</a>[/#if]</td>
		[#assign total=total+logonStat[2]]
	[/@]
[/@]
