[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
[#include "scope.ftl"/]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
[@b.toolbar title="security.resource.info"]bar.addClose();[/@]
[@b.form action="!save"]
<table width="80%" class="formTable" align="center">
	<tr class="thead">
	 <td  colspan="2">${b.text("security.resource.info")}</td>
	</tr>
	<tr>
	 <td class="title" width="25%" id="f_name">${b.text("common.name")}<font color="red">*</font>:</td>
	 <td >
	  <input type="text" name="resource.name" value="${resource.name!}" style="width:200px;" />
	 </td>
	</tr>
	<tr>
	 <td class="title" id="f_title">标题<font color="red">*</font>:</td>
	 <td >
	  <input type="text" name="resource.title" value="${resource.title!}" style="width:200px;" />
	 </td>
	</tr>
	<tr>
	 <td class="title" id="f_description">${b.text("common.description")}:</td>
	 <td >
		<input type="text" name="resource.description" value="${resource.description!}"/>
	 </td>
	</tr>
	<tr>
	 <td class="title" >可见范围:</td>
	 <td>
		[@resourceScopeRadio resource.scope/]
	 </td>
	</tr>
	<tr>
		<td class="title">${b.text("common.status")}:</td>
		<td>
			<input type="radio" id="status_enabled" name="resource.enabled" value='1' [#if resource.enabled!true]checked="checked"[/#if]>
			<label for="status_enabled">${b.text("action.activate")}</label>
			<input type="radio" id="status_disabled" name="resource.enabled" value='0' [#if !(resource.enabled!true)]checked="checked"[/#if]>
			<label for="status_disabled">${b.text("action.freeze")}</label>
		</td>
	</tr>
	 <tr>
	 <td class="title">适用用户:</td>
	 <td>
	  [#list categories as category]
	  <input name="categoryIds" value="${category.id}" type="checkbox" id="categoryIds${category.id}" [#if resource.categories?seq_contains(category)]checked="checked"[/#if]/]
		<label for="categoryIds${category.id}" >${category.name}</label>
	  [/#list]
	</tr>
	<tr>
	<td class="title" >数据限制对象:</td>
	<td >
	 <table>
	  <tr>
		<td>
		<select name="RestrictObjects" multiple="multiple" size="10" style="width:200px" onDblClick="JavaScript:bg.select.moveSelected(this.form['RestrictObjects'], this.form['SelectedRestrictObjects'])" >
		 [#list restrictObjects as object]
		  <option value="${object.id}">${object.name}</option>
		 [/#list]
		</select>
		</td>
		<td  valign="middle">
		<br/><br/>
		<input onclick="JavaScript:bg.select.moveSelected(this.form['RestrictObjects'], this.form['SelectedRestrictObjects'])" type="button" value="&gt;"/>
		<br/><br/>
		<input onclick="JavaScript:bg.select.moveSelected(this.form['SelectedRestrictObjects'], this.form['RestrictObjects'])" type="button" value="&lt;"/>
		<br/>
		</td>
		<td  class="normalTextStyle">
		<select name="SelectedRestrictObjects" multiple="multiple" size="10" style="width:200px;" onDblClick="JavaScript:bg.select.moveSelected(this.form['SelectedRestrictObjects'], this.form['RestrictObjects'])">
		 [#list resource.entities! as entity]
		  <option value="${entity.id}">${entity.name}</option>
		 [/#list]
		</select>
		</td>
	  </tr>
	 </table>
	</td>
	</tr>
	<tr class="tfoot">
	 <td colspan="6" >
		<input type="hidden" name="resource.id" value="${(resource.id)!}" style="width:200px;" />
		<input type="hidden" name="restrictObjectIds" value=""/>
		[@b.submit onsubmit="validateResource" value="action.submit"/]
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
	 </td>
	</tr>
</table>
[/@]
<script>
function validateResource(form){
	form['restrictObjectIds'].value = bg.select.getValues(form.SelectedRestrictObjects);
	var a_fields = {
		 'resource.title':{'l':'标题', 'r':true, 't':'f_title'},
		 'resource.name':{'l':'${b.text("common.name")}', 'r':true,'t':'f_name'}
	};
	var v = new validator(form, a_fields, null);
	return v.exec();
}
</script>
[@b.foot/]