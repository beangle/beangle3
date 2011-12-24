[#ftl/]
[@b.head/]
<h3>Action information - ${actionName}</h3>

<table>
	<tr><td>Action name:</td><td>${actionName}</td></tr>
	<tr><td>Namespace:</td><td> ${namespace}</td></tr>
	<tr><td>Action class:</td><td> ${config.className}</td></tr>
	<tr><td>Action method:</td><td> [#if config.methodName?exists]${config.methodName}[/#if]</td></tr>
	<tr><td>Parameters:</td><td> [#list config.params?keys as p]${p}[/#list]</td></tr>
	<tr><td>Default location:</td><td> <a href="${base}${namespace}/${actionName}.${extension}">${base}${namespace}/${actionName}.${extension}</a></td></tr>
</table>

[@b.tabs]
	[@b.tab label="Results"]
	    <table width="100%">
    	<tr><th>Name</th><th>Type</th><th>Parameters</th></tr>
    	[#list config.results.values() as r]
    		<tr [#if r_index%2 gt 0]class="b"[#else]class="a"[/#if]>
    		<td>${r.name}</td>
    		<td>${r.className}</td>
    		<td>
    		[#list r.params.keySet() as p]
    			${p} = ${r.params[p]}<br>
    		[/#list]
    		</td>
    		</tr>
    	[/#list]
    </table>
	[/@]
	[@b.tab label="Exception Mappings"]
    <table width="100%">
        <tr><th>Name</th><th>Exception Class Name</th><th>Result</th><th>Parameters</th></tr>
        [#list config.exceptionMappings as e]
        	<tr [#if e_index%2 gt 0]class="b"[#else]class="a"[/#if]>
    			<td>${e.name}</td>
    			<td>${e.exceptionClassName}</td>
    			<td>${e.result}</td>
    		    <td>
    		        [#list e.params.keySet() as p]
    			        ${p} = ${e.params[p]}<br>
    		        [/#list]
    		    </td>
    		</tr>
    	[/#list]
    </table>
	[/@]
	[@b.tab label="Interceptors"]
    <table width="100%">
        <tr><th>Name</th><th>Type</th></tr>
        [#list config.interceptors as i]
        	<tr [#if i_index%2 gt 0]class="b"[#else]class="a"[/#if]>
    			<td>${action.stripPackage(i.interceptor.class)}</td>
    			<td>${i.interceptor.class.name}</td>
    		</tr>
    	[/#list]
    </table>
	[/@]
	[@b.tab label="Properties"]
	<table width="100%">
        <tr><th>Name</th><th>Type</th></tr>
        [#list properties as prop]
        	<tr [#if prop_index%2 gt 0]class="b"[#else]class="a"[/#if]>
    			<td>${prop.name}</td>
    			<td>${prop.propertyType.name}</td>
    		</tr>
    	[/#list]
    </table>
	[/@]
	[@b.tab label="Validators"  href="!validators?clazz=${config.className}&context=${namespace}"/]
[/@]
[@b.foot/]