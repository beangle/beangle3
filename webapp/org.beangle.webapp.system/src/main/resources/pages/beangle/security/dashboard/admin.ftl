[#ftl]
[@b.xhtmlhead title="Security Dashboard"]
<link href="${base}/static/themes/default/css/panel.css" rel="stylesheet" type="text/css"/>
[/@]
<body class="autoadapt">
<div>[@b.messages/]</div>
<div id="categoryDiv" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('categoryDiv');">用户类别列表</a>
	</h2>
	<div class="modulebody">
	[#list categories as category]
	<li>${category.name}&nbsp;<a href="?removeCategoryId=${category.id}"><img style="border:0px" src="${base}/static/images/action/delete.gif"/></a></li>
	[/#list]
	<form name="dashboard!admin.action" method="post">
		<label for="newCategoryInput">输入新的用户类别:</label><input name="newCategory" style="width:100px" id="newCategoryInput"/>
		<input type="submit" value="提交"/>
	</form>
	</div>
</div>
<div id="adminDiv" class="module expanded">
	<h2 class="header">
		<a href="#" class="toggle" onclick="_wi_tm('adminDiv');">超级管理员(加入时间)列表</a>
	</h2>
	<div class="modulebody">
	[#list adminUsers as adminUser]
	<li>
	${adminUser.user.name}(${adminUser.user.fullname}) ${(adminUser.createdAt?string("yyyy-MM-dd"))!}
	&nbsp;<a href="?removeAdminId=${adminUser.id}"><img  style="border:0px" src="${base}/static/images/action/delete.gif"/></a>
	</li>
	[/#list]
	<form name="dashboard!admin.action" method="post">
		<label for="newAdminInput">输入用户名，加入超级管理员:</label><input name="newAdmin" style="width:100px" id="newAdminInput"/>
		<input type="submit" value="提交"/>
	</form>
	</div>
</div>
</body>
</html>