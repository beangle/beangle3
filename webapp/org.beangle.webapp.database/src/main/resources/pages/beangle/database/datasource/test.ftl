[#ftl]
[#if exceptionStack??]
<div >${exceptionStack}</div>
[#else]
<table >
<tr><td colspan="2"><em>Driver Information</em></td></tr>
[#list driverinfo?keys?sort as dk]
<tr>
<td>${dk}:</td><td>${driverinfo[dk]}</td>
</tr>
[/#list]
<tr><td colspan="2"><em>Database Information</em></td></tr>
[#list dbinfo?keys?sort as dk]
<tr>
<td>${dk}:</td><td>${dbinfo[dk]}</td>
</tr>
[/#list]
<tr><td colspan="2"><em>JDBC Information</em></td></tr>
[#list jdbcinfo?keys?sort as dk]
<tr>
<td>${dk}:</td><td>${jdbcinfo[dk]}</td>
</tr>
[/#list]
</table>
[/#if]