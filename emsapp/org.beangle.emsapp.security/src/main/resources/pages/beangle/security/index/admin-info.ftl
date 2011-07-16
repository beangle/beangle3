[#ftl]
[@b.head title="Security Dashboard" /]
[#include "../nav.ftl"/]
[@b.messages/]
[@b.module title="用户类别列表"]
	<ul>
	[#list categories as category]
	<li>${category.title}</li>
	[/#list]
	</ul>
[/@]
[@b.foot/]