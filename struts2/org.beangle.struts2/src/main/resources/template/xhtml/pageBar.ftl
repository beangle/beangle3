[#ftl]
<div [#if extra['cssClass']??]class="${extra['cssClass']}"[#else]class="pagebar"[/#if] [#if extra['cssStyle']??]style="${extra['style']}"[/#if]>
	[#if curPage.pageNo==1]${b.text("page.first")} [#else]
	<a href="#" onclick="bg.page.goPage('${pageId}',1)">${b.text("page.first")}</a>
	<a href="#" onclick="bg.page.goPage('${pageId}',${curPage.previousPageNo})" >${b.text("page.previous")}</a>
	[/#if]
	<input type="text" name="pageNo" value="${curPage.pageNo}" title="当前页"
	onchange="bg.page.goPage('${pageId}',this.value)" style="width:30px;background-color:#CDD6ED"/>
	[#if !(curPage.hasNext())]共${curPage.maxPageNo}页[#else]
	<a href="#" onclick="bg.page.goPage('${pageId}',${curPage.nextPageNo})" >${b.text("page.next")}</a>
	<a href="#" onclick="bg.page.goPage('${pageId}',${curPage.maxPageNo})" title="${b.text("page.last")}">共${curPage.maxPageNo}页</a>
	[/#if]
	[#if !fixPageSize!false]
	每页[#assign pageRank=[10,15,20,25,30,50,70,90,100,150,300,1000]]
	<select name="pageSize" onchange="bg.page.goPage('${pageId}',1,this.value)" title="page size">
	[#list pageRank as rank]<option value="${rank}" [#if curPage.pageSize=rank]selected="selected"[/#if]>${rank}</option>[/#list]
	</select>
	[#else]
	每页${curPage.pageSize}
	[/#if]
	总${curPage.total}条
</div>