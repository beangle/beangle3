[#ftl/]
<tr><td class="search-item">
[#if tag.label??]<label for="${tag.id}" class="title">${tag.label}:</label>[/#if]
<select id="${tag.id}"  name="${tag.name}"${tag.parameterString}><option value="">加载中...</option>${tag.body}</select>
</td></tr>
<script type="text/javascript">
	jQuery(function(){
		jQuery.post("${base}/dictionary/code.action",{type:"${(tag.type)!}",[#if tag.format??]format:"${tag.format}"[/#if]},function(data){
				jQuery("#${tag.id}").empty();
				[#if tag.empty??]jQuery("#${tag.id}").append("<option value=''>${tag.empty}</option>");[/#if]
				if(data!=""){
					jQuery("#${tag.id}").append(data);
					[#if tag.value??]
						jQuery("#${tag.id}").val("${tag.value}");
					[/#if]
				}
		},"text");
	});
</script>