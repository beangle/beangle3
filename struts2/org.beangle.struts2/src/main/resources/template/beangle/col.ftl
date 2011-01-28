[#ftl/]
<td[#list parameters?keys as k] ${k}="${parameters[k]}"[/#list]>[#if nested_body??]${nested_body!}[#elseif tag.property??]${tag.value!}[/#if]</td>