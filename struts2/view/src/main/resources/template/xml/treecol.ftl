[#ftl/]
<td${tag.parameterString}>
<div class="tree-tier${tag.curObj.depth}" align="left">
[#if (tag.curObj.children?size==0)]<a href="#" class="tree-item"></a>
[#else]
<a href="#" class="tree-folder-open" id="${tag.curObj.indexno}_folder" onclick="toggleRows(this)" ></a>
[/#if]
[#if tag.body?length>0]${tag.body}[#elseif tag.property??]${(tag.value?html)!}[/#if]
</div>
</td>
