[#ftl]
[@b.toolbar title="修改参数信息"]bar.addBack("${b.text("action.back")}");[/@]
[@b.form action="!saveField" title="数据限制参数" theme="list"]
	[@b.textfield label="common.name" name="field.name" value="${field.name!}" required="true" maxLength="50"/]
	[@b.textfield label="标题" name="field.remark" value="${field.remark!}" maxLength="50"/]
	[@b.textfield label="引用类型" name="field.source" value="${(field.source)!}" style="width:400px;" maxLength="100" /]
	[@b.textfield label="主键属性" name="field.keyName" value="${(field.keyName)!}" maxLength="40"/]
	[@b.textfield label="其它显示属性" name="field.propertyNames" value="${(field.propertyNames)!}" maxLength="100"  /]
	[@b.field label="是否允许多值" required="true"]
	  <input type="checkbox" name="field.multiple" value="1" [#if field.multiple]checked="checked"[/#if] />是
	  <input type="checkbox" name="field.multiple" value="0" [#if !field.multiple]checked="checked"[/#if] />否
	[/@]
	[@b.field label="restriction.entities" required="true"]
		[@b.select2 name1st="Entitys" name2nd="SelectedEntity" items1st=entities items2nd=field.entities /]
	[/@]
	[@b.formfoot]
		<input type="hidden" name="entityIds" value=""/>
		<input type="hidden" name="field.id" value="${(field.id)!}" style="width:200px;" />
		[@b.submit value="action.submit" onsubmit="validateField" /]
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" />
	[/@]
</table>
[/@]
<script>
function validateField(form){
	form['entityIds'].value = bg.select.getValues(form.SelectedEntity);
}
</script>