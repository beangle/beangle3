<script type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>
<div id="${tag.id}"></div>
<script type="text/javascript">
	Recaptcha.create("${tag.publickey}","${tag.id}",{theme: "${tag.theming}",callback: Recaptcha.focus_response_field});
</script>