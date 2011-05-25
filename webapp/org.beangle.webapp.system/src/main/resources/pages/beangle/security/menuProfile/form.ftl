[#ftl]
[@b.head/]
[@b.toolbar title="菜单配置"]bar.addClose();[/@]
[@b.form action="!save" theme="list" title="info.module.detail"]
	[@b.textfield name="menuProfile.name" label="common.name" value="${menuProfile.name!}" required="true" maxlength="50"/]
	[@b.field label="entity.userCategory" required="true"]
		<select  name="menuProfile.category.id" style="width:100px;" >
			[#list categories as category]
				<option value="${category.id}"  [#if menuProfile.category??&&menuProfile.category.id==category.id]selected="selected"[/#if]>${category.name}</option>
			[/#list]
		</select>
	[/@]
	[@b.formfoot]
		[@b.redirectParams/]
		<input type="hidden" name="menuProfile.id" value="${menuProfile.id!}" style="width:200px;" />
		[@b.submit value="action.submit"/]&nbsp;
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
	[/@]
[/@]
[@b.foot/]