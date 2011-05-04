[#ftl]
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString} [#if tag.validate=="true" || tag.onsubmit??]onsubmit="return onsubmit${tag.id}()"[/#if]>${tag.body}</form>
[#if (tag.validate!"")=="true" ||tag.onsubmit??]
<script>
bg.ui.load("validity");
function onsubmit${tag.id}(){
	var res=null;
[#if (tag.validate!"")=="true"]
	jQuery.validity.start();
	${tag.validity}
	res = $.validity.end().valid;
[/#if]
	if(false==res) return false;
	[#if tag.onsubmit??]
	var nativeOnsubmit${tag.id} = function(){${tag.onsubmit}}
	try{res=nativeOnsubmit${tag.id}();}catch(e){alert(e);return false;}
	[/#if]
	return res;
}
</script>
[/#if]