[#ftl]
[@b.head/]
[@b.toolbar title="info.moduleUpdate"]bar.addBack();[/@]
[#assign userMsg]${b.text("entity.menu")}[/#assign]
[#assign labelInfo]${b.text("ui.editForm",userMsg)}[/#assign]
[@b.form action="!save" title=labelInfo theme="list"]
	[@b.field label="菜单配置" required="true"]
		<select  name="menu.profile.id" style="width:150px;" >
		[#list profiles as profile]
		<option value="${profile.id}" [#if ((menu.profile.id)!0) ==profile.id]selected="selected"[/#if]>${profile.name}</option>
		[/#list]
		</select>
	[/@]
	[@b.textfield label="common.code" name="menu.code" value="${menu.code!}" required="true" maxLength="20" check="match('integer')" comment="数字格式,例如(0601)" /]
	[@b.textfield label="标题" name="menu.title" value="${menu.title!}" style="width:200px;" required="true" maxLength="50"/]
	[@b.textfield label="英文标题" name="menu.engTitle" value="${menu.engTitle!}" style="width:200px;"  required="true" maxLength="100" />
	[@b.field label="common.status" required="true"]
		<select  name="menu.enabled" style="width:100px;" >
			<option value="true" [#if menu.enabled]selected="selected"[/#if]>${b.text("action.activate")}</option>
			<option value="false" [#if !menu.enabled]selected="selected"[/#if]>${b.text("action.freeze")}</option>
		</select>
	[/@]
	[@b.textfield label="menu.entry"  name="menu.entry" value="${menu.entry!}" maxLength="100" /]
	[@b.field label="使用资源"]
		[@b.select2 name1st="Resources" name2nd="SelectedResource" items1st=resources?sort_by("name") items2nd= menu.resources?sort_by("name") /]
	[/@]
	[@b.textfield label="common.remark"  name="menu.remark" maxLength="50" value="${menu.remark!}"/]
	[@b.formfoot]
		[@b.submit value="action.submit"  onsubmit="validateMenu"/]&nbsp;
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		[@b.redirectParams/]
		<input type="hidden" name="menu.id" value="${menu.id!}" />
		<input type="hidden" name="resourceIds" />
	[/@]
[/@]
<script  type="text/javascript">
	function validateMenu(form){
		form.resourceIds.value = bg.select.getValues(form.SelectedResource);
		var codeValue=form['menu.code'].value;
		var le = codeValue.length;
		if(le%2!=0){
			 alert("代码必须为双数位!");
			 return false;
		}
		return true;
	}
</script>
[@b.foot/]