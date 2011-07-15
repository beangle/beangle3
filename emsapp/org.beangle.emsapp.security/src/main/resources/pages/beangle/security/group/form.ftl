[#ftl]
[@b.head/]
[@b.toolbar title="ui.groupInfo"]bar.addBack();[/@]
[@b.form action="!save" title="ui.groupInfo" theme="list"]
	[@b.textfield name="userGroup.name" label="common.name" value="${userGroup.name!}" required="true" maxlength="50"/]
	[@b.radios label="common.status"  name="userGroup.enabled"value=userGroup.enabled items="1:action.activate,0:action.freeze"/]
	[@b.field label="适用身份" required="true"]
		<select  name="userGroup.category.id" style="width:100px;" >
			[#list categories as category]
			<option value="${category.id}" [#if category.id=(userGroup.category.id)!(0)]selected="selected"[/#if]>${category.name}</option>
			[/#list]
		</select>
		[#if userGroup.id??][@b.a target="_blank" href="restriction!info?forEdit=1&restrictionType=group&restriction.holder.id=${userGroup.id}"]数据级权限[/@][/#if]
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