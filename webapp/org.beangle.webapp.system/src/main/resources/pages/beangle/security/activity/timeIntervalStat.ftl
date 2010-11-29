<@table.table width="100%" >
	<@table.thead>
		<td><b>时间段</b></td>
		<td><b>登录次数</b></td>
	</@>
	<#assign total=0>
	<@table.tbody datas=logonStats;logonStat>
		<td align="center">${logonStat[0]}:00-${(logonStat[0]+1)%24}:00</td>
		<td align="center">${logonStat[1]}</td>
		<#assign total=total+logonStat[1]>
	</@>
	<tr align="center">
		<td>合计</td>
		<td>${total}</td>
	</tr>
</@>
