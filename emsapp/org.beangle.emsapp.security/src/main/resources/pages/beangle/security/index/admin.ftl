[#ftl]
[@b.head title="Security Dashboard" /]
[#include "../nav.ftl"/]
[@b.messages/]
[@b.module title="用户类别列表"]
	[@b.form action="!admin"]
	<label>用户类别 名称和标题</label>
	<ul>
	[#list categories as category]
	<li>[@b.textfield name="${category.id}_category.name" style="width:80px" value=category.name/]&nbsp;[@b.textfield name="${category.id}_category.title" value=category.title/]&nbsp;
	[@b.a href="!admin?removeCategoryId=${category.id}"]<img style="border:0px" src="${b.theme.iconurl('actions/edit-delete.png')}"/>[/@]
	</li>
	[/#list]
	</ul>
		<label for="newCategoryInput">输入新的用户类别:</label><input name="newCategory" style="width:100px" id="newCategoryInput"/>
		[@b.submit value="提交"/]
	[/@]
[/@]
[@b.foot/]