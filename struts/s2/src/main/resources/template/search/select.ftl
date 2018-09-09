[#if tag.option??][#assign optionTemplate=tag.option?interpret][/#if]
<tr><td class="search-item"><label for="${tag.id}">${tag.label}:</label>
[#assign selected=false/]
<select id="${tag.id}" name="${tag.name}"${tag.parameterString}>
${tag.body}
[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#rt/]
[#if tag.items??]
[#list tag.items as item][#assign optionText][#if tag.option??][@optionTemplate/][#else]${item[tag.valueName]!}[/#if][/#assign]
<option value="${item[tag.keyName]}"  title="${optionText!}"[#if !selected && tag.isSelected(item)] selected="selected" [#assign selected=true/][/#if]>${optionText!}</option>
[/#list]
[#if tag.value?? && !selected]<option value="${tag.value}" selected="selected">${tag.value}</option>[/#if]
[/#if]
</select></td></tr>
[#if !(tag.items??) && tag.href??]
<script type="text/javascript">
jQuery.ajax({
  url: "${tag.href}",
  headers:{"Accept":"application/json"},
  success: function(datas){
    var select = $("#${tag.id}")
    for(var i in datas){
      var data = datas[i], value = data.${tag.keyName}
      select.append('<option value="'+value+'" title="'+data.name+'">'+data.${tag.valueName}+'</option>');
    }
    [#if tag.value??]
    select.val("${tag.value}")
    [/#if]
  }
});
</script>
[/#if]
