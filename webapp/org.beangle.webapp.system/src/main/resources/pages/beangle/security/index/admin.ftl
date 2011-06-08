[#ftl]
[@b.head title="Security Dashboard" /]
[#include "../nav.ftl"/]
[@b.messages/]
[@b.module title="用户类别列表"]
	<ul>
	[#list categories as category]
	<li>${category.name}&nbsp;[@b.a href="!admin?removeCategoryId=${category.id}"]<img style="border:0px" src="${b.theme.iconurl('actions/edit-delete.png')}"/>[/@]</li>
	[/#list]
	</ul>
	[@b.form action="!admin"]
		<label for="newCategoryInput">输入新的用户类别:</label><input name="newCategory" style="width:100px" id="newCategoryInput"/>
		[@b.submit value="提交"/]
	[/@]
[/@]
[@b.foot/]