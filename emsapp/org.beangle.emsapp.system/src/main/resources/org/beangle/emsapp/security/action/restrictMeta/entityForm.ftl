[#ftl/]
[@b.form theme="list" action="!saveEntity"]
	[@b.textfield name="entity.name" value=entity.name! label="common.name"/]
	[@b.textfield name="entity.remark" value=entity.remark! label="common.remark"/]
	[@b.select2 label="数据限制域" name1st="RestrictEntities"  name2nd="fieldId" required="true" items1st=fields items2nd=entity.fields/]
	[@b.formfoot]
		<input type="hidden" name="entity.id" value="${(entity.id)!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
	[/@]
[/@]
	