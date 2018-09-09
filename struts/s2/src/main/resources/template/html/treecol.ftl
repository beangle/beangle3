<td${tag.parameterString}>
<div class="tree-tier${tag.curObj.depth}">
[#if (tag.curObj.children?size==0)]<a href="#" class="tree-item"></a>
[#else]
<a href="#" class="tree-folder-open" id="${tag.curObj.indexno}_folder" onclick="bg.tabletree.toggle(this)" ></a>
[/#if]
[#if tag.body?length>0]${tag.body}[#elseif tag.property??]${(tag.value?html)!}[/#if]
</div>
</td>
