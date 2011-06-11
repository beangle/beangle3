[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
${(systemVersion.name)!} 版本信息
<hr/>
<ul>
	<li>版本：${(systemVersion.version)!}</li>
	<li>主版本：${(systemVersion.majorVersion)!}</li>
	<li>次版本：${(systemVersion.minorVersion)!}</li>
	<li>开发商：${(systemVersion.vendor)!}</li>
</ul>
[@b.foot/]