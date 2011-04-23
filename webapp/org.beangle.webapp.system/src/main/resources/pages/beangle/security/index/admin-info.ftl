[#ftl]
[@b.head title="Security Dashboard" /]
[#include "../nav.ftl"/]
[@b.css href="panel.css"/]
[@b.messages/]
<div id="categoryDiv" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('categoryDiv');">用户类别列表</a>
	</h2>
	<div class="modulebody">
	<ul>
	[#list categories as category]
	<li>${category.name}</li>
	[/#list]
	</ul>
	</div>
</div>
<div id="adminDiv" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('adminDiv');">超级管理员(加入时间)列表</a>
	</h2>
	<div class="modulebody">
	<ul>
	[#list adminUsers as adminUser]
	<li>
	${adminUser.user.name}(${adminUser.user.fullname}) ${(adminUser.createdAt?string("yyyy-MM-dd"))!}
	</li>
	[/#list]
	</ul>
	</div>
</div>

[@b.foot/]