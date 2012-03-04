[#ftl/]
[@b.head/]
[@b.toolbar title="代码元信息维护"]
	bar.addBack("${b.text("action.goback")}");
[/@]
[@b.messages/]
[@b.form action="!save" name="codeMetaForm" theme="list" title="代码元信息维护"]
	[@b.textfield name="codeMeta.name" value=codeMeta.name label="common.name" required="true" maxlength="50"/]
	[@b.textfield name="codeMeta.title" value=codeMeta.title label="common.title" required="true" maxlength="100"/]
	[@b.textfield name="codeMeta.className" value=codeMeta.className label="实体类名" required="true" maxlength="100"/]
	[@b.select name="codeMeta.category.id" items=categories value=(codeMeta.category.id)! empty="..." required="true" label="类别" style="width:100px"/]
	[@b.formfoot]
		<input type="hidden" name="codeMeta.id" value="${codeMeta.id!}"/>
		[@b.submit value="action.submit"/]
		<input type="button" onclick='reset()' value="${b.text("action.reset")}" class="buttonStyle"/>
	[/@]
[/@]
[@b.foot/]