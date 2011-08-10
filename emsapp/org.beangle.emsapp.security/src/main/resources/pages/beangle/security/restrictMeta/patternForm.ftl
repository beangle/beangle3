[#ftl]
[#assign labInfo]${b.text("security.restrictionPattern.info")}[/#assign]
[@b.toolbar title=labInfo]
bar.addBack("${b.text("action.back")}");
[/@]
[@b.form action="!savePattern" theme="list" title="数据限制模式"]
	[@b.textfield label="描述" name="pattern.remark" value="${pattern.remark!}" maxlength="50" required="true"/]
	[@b.textarea label="restrictPattern.content" name="pattern.content" required="true" value="${pattern.content!}" maxlength="400" rows="4" style="width:500px;"/]
	[@b.field label="限制参数"][#list pattern.entity.fields as field]${field.name}(${field.remark!})[#if field_has_next],[/#if][/#list][/@]
	[@b.formfoot]
		<input type="hidden" name="pattern.id" value="${(pattern.id)!}"/>
		<input type="hidden" name="pattern.entity.id" value="${(pattern.entity.id)!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
	[/@]
</table>
[/@]