[#ftl/]
<div class="grid">
<div>[@s.actionmessage theme="beangle"/][@s.actionerror theme="beangle"/]</div>
[#if tag.hasbar]<div id="${tag.id}_bar1" class="gridbar"></div>[/#if]

<table id="${tag.id}" class="gridtable" [#list parameters?keys as k] ${k}="${parameters[k]}"[/#list]>
<thead class="gridhead">
<tr>
[#list tag.cols as cln]
<th [#if cln.width??]width="${cln.width}" [/#if][#if cln.type??]><input type="${cln.type}" name="${cln.boxname}box" onclick="bg.input.toggleCheckBox(document.getElementsByName('${cln.boxname}'),event)" title="select all"/>[#else][#if tag.isSortable(cln)]class="gridhead-sortable" id="${cln.parameters['sort']!(tag.defaultSort(cln.property))}"[/#if]>${cln.name}[/#if]</th>
[/#list]
</tr>
</thead>
<tbody id="${tag.id}_data">
${nested_body!}
</tbody>
</table>

[#if tag.hasbar]
[#if tag.pageable && tag.notFullPage]
<div class="gridempty" id="${tag.id}_empty"></div>
[/#if]
<div id="${tag.id}_bar2"  class="gridbar"></div>
[/#if]
<script type="text/javascript">
	bg.ui.grid.init('${tag.id}',"${Parameters['orderBy']!('null')}");
[#if tag.hasbar]
	bar=new bg.ui.gridbar(['${tag.id}_bar1','${tag.id}_bar2'],'${(parameters['title']?default(''))?replace("'","\"")}');
	bar.pageId('${tag.id}').target("${parameters['target']!""}").action("${request.requestURI}").paramstring('${b.paramstring}');
	[#if tag.pageable]
	bar.addPage(${tag.items.pageNo},${tag.items.pageSize},${tag.items.total}[#if !parameters['fixPageSize']??],[10,15,20,25,30,50,70,90,100,150,300,1000][/#if]);
	[#if tag.notFullPage]bg.ui.grid.fillEmpty('${tag.id}_empty',${tag.items.pageSize},${tag.items?size});[/#if]
	[/#if]
	[#if tag.var??]action=bar.addEntityAction('${tag.var}');[/#if]
	${tag.bar!}
[/#if]
</script>
</div>