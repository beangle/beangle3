[#ftl]
[@b.head/]
[#include "nav.ftl"/]
	<p align="center">实体类统计</p>
	<table width="80%" class="gridtable" style="width:80%;margin:auto">
		<tr>
			<th class="gridhead" width="5%">Index</th>
			<th class="gridhead" width="53%">Entity</th>
			<th class="gridhead" width="7%">Loads</th>
			<th class="gridhead" width="7%">Fetches</th>
			<th class="gridhead" width="7%">Inserts</th>
			<th class="gridhead" width="7%">Updates</th>
			<th class="gridhead" width="7%">Deletes</th>
			<th class="gridhead" width="7%">Optimistic failures</th>
		</tr>
		[#list statistics.entityNames?sort as entity]
		[#assign entityStats=statistics.getEntityStatistics(entity)/]
		<tr>
			<td>${entity_index+1}</td>
			<td>${entity}</td>
			<td>${entityStats.loadCount}</td>
			<td>${entityStats.fetchCount}</td>
			<td>${entityStats.insertCount}</td>
			<td>${entityStats.updateCount}</td>
			<td>${entityStats.deleteCount}</td>
			<td>${entityStats.optimisticFailureCount}</td>
		</tr>
		[/#list]
	</table>
[@b.foot/]