[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
[@b.messages/]
[@b.module title="代码类别列表" ]
	[@b.form action="!saveCategory"]
		<ul>
		[#list categories as category]
		<li>[@b.textfield name="${category.id}_codeCategory.name" style="width:80px" value=category.name/]&nbsp;<input type="hidden" name="${category.id}_codeCategory.id" value="${category.id}"/>
		[@b.a href="!removeCategory?codeCategory.id=${category.id}"]<img style="border:0px" src="${b.theme.iconurl('actions/edit-delete.png')}"/>[/@]
		</li>
		[/#list]
		</ul>
		<label for="newCategoryInput">输入新的代码类别:</label><input name="newCodeCategory.name" style="width:100px" id="newCategoryInput"/>
		[@b.submit value="提交"/]
	[/@]
[/@]
[@b.foot/]