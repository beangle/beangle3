[#ftl]
[#if tag.label??]<label for="${tag.id}">${tag.label}:</label>[/#if]
[#list tag.radios as radio]
<input type="radio" id="${tag.id}" style="display:none" name="${tag.name}" value="${radio.value!?html}"${tag.parameterString} [#if tag.checked?? && radio.value!?html==tag.checked]checked[#elseif !tag.checked?exists && radio_index==(tag.radios?size-1)]checked[/#if]/>
<span class="radioClass"><img src="${base}/static/themes/default/images/radio1.gif"/>&nbsp;&nbsp;${radio.title!}</span>&nbsp;&nbsp;
[/#list]
<script type="text/javascript">
	if(jQuery.struts2_jquery.scriptCache["/static/scripts/radioInit.js"]){
		jQuery(".radioClass").cssRadio();
	}else{
		jQuery.struts2_jquery.require("/static/scripts/radioInit.js",function(){jQuery(".radioClass").cssRadio();},"${base}");
	}
	 jQuery("form .radioClass").parent().parent().find(":reset").click(function(){
	 	[#if tag.checked??]
		jQuery("input[name='${tag.name}']").each(function(){
			if(jQuery(this).val()==${tag.checked}){
				jQuery(this).next(".radioClass").click();
			}
		});
		[#else]
			jQuery("input[name='${tag.name}']:last").next(".radioClass").click();
		[/#if]
	 });
	[#if tag.checked??]
		jQuery("input[name='${tag.name}']").each(function(){
			if(jQuery(this).val()==${tag.checked}){
				jQuery(this).next(".radioClass").click();
			}
		});
	[#else]
		jQuery("input[name='${tag.name}']:last").next(".radioClass").click();
	[/#if]
</script>
