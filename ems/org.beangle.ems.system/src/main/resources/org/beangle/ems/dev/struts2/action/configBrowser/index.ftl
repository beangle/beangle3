<@b.head>
	<@b.css href="/struts2-config.css"/>
</@>
<div id="breadcrumbs">
	<table border="0" cellspacing="0" cellpadding="4" width="100%">
		<tr>
			<td> 
				Struts Configuration Browser
			</td>
		</tr>
	</table>
</div>
  
<table border="0" cellspacing="0" cellpadding="4" width="100%" id="main">
	<tr valign="top">
		<#if !hideNav?exists>
		<td id="leftcol" width="20%">
			<div id="navcolumn">
				<div class="toolgroup">
					<div class="label"><strong>Configuration</strong></div>
					<div class="body">
						<div><@b.a href="!index"  target="content">Actions</@></div>
						<div><@b.a href="!consts" target="content">Constants</@></div>
						<div><@b.a href="!beans" target="content">Beans</@></div>
						<div><@b.a href="!jars"  target="content">Jars</@></div>
					</div>
				</div>
				<div id="projecttools" class="toolgroup">
					<#if namespaces?exists>
					<div class="label"><strong>Namespaces</strong></div>
					<div class="body">
						<#foreach namespace in namespaces?sort><div><@b.a href="!actions?namespace=${namespace}" target="content"><#if namespace="">default<#else>${namespace}</#if></@></div></#foreach>
					</div>
					</#if>
				</div>
				<div class="toolgroup">
					<div class="body">
					<#if actionNames?exists><div class="label"><strong>Actions in <#if namespace == ""> default <#else> ${namespace} </#if></strong></div>
					<#foreach name in actionNames>
					<div><@b.a href="!config?namespace=${namespace}&actionName=${name}" target="content">${name}</@></div>
					</#foreach>
					</#if>
					</div>
				</div>
			</div>
		</td>
		</#if>
		<td>
			<div id="bodycol">
				<@b.div id="content" class="app" href="!actions?namespace=${Parameters['namespace']!}"/>
			</div>
		</td>
	</tr>
</table>