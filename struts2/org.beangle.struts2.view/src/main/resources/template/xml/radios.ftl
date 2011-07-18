[#ftl]
[#if tag.label??]<label for="${tag.id}">${tag.label}:</label>[/#if]
[#list tag.radios as radio]
<input type="radio" style="width:10px" id="${radio.id}" name="${tag.name}" value="${radio.value}" ${tag.parameterString} [#if (tag.value!"")==radio.value]checked="checked"[/#if]/>
<label for="${radio.id}">&nbsp;&nbsp;${radio.title!}</label>
[/#list]