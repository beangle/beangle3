[#ftl/]
<td[#list tag.parameters?keys as k] ${k}="${tag.parameters[k]}"[/#list]>[#if nested_body??]${nested_body!}[#elseif tag.property??]${tag.value!}[/#if]</td>