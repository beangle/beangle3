[#ftl]
[@b.grid  items=logonStats var="logonStat"]
	[#assign total=0]
	[@b.row]
		[@b.col name="时间段"]${logonStat[0]}:00-${(logonStat[0]+1)%24}:00[/@]
		[@b.col name="登录次数"]${logonStat[1]}[/@]
		[#assign total=total+logonStat[1]]
	[/@]
	<tr style="font-weight: bold;">
		<td>合计</td>
		<td>${total}</td>
	</tr>
[/@]
