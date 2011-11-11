[#ftl]
[@b.head/]
[#include "nav.ftl"/]
	<p align="center">查询缓存统计</p>
	<table class="gridtable" style="width:80%;margin:auto">
		<tr>
			<th class="gridhead" width="5%">Index</th>
			<th class="gridhead" width="50%">HQL Query</th>
			<th class="gridhead" width="5%">Calls</th>
			<th class="gridhead" width="5%">Total rowcount</th>
			<th class="gridhead" width="5%">Max dur.</th>
			<th class="gridhead" width="5%">Min dur.</th>
			<th class="gridhead" width="5%">Avg dur.</th>
			<th class="gridhead" width="5%">Total dur.</th>
			<th class="gridhead" width="5%">Cache hits</th>
			<th class="gridhead" width="5%">Cache miss</th>
			<th class="gridhead" width="5%">Cache put</th>
		</tr>
		[#list statistics.queries?sort as query]
		<tr>
			[#assign queryStats=statistics.getQueryStatistics(query)/]
			<td>${query_index+1}</td>
			<td>${query}</td>
			<td>${queryStats.executionCount}</td>
			<td>${queryStats.executionRowCount}</td>
			<td>${queryStats.executionMaxTime}</td>
			<td>${queryStats.executionMinTime}</td>
			<td>${queryStats.executionAvgTime}</td>
			<td>
				${queryStats.executionAvgTime*queryStats.executionCount}
			</td>
			<td>${queryStats.cacheHitCount}</td>
			<td>${queryStats.cacheMissCount}</td>
			<td>${queryStats.cachePutCount}</td>
		</tr>
		[/#list]
	</table>
[@b.foot/]