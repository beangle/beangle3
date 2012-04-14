[#ftl/]
[@b.form theme="list" action="!saveEntity"]
	[@b.textfield name="entity.name" value=entity.name! label="common.name"/]
	[@b.textfield name="entity.remark" value=entity.remark! label="common.remark"/]
	[@b.formfoot]
		<input type="hidden" name="entity.id" value="${(entity.id)!}" />
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
	[/@]
[/@]
	