[#if tag.option??][#assign optionTemplate=tag.option?interpret][/#if]
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}:</label>[/#if]
[#assign selected=false/]
<select id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}"${tag.parameterString}>
${tag.body}
[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#rt/]
[#if tag.items??]
[#list tag.items as item][#assign optionText][#if tag.option??][@optionTemplate/][#else]${item[tag.valueName]!}[/#if][/#assign]
<option value="${item[tag.keyName]}" title="${optionText!}"[#if !selected && tag.isSelected(item)] selected="selected" [#assign selected=true/][/#if]>${optionText!}</option>
[/#list]
[#if tag.value?? && !selected]<option value="${tag.value}" selected="selected">${tag.value}</option>[/#if]
[/#if]
</select>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
</li>
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
