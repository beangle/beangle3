[#ftl/]
[#if tag.id??]
<div class="ui-widget" id="${tag.id}">
[#if tag.hasActionMessages()]
<div class="actionMessage">
	<div class="ui-state-highlight ui-corner-all"> 
		[#list tag.actionMessages as message]
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
		<span>${message!}</span>[#if message_has_next]<br/>[/#if]
		[/#list]
	</div>
</div>
[/#if]
[#if tag.hasActionErrors()]
<div class="actionError">
	<div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em;"> 
		[#list tag.actionErrors as message]
		<span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>
		<span>${message!}</span>[#if message_has_next]<br/>[/#if]
		[/#list]
	</div>
</div>
[/#if]
</div>
[#if tag.parameters['slash']??]
<script>
	//jQuery("${tag.id}").fadeOut("fast",function(){alert(1)});
	setTimeout(function(){var msgdiv=document.getElementById('${tag.id}');if(msgdiv) msgdiv.style.display="none";},${tag.parameters['slash']}*1000);
</script>
[/#if]
[/#if]