[#ftl]
[@b.grid  items=logonStats var="logonStat"]
	[#assign total=0]
	[@b.row]
		[@b.col title="时间段"]${logonStat[0]}:00-${(logonStat[0]+1)%24}:00[/@]
		[@b.col title="登录次数"]${logonStat[1]}[/@]
		[#if logonStat??][#assign total=total+logonStat[1]][/#if]
	[/@]
	<tr style="font-weight: bold;">
		<td>合计</td>
		<td>${total}</td>
	</tr>
[/@]
