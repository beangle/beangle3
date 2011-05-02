[#ftl]
[#--<script  type="text/javascript" src="${base}/static/scripts/jquery.validity.js"></script>
<link href="/demo/static/themes/default/jquery.validity.css" rel="stylesheet" type="text/css" />--]
<form id="${tag.id}" [#if !tag.parameters['cssClass']??]class="listform"[/#if] name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString} 
[#if tag.validate=="true" || tag.onsubmit??]onsubmit="return onsubmit${tag.id}()"[/#if]>
[#if tag.title??]
<fieldset>
<legend>${tag.title}</legend>
<ol>${tag.body}</ol>
</fieldset>
[#else]
<ol>${tag.body}</ol>
[/#if]
</form>
[#if (tag.validate!"")=="true" ||tag.onsubmit??]
<script>
bg.ui.loadResource("validity");
function onsubmit${tag.id}(){
	var res=null;
	[#if tag.onsubmit??]try{res=eval("${tag.onsubmit}");}catch(e){alert(e);return false}[/#if]
	[#if (tag.validate!"")=="true"]
	jQuery.validity.start();
	${tag.validity}
	res = $.validity.end().valid;
	[/#if]
	return res;
}
$(function() {
$("#${tag.id}").validity(function() {
${tag.validity}
});
});

</script>
[/#if]