[#ftl]
[@b.xhtmlhead/]
[@sj.head /]

[#if Parameters['connection-scripts']??]
[#assign connection_scripts=Parameters['connection-scripts']/]
[#else]
[#assign connection_scripts]
driverClassName=
url=
username=
password=
[/#assign]
[/#if]

<div id="t1">
<form name="form1" method="post" action="${base}/database/connection-test.action">
  <table >
	<tr>
	  <td><textarea name="connection-scripts" cols="80" rows="4" id="scripts">${connection_scripts!}</textarea></td>
	</tr>
	<tr>
	  <td align="right"><input type="submit" name="Submit" value="-提交-"/></td>
	</tr>
  </table>
</form>
</div>
[#include "/template/foot.ftl"/]
