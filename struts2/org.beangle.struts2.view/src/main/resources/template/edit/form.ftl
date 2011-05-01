[#ftl]
<script  type="text/javascript" src="${base}/static/scripts/jquery.validity.js"></script>
<link href="/demo/static/themes/default/jquery.validity.css" rel="stylesheet" type="text/css" />
<form id="${tag.id}" [#if !tag.parameters['cssClass']??]class="editform"[/#if] name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString} [#if tag.validate=="true" || tag.onsubmit??]onsubmit="[#if tag.validate=="true"]validate${tag.id}();[/#if]${tag.onsubmit!}"[/#if]>
[#if tag.title??]
<fieldset>
<legend>${tag.title}</legend>
${tag.body}
</fieldset>
[#else]
${tag.body}
[/#if]
</form>
[#if (tag.validate!"")=="true"]
<script>
function validate${tag.id}(){
	jQuery.validity.start();
	${tag.validity}
	var result = $.validity.end();
	return result.valid;
}
</script>
[/#if]