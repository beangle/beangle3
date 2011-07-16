[#ftl/]
<div id="${tag.id}">
<ul>
</ul>
${tag.body}
</div>
<script>
jQuery(document).ready(function () { 
	var tabs = [];
	[#list tag.tabs as tab]
	tabs.push({id:"${tab.id}",label:"${tab.label}",href:"[#if tab.target??]#${tab.target}[#elseif tab.href??]${tab.href}[/#if]"});
	[/#list]
	jQuery('#${tag.id}').data('taboptions', tabs);
	jQuery.struts2_jquery.bind(jQuery('#${tag.id}'),{jqueryaction:"tabbedpanel",id:"${tag.id}"});
});
</script>