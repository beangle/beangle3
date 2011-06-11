[#ftl]
[@b.head/]
[@b.toolbar title="ui.groupInfo"]bar.addBack();[/@]
[@b.form action="!save" title="ui.groupInfo" theme="list"]
	[@b.textfield name="userGroup.name" label="common.name" value="${userGroup.name!}" required="true" maxlength="50"/]
	[@b.field label="common.status" required="true"]
		<input value="1" id="group_enabled" type="radio" name="userGroup.enabled" [#if (userGroup.enabled)]checked="checked"[/#if]>
		<label for="group_enabled">${b.text("action.activate")}</label>
		<input value="0" id="group_disabled" type="radio" name="userGroup.enabled" [#if !(userGroup.enabled)]checked="checked"[/#if]>
		<label for="group_disabled">${b.text("action.freeze")}</label>
		[#if userGroup.id??][@b.a target="_blank" href="restriction!info?forEdit=1&restrictionType=group&restriction.holder.id=${userGroup.id}"]数据级权限[/@][/#if]
	[/@]
	[@b.field label="适用身份" required="true"]
		<select  name="userGroup.category.id" style="width:100px;" >
			[#list categories as category]
			<option value="${category.id}" [#if category.id=(userGroup.category.id)!(0)]selected="selected"[/#if]>${category.name}</option>
			[/#list]
		</select>
	[/@]
	[@b.textfield name="userGroup.remark" label="common.remark" value="${userGroup.remark!}" maxlength="50"/]
	[@b.formfoot]
		<input type="hidden" name="userGroup.id" value="${userGroup.id!}" />
		[@b.redirectParams/]
		[@b.submit value="action.submit"/]
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" />
	[/@]
[/@]
[@b.foot/]