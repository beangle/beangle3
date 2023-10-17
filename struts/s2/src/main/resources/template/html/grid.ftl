<div class="grid[#if tag.cssClass??] ${tag.cssClass}[/#if]" id="${tag.id}_div">[@b.messages slash="4"/]
[#if tag.caption??]<div class="grid-caption">${tag.caption?html}</div>[/#if]
[#if tag.hasbar]<div id="${tag.id}_bar1" class="grid-bar"></div>[/#if]
<div id="${tag.id}_content" [#if tag.overflow??]style="overflow-x:hidden"[/#if]>
<table id="${tag.id}" class="grid-table border-0px-lr [#if !tag.hasbar]border-0px-tb[/#if]" ${tag.parameterString}>
[#if tag.cols?size>0]
<thead class="grid-head">
[#assign filterable = (tag.filterable=="true" || tag.filters?size > 0 )]
[#if filterable]
<tr>
[#list tag.cols as cln]
  [#if cln.type??]
  <th class="grid-select-top" [#if cln.width??] width="${cln.width}"[/#if]>[@b.submit id="${tag.id}_filter_submit" class="grid-filter-submit" value=""/]</th>
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
<th [#if !filterable && cln.width??] width="${cln.width}"[/#if] [#if cln.type??]class="grid-select-top" [#elseif tag.isSortable(cln)]class="grid-head-sortable" id="${cln.parameters['sort']!(tag.defaultSort(cln.property))}"[/#if]>[#t/]
  [#if cln.type??][#if cln.type=="checkbox"]<input type="${cln.type}" name="${cln.boxname}box" onclick="bg.ui.grid.toggleAll(event)" title="${b.text('action.selectall')}"/>[/#if] [#t/]
  [#else]${cln.title}[/#if][#t/]
</th>
[/#list]
</tr>

</thead>
[/#if]

<tbody id="${tag.id}_data">${tag.body}</tbody>
</table>
</div>
[#if tag.hasbar]
[#if tag.notFullPage]
<div class="grid-empty border-bottom-1px border-blue" id="${tag.id}_empty"></div>
[/#if]
<div id="${tag.id}_bar2"  class="grid-bar"></div>
[/#if]
</div>
<script type="text/javascript">
  page_${tag.id} = bg.page("${request.requestURI}","${tag.parameters['target']!""}");
  page_${tag.id}.setTarget("${tag.parameters['target']!""}",'${tag.id}').action("${request.requestURI}").addParams('${b.paramstring}').orderBy("${Parameters['orderBy']!('null')}");
  bg.ui.grid.init('${tag.id}',page_${tag.id});
  [#if tag.hasbar]
  bar=new bg.ui.gridbar(['${tag.id}_bar1','${tag.id}_bar2'],'${(tag.parameters['title']?default(''))?replace("'","\"")}');
  bar.pageId('${tag.id}')
  [#if tag.pageable]
  page_${tag.id}.pageInfo(${tag.items.pageIndex},${tag.items.pageSize},${tag.items.totalItems});
  bar.addPage(page_${tag.id},[#if tag.parameters['fixPageSize']??][][#else]null[/#if],{${b.text('page.description')}});
  [/#if]
  [#if tag.hasbar && tag.notFullPage]bg.ui.grid.fillEmpty('${tag.id}_empty',[#if tag.pageable]${tag.items.pageSize}[#else]10[/#if],${tag.items?size},'${tag.emptyMsg!b.text("page.noData")}');[/#if]
  [#if tag.var??]action=bar.addEntityAction('${tag.var}',page_${tag.id});action.renderAs('struts');[/#if]
  ${tag.bar!}
  [/#if]
  [#-- refresh interval --]
  [#if tag.refresh??]
  if(typeof ${tag.id}_timer !="undefined"){clearTimeout(${tag.id}_timer)}
  var ${tag.id}_timer=setTimeout(function(){if(document.getElementById('${tag.id}')) page_${tag.id}.goPage()},${tag.refresh}*1000);
  [/#if]
  [#if tag.overflow??]
  function adjustGrid(){
     $('#${tag.id}_content').css("width", "0px");
     $('#${tag.id}_content').css("width", $("#${tag.id}_div").outerWidth(true));
     $('#${tag.id}_content').css("overflow", "auto");
  }
  $(document).ready(function() {
    adjustGrid();
  });
  window.onresize = adjustGrid
  [/#if]
</script>
