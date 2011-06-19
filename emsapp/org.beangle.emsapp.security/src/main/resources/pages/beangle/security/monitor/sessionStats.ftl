[#ftl]
[@b.head/]
[@b.toolbar title="${sessionStats?first.statAt?string('yyyy-MM-dd HH:mm:ss')}各服务器会话分布" /]
<table class="gridtable" style="width:600px">
<thead class="gridhead">
<th>序号</th><th>服务器</th><th>会话数</th>[#list categoryProfiles as cp]<th>${cp.category.name}(${cp.capacity})</th>[/#list]
</thead>
[#assign total=0 /]
[#assign detailTotal={}/]
[#list sessionStats as stat]
<tr style="text-align:center">
<td>${stat_index+1}</td><td>${stat.serverName}</td><td>${stat.sessions}[#assign total=total + stat.sessions/]</td>
[#list categoryProfiles as cp]
<td>${stat.details.get(cp.category.name)!}[#assign detailTotal=detailTotal + {cp.category.name:(detailTotal[cp.category.name]!0) + stat.details.get(cp.category.name)!0} /]</td>
[/#list]
</tr>
[/#list]
<tr style="text-align:center">
	<td colspan="2">总计</td><td>${total}</td>
	[#list categoryProfiles as cp]
	<td>${detailTotal[cp.category.name]}</td>
	[/#list]
</tr>
</table>
[@b.foot/]