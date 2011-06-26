[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
[#include "scope.ftl"/]
[@b.toolbar title="新建/修改资源"]bar.addBack();[/@]
<div style="width:850px">
[@b.form action="!save" title="security.resource.info" theme="list"]
	[@b.textfield name="resource.name" required="true" label="common.name" value="${resource.name!}" maxlength="50" comment="资源名称唯一"/]
	[@b.field label="能否独立访问" required="true"]
		<input type="radio" id="needParams_enabled" name="resource.needParams" value='1' [#if resource.needParams]checked="checked"[/#if]>
		<label for="needParams_enabled">需要其它参数</label>
		<input type="radio" id="needParams_disabled" name="resource.needParams" value='0' [#if !(resource.needParams)]checked="checked"[/#if]>
		<label for="needParams_disabled">是，访问时不需要额外参数</label>
	[/@]
	[@b.textfield name="resource.title" required="true" label="标题" value="${resource.title!}" maxlength="50"/]
	[@b.textfield name="resource.remark" label="common.remark" value="${resource.remark!}" maxlength="50"/]
	[@b.field label="可见范围" required="true"][@resourceScopeRadio resource.scope/][/@]
	[@b.field label="common.status" required="true"]
		<input type="radio" id="status_enabled" name="resource.enabled" value='1' [#if resource.enabled!true]checked="checked"[/#if]>
		<label for="status_enabled">${b.text("action.activate")}</label>
		<input type="radio" id="status_disabled" name="resource.enabled" value='0' [#if !(resource.enabled!true)]checked="checked"[/#if]>
		<label for="status_disabled">${b.text("action.freeze")}</label>
	[/@]
	[@b.field label="适用用户" required="true"]
		[#list categories as category]
		<input name="categoryIds" value="${category.id}" type="checkbox" id="categoryIds${category.id}" [#if resource.categories?seq_contains(category)]checked="checked"[/#if]/>
		<label for="categoryIds${category.id}" >${category.name}</label>
		[/#list]
	[/@]
	[@b.select2 label="数据限制对象" name1st="RestrictEntities" name2nd="SelectedRestrictEntities" items1st=restrictEntities items2nd=resource.entities/]
	[@b.formfoot]
		[@b.submit value="action.submit" onsubmit="validateResource"/]
		[@b.redirectParams/]
		<input type="reset" value="${b.text('action.reset')}"/>
		<input type="hidden" name="resource.id" value="${(resource.id)!}" />
		<input type="hidden" name="restrictEntityIds" value=""/>
	[/@]
[/@]
</div>
<script>
function validateResource(form){
	form['restrictEntityIds'].value = bg.select.getValues(form.SelectedRestrictEntities);
}
</script>
[@b.foot/]