[#ftl]
<tr [#if extra['cssClass']??]class="${extra['cssClass']}"[#else]class="pagebar"[/#if] [#if extra['cssStyle']??]style="${extra['style']}"[/#if]>
	<td align="center" id="${pageId}_bar">
		[#if curPage.pageNo==1][@msg.text name="page.first"/]&nbsp;[#else]
		<a href="#" onclick="bg.page.goPage('${pageId}',1)">[@msg.text name="page.first"/]</a>
		<a href="#" onclick="bg.page.goPage('${pageId}',${curPage.previousPageNo})" >[@msg.text name="page.previous"/]</a>
		[/#if]
		<input type="text" name="pageNo" value="${curPage.pageNo}" title="当前页"
		onchange="bg.page.goPage('${pageId}',this.value)" style="width:30px;background-color:#CDD6ED">
		[#if !(curPage.hasNext())]共${curPage.maxPageNo}页[#else]
		<a href="#" onclick="bg.page.goPage('${pageId}',${curPage.nextPageNo})" >[@msg.text name="page.next" /]</a>
		<a href="#" onclick="bg.page.goPage('${pageId}',${curPage.maxPageNo})" title="[@msg.text name="page.last"/]">共${curPage.maxPageNo}页</a>
		[/#if]
		[#if !fixPageSize!false]
		每页[#assign pageRank=[10,15,20,25,30,50,70,90,100,150,300,1000]]
		<select name="pageSize" onchange="bg.page.goPage('${pageId}',1,this.value)">
		[#list pageRank as rank]<option value="${rank}" [#if curPage.pageSize=rank]selected[/#if]>${rank}</option>[/#list]
		</select>
		[#else]
		每页${curPage.pageSize}
		[/#if]
		总${curPage.total}条
	</td>
</tr>
<script>
bg.ui.grid.spanPagebar("${pageId}_bar");
</script>
