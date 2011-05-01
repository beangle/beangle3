[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
[#include "scope.ftl"/]
[@b.toolbar title="新建/修改资源"]bar.addBack();[/@]
<br/>
<div style="width:850px">
[@b.form action="!save" title="security.resource.info" theme="edit"]
[@b.textfield name="resource.name" required="true" label="common.name" value="${resource.name!}" check="maxLength(100)" comment="资源名称唯一"/]
[@b.textfield name="resource.title" required="true" label="标题" value="${resource.title!}" check="maxLength(50)" /]
[@b.textfield name="resource.remark" label="common.remark" value="${resource.remark!}"/]
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
[@b.field label="数据限制对象" required="true"]
		<select name="RestrictEntities" multiple="multiple" size="10" style="width:200px" onDblClick="JavaScript:bg.select.moveSelected(this.form['RestrictEntities'], this.form['SelectedRestrictEntities'])" >
		 [#list restrictEntities as entity]
		  <option value="${entity.id}">${entity.name}</option>
		 [/#list]
		</select>
		<input style="margin:auto" onclick="JavaScript:bg.select.moveSelected(this.form['RestrictEntities'], this.form['SelectedRestrictEntities'])" type="button" value="&gt;"/>
		<input style="vertical-align: middle;" onclick="JavaScript:bg.select.moveSelected(this.form['SelectedRestrictEntities'], this.form['RestrictEntities'])" type="button" value="&lt;"/>
		<select name="SelectedRestrictEntities" multiple="multiple" size="10" style="width:200px;" onDblClick="JavaScript:bg.select.moveSelected(this.form['SelectedRestrictEntities'], this.form['RestrictEntities'])">
		 [#list resource.entities! as entity]
		  <option value="${entity.id}">${entity.name}</option>
		 [/#list]
		</select>
[/@]
[@b.field]
	[@b.submit value="action.submit"/]
	<input type="hidden" name="resource.id" value="${(resource.id)!}" />
	<input type="hidden" name="restrictEntityIds" value=""/>
[/@]
[/@]
</div>
[@b.foot/]