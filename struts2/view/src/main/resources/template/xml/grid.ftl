[#ftl/]
<div class="grid">[@b.messages slash="4"/]
[#if tag.caption??]<div class="grid-caption">${tag.caption?html}</div>[/#if]
[#if tag.hasbar]<div id="${tag.id}_bar1" class="gridbar"></div>[/#if]
<table id="${tag.id}" class="gridtable"${tag.parameterString}>
[#if tag.cols?size>0]
<thead class="gridhead">

[#assign filterable=(tag.filterable="true" || tag.filters?size>0)]
[#if filterable]
<tr>
[#list tag.cols as cln]
  [#if cln.type??]
  <th class="gridselect-top" [#if cln.width??] width="${cln.width}"[/#if]>[@b.submit id="${tag.id}_filter_submit" class="grid-filter-submit" value=""/]</th>
  [#else]
  [#if tag.isFilterable(cln)]
  <th title="${cln.title}" [#if cln.width??]width="${cln.width}"[/#if] style="padding-left:3px">[#t/]
  [#if tag.filters[cln.property]??]${tag.filters[cln.property]}[#else][#t/]
  <div style="margin-right:6px"><input type="text" name="${cln.propertyPath}"  maxlength="100" value="${(Parameters[cln.propertyPath]!)?html}" style="width:100%;"/></div>[#t/]
  [/#if]
  </th>
  [#else]<th [#if cln.width??]width="${cln.width}"[/#if]></th>[/#if][#t/]
  [/#if]
[/#list]
</tr>
[/#if]

<tr>
[#list tag.cols as cln]
<th [#if !filterable && cln.width??] width="${cln.width}"[/#if] [#if cln.type??]class="gridselect-top" [#elseif tag.isSortable(cln)]class="gridhead-sortable" id="${cln.parameters['sort']!(tag.defaultSort(cln.property))}"[/#if]>[#t/]
  [#if cln.type??][#if cln.type=="checkbox"]<input type="${cln.type}" name="${cln.boxname}box" onclick="bg.ui.grid.toggleAll(event)" title="${b.text('action.selectall')}"/>[/#if] [#t/]
  [#else]${cln.title}[/#if][#t/]
</th>
[/#list]
</tr>

</thead>
[/#if]

<tbody id="${tag.id}_data">${tag.body}</tbody>
</table>
[#if tag.hasbar]
[#if tag.notFullPage]
<div class="gridempty" id="${tag.id}_empty"></div>
[/#if]
<div id="${tag.id}_bar2"  class="gridbar"></div>
[/#if]
</div>
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
  [/#if]
  [#if tag.hasbar && tag.notFullPage]bg.ui.grid.fillEmpty('${tag.id}_empty',[#if tag.pageable]${tag.items.pageSize}[#else]10[/#if],${tag.items?size},'${tag.emptyMsg!b.text("page.noData")}');[/#if]
  [#if tag.var??]action=bar.addEntityAction('${tag.var}',page_${tag.id});[/#if]
  ${tag.bar!}
  [/#if]
  [#-- refresh interval --]
  [#if tag.refresh??]
  if(typeof ${tag.id}_timer !="undefined"){clearTimeout(${tag.id}_timer)}
  var ${tag.id}_timer=setTimeout(function(){if(document.getElementById('${tag.id}')) page_${tag.id}.goPage()},${tag.refresh}*1000);
  [/#if]
</script>