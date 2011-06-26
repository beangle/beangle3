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
	[@b.textfield label="common.name" name="menu.name" value="${menu.name!}" style="width:200px;"  required="true" maxlength="100" /]
	[@b.textfield label="标题" name="menu.title" value="${menu.title!}" style="width:200px;" required="true" maxlength="50"/]
	[@b.select label="上级菜单" name="parent.id" value=(menu.parent.id)! style="width:200px;"  items=parents option="id,description" empty="..."/]
	[@b.textfield label="同级顺序号" name="indexno" value="${menu.indexno!}" required="true" maxlength="2" check="match('integer')" /]
	[@b.field label="common.status" required="true"]
		<select  name="menu.enabled" style="width:100px;" >
			<option value="true" [#if menu.enabled]selected="selected"[/#if]>${b.text("action.activate")}</option>
			<option value="false" [#if !menu.enabled]selected="selected"[/#if]>${b.text("action.freeze")}</option>
		</select>
	[/@]
	[@b.textfield label="menu.entry"  name="menu.entry" value="${menu.entry!}" maxlength="100" /]
	[@b.select2 label="使用资源" name1st="Resources" name2nd="SelectedResource" items1st=resources?sort_by("name") items2nd= menu.resources?sort_by("name") option="id,description"/]
	[@b.textarea label="common.remark"  name="menu.remark" maxlength="50" value=menu.remark! rows="2" cols="40"/]
	[@b.formfoot]
		[@b.submit value="action.submit" onsubmit="validateMenu"/]&nbsp;
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		[@b.redirectParams/]
		<input type="hidden" name="menu.id" value="${menu.id!}" />
		<input type="hidden" name="resourceIds" />
	[/@]
[/@]
<script  type="text/javascript">
   function validateMenu(form){
       form['resourceIds'].value = bg.select.getValues(form.SelectedResource);
       return true;
   }
</script>

[@b.foot/]