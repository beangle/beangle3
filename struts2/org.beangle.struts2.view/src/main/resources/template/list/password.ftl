[#ftl]
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]
${tag.label}:</label>[/#if]<input type="password" id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}" [#rt/]
 maxlength="${tag.maxlength}" ${tag.parameterString} /> [#rt/]
[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]</li>
[#if tag.showStrength='true']
<script>
bg.ui.load("jquery.pstrength");
jQuery(document).ready(function() { jQuery('#${tag.id}').pstrength({minChar:${tag.minlength}}); });
</script>
[/#if]
