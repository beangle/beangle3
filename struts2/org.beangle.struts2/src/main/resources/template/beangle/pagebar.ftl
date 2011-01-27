[#ftl/]
<script type="text/javascript">
	page=bg.page("${tag.id}","${request.requestURI}","${parameters['target']!""}");
	page.maxPageNo=${tag.page.maxPageNo};
	page.addParams('${b.paramstring}');
</script>
[#if parameters['fixPageSize']?? && (parameters['fixPageSize']=='1' || parameters['fixPageSize']=='true')][#assign fixPageSize=true][#else][#assign fixPageSize=false][/#if]
<div [#if parameters['cssClass']??]class="${parameters['cssClass']}"[#else]class="pagebar"[/#if] [#if parameters['cssStyle']??]style="${parameters['style']}"[/#if]>
	[#if tag.page.pageNo==1]${b.text("page.first")} [#else]
	<a href="#" onclick="bg.page.goPage('${tag.id}',1)">${b.text("page.first")}</a>
	<a href="#" onclick="bg.page.goPage('${tag.id}',${tag.page.previousPageNo})" >${b.text("page.previous")}</a>
	[/#if]
	<input type="text" name="pageNo" value="${tag.page.pageNo}" title="当前页"
	onchange="bg.page.goPage('${tag.id}',this.value)" style="width:30px;background-color:#CDD6ED"/>
	[#if !(tag.page.hasNext())]共${tag.page.maxPageNo}页[#else]
	<a href="#" onclick="bg.page.goPage('${tag.id}',${tag.page.nextPageNo})" >${b.text("page.next")}</a>
	<a href="#" onclick="bg.page.goPage('${tag.id}',${tag.page.maxPageNo})" title="${b.text("page.last")}">共${tag.page.maxPageNo}页</a>
	[/#if]
	[#if !fixPageSize!false]
	每页[#assign pageRank=[2,10,15,20,25,30,50,70,90,100,150,300,1000]]
	<select name="pageSize" onchange="bg.page.goPage('${tag.id}',1,this.value)" title="page size">
	[#list pageRank as rank]<option value="${rank}" [#if tag.page.pageSize=rank]selected="selected"[/#if]>${rank}</option>[/#list]
	</select>
	|${tag.page?size}
	[#else]
	每页${tag.page.pageSize}
	[/#if]
	总${tag.page.total}条
</div>
