[#ftl]
[@b.head/]
[#include "nav.ftl"/]
	<p align="center">集合统计</p>
	<table class="gridtable" style="width:80%;margin:auto">
		<tr>
			<th class="gridhead" width="5%">Index</th>
			<th class="gridhead" width="53%">Role</th>
			<th class="gridhead" width="8%">Loads</th>
			<th class="gridhead" width="8%">Fetches</th>
			<th class="gridhead" width="8%">Updates</th>
			<th class="gridhead" width="8%">Recreates</th>
			<th class="gridhead" width="8%">Remove</th>
		</tr>
		[#list statistics.collectionRoleNames?sort as collection]
		[#assign collectionStats=statistics.getCollectionStatistics(collection)/]
		<tr>
			<td>${collection_index+1}</td>
			<td>${collection}</td>
			<td>${collectionStats.loadCount}</td>
			<td>${collectionStats.fetchCount}</td>
			<td>${collectionStats.updateCount}</td>
			<td>${collectionStats.recreateCount}</td>
			<td>${collectionStats.removeCount}</td>
		</tr>
		[/#list]
	</table>
[@b.foot/]