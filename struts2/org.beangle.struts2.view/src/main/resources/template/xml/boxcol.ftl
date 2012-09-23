[#ftl/]
<td class="gridselect"[#if tag.id??] id="${tag.id}"[/#if]${tag.parameterString} [#if !tag.parameters['width']??]width="25px"[/#if]>
[#if tag.display]<input class="box" name="${tag.boxname}" value="${tag.value}"[#if tag.checked] checked="checked"[/#if] type="${tag.type}" title="select me"/>[/#if]${tag.body}
</td>