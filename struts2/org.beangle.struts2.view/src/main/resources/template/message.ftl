<#macro text name extra...>
	<#if (extra?size=0)>
		<@s.text name="${name}"/>
	<#elseif (extra?size=1)>
		<@s.text name="${name}">
		  <@s.param>${extra['arg0']}</@s.param>
		</@>
	</#if>
</#macro>
