<h4>Actions in <#if namespace == ""> default namespace <#else> ${namespace} </#if></h4>
<table>
	<tr>
		<td>
			<ul>
			<#list actionNames as name>
			<li><@b.a href="!config?namespace=${namespace}&actionName=${name}">${name}</@></li>
			</#list>
			</ul>
		</td>
		<td>
			<!-- Placeholder for namespace graph -->
		</td>
	</tr>
</table>
