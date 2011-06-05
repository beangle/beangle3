[#ftl/]
<div class="grid">[@b.messages slash="4"/][#if tag.hasbar]<div id="${tag.id}_bar1" class="gridbar"></div>[/#if]
<table id="${tag.id}" class="gridtable"${tag.parameterString}>
<thead class="gridhead"><tr>
[#list tag.cols as cln]<th [#if cln.width??]width="${cln.width}" [/#if][#if cln.type??][#if cln.type!="checkbox"]>[#else]><input type="${cln.type}" name="${cln.boxname}box" onclick="bg.input.toggleCheckBox(document.getElementsByName('${cln.boxname}'),event)" title="${b.text('action.selectall')}"/>[/#if][#else][#if tag.isSortable(cln)]class="gridhead-sortable" id="${cln.parameters['sort']!(tag.defaultSort(cln.property))}"[/#if]>${cln.title}[/#if]</th>
[/#list]
</tr></thead>
<tbody id="${tag.id}_data">${tag.body}</tbody>
</table>
[#if tag.hasbar]
[#if tag.pageable && tag.notFullPage]
<div class="gridempty" id="${tag.id}_empty"></div>
[/#if]
<div id="${tag.id}_bar2"  class="gridbar"></div>
[/#if]
<script type="text/javascript">
page_${tag.id} = bg.page("${request.requestURI}","${tag.parameters['target']!""}");
page_${tag.id}.target("${tag.parameters['target']!""}",'${tag.id}').action("${request.requestURI}").addParams('${b.paramstring}').orderBy("${Parameters['orderBy']!('null')}");
bg.ui.grid.init('${tag.id}',page_${tag.id});
[#if tag.hasbar]
bar=new bg.ui.gridbar(['${tag.id}_bar1','${tag.id}_bar2'],'${(tag.parameters['title']?default(''))?replace("'","\"")}');
bar.pageId('${tag.id}')
[#if tag.pageable]
page_${tag.id}.pageInfo(${tag.items.pageNo},${tag.items.pageSize},${tag.items.total});
bar.addPage(page_${tag.id},[#if tag.parameters['fixPageSize']??][][#else]null[/#if],{${b.text('page.description')}});
[#if tag.notFullPage]bg.ui.grid.fillEmpty('${tag.id}_empty',${tag.items.pageSize},${tag.items?size},'${b.text("page.noData")}');[/#if]
[/#if]
[#if tag.var??]action=bar.addEntityAction('${tag.var}',page_${tag.id});[/#if]
${tag.bar!}
[/#if]
</script>
</div>