[#ftl]
[@b.head title="Security Dashboard" /]
[#include "../nav.ftl"/]
[@b.messages/]
[@b.module title="用户类别列表"]
	<ul>
	[#list categories as category]
	<li>${category.name}</li>
	[/#list]
	</ul>
[/@]

[@b.module title="超级管理员(加入时间)列表"]
	<ul>
	[#list adminUsers as adminUser]
	<li>
	${adminUser.user.name}(${adminUser.user.fullname}) ${(adminUser.createdAt?string("yyyy-MM-dd"))!}
	</li>
	[/#list]
	</ul>
[/@]
[@b.foot/]