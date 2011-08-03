[#ftl/]
<td${tag.parameterString}>
<div class="tier${tag.curObj.depth}" align="left">
[#if (tag.curObj.children?size==0)]<a href="#" class="doc"/>
[#else]
<a href="#" class="folder_open" id="${tag.curObj.code}_folder" onclick="toggleRows(this)" >   </a>
[/#if]
[#if tag.body?length>0]${tag.body}[#elseif tag.property??]${(tag.value?html)!}[/#if]
</div>
</td>