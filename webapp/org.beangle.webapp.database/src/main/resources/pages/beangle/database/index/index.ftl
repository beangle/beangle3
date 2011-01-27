[#ftl]
[@b.head/]
<table>
<tbody>
	<tr>
	<td><a href="${b.url('datasource')}">Data Source</a>
	[@sj.a href="${b.url('!connect')}" targets="datacontent"]Connect[/@]
	<a href="${b.url('!disconnect')}"><img  style="border: 0 none" title="Disconnect" alt="Disconnect" src="${base}/static/images/database/disconnect.gif"/></a>
	<img  style="border: 0 none" alt="" src="${base}/static/images/database/line.gif"/>
	<a href="tables.do?jsessionid=c91ff85be9cd937e91fe3af84bb566b4"><img  style="border: 0 none" title="Refresh" alt="Refresh" src="${base}/static/images/database/refresh.gif"/></a>
	<img  style="border: 0 none" alt="" class="iconLine" src="${base}/static/images/database/line.gif">
	</td>
	<td><input type="checkbox" onclick="javascript:alert(1)" value="autoCommit" name="autoCommit" />Auto commit&nbsp;</td>
	<td>
		<a href="query.do?jsessionid=c91ff85be9cd937e91fe3af84bb566b4&amp;sql=ROLLBACK"><img  style="border: 0 none" title="Rollback" alt="Rollback" src="${base}/static/images/database/rollback.gif" /></a>
		<a href="query.do?jsessionid=c91ff85be9cd937e91fe3af84bb566b4&amp;sql=COMMIT"><img  style="border: 0 none" title="Commit" alt="Commit" src="${base}/static/images/database/commit.gif" /></a>
	</td>
	<td>&nbsp;Max rows:&nbsp;
		<select><option value="0">All</option>
		<option value="10000">10000</option>
		<option value="1000" selected="selected">1000</option>
		<option value="100">100</option>
		<option value="10">10</option>
		</select>&nbsp;
	</td>
	<td>
		<a href="javascript:"><img  style="border: 0 none" title="Run (Ctrl+Enter)" alt="Run (Ctrl+Enter)" src="${base}/static/images/database/run.gif" /></a>
	</td>
	<td>
		<a href="query.do?jsessionid=c91ff85be9cd937e91fe3af84bb566b4&amp;sql=@cancel."><img  style="border: 0 none" title="Cancel the current statement" alt="Cancel the current statement" src="${base}/static/images/database/stop.gif" /></a>
		<img  style="border: 0 none" alt=""  src="${base}/static/images/database/line.gif" />
		<a href="query.do?jsessionid=c91ff85be9cd937e91fe3af84bb566b4&amp;sql=@history."><img  style="border: 0 none" title="Command history" alt="Command history" src="${base}/static/images/database/history.gif" /></a>
		<img  style="border: 0 none" alt="" class="iconLine" src="${base}/static/images/database/line.gif">
	</td>
	<td><a target="h2result" href="help.jsp?jsessionid=c91ff85be9cd937e91fe3af84bb566b4"><img  style="border: 0 none" title="Help" alt="Help" src="${base}/static/images/database/help.gif" /></a></td>
	</tr>
</tbody>
</table>
<div id="datacontent"></div>
[@b.foot/]
