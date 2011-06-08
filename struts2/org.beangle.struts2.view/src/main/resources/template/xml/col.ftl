[#ftl/]
<td${tag.parameterString}>[#if tag.body?length>0]${tag.body}[#elseif tag.property??]${(tag.value?html)!}[/#if]</td>