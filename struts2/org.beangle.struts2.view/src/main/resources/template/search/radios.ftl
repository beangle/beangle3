[#ftl]
[#assign moreThan2=(tag.radios?size)>2/]
<tr><td class="search-item" [#if moreThan2]style="text-align:left"[/#if]>[#if tag.label??]<label for="${tag.id}">${tag.label}:</label>[#if moreThan2]<br/>[/#if][/#if]
[#list tag.radios as radio]
[#if moreThan2]&nbsp;&nbsp;&nbsp;&nbsp;[/#if]
<input type="radio" id="${radio.id}" style="width:10px" name="${tag.name}" value="${radio.value}" ${tag.parameterString} [#if (tag.value!"")==radio.value]checked[/#if]/>
<label for="${radio.id}">${radio.title!}</label>[#if moreThan2]<br/>[/#if]
[/#list]
</td></tr>