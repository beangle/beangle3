[#ftl]
[@b.messages slash="4"/]
<form id="${tag.id}" [#if !tag.parameters['cssClass']??]class="listform"[/#if] name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString} 
[#if tag.validate=="true" || tag.onsubmit??]onsubmit="return onsubmit${tag.id}()"[/#if]>
[#if Parameters['params']??]<input name="params" type="hidden" value="${Parameters['params']?html}" />[/#if]
<fieldset[#if !tag.title??] class="emptytitle"[/#if]>
[#if tag.title??]<legend>${tag.title}</legend>[/#if]
<ol>${tag.body}</ol>
</fieldset>
</form>
[#if (tag.validate!"")=="true" ||tag.onsubmit??]
<script>
bg.ui.load("validity");
function onsubmit${tag.id}(){
  var res=null;
[#if (tag.validate!"")=="true"]
  jQuery.validity.start();
  ${tag.validity}
  res = jQuery.validity.end().valid;
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