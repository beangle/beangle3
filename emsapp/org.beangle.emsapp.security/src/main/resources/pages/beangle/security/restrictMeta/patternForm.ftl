[#ftl]
[#assign labInfo]${b.text("security.restrictionPattern.info")}[/#assign]
[@b.toolbar title=labInfo]
bar.addBack("${b.text("action.back")}");
[/@]
[@b.form action="!savePattern" theme="list" title="数据限制模式"]
	[@b.textfield label="描述" name="pattern.remark" value="${pattern.remark!}" maxlength="50" required="true"/]
	[@b.textarea label="模式" name="pattern.content" required="true" value="${pattern.content!}" maxlength="400" rows="4" style="width:500px;"/]
	[@b.field required="true" label="entity.restrictEntity"]
			<select name="pattern.entity.id"  style="width:200px">
			 [#list entities as entity]
			  <option value="${entity.id}" [#if entity.id=(pattern.entity.id)!(0)]selected="selected"[/#if]>${entity.name}</option>
			 [/#list]
			</select>
	[/@]
	[@b.formfoot]
		<input type="hidden" name="pattern.id" value="${(pattern.id)!}" style="width:200px;" />
		[@b.submit value="action.submit" /]
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
	[/@]
</table>
[/@]