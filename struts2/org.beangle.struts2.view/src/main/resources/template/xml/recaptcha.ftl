[#ftl/]
<script type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>
<div id="${tag.id}">
[#if tag.theming=='onlyimage']
<div id="recaptcha_image"></div>
<input type="text" name="recaptcha_response_field" id="recaptcha_response_field" size="30" />
<a href="javascript:Recaptcha.reload()" title="Refresh">
<img width="25" height="17" id="recaptcha_reload" src="http://www.google.com/recaptcha/api/img/red/refresh.gif" alt="Get a new challenge">
</a>
[#else]
${tag.body}
[/#if]
</div>
<script type="text/javascript">
jQuery(document).ready(function () {
	Recaptcha.create("${tag.publickey}","${tag.id}",{theme: "${(tag.buildinTheming&&tag.body?length==0)?string(tag.theming,'custom')}",callback: Recaptcha.focus_response_field});
});
</script>