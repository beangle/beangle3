[#ftl]
[@b.head/]
[@b.toolbar title="ui.groupInfo"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="用户组基本信息"]
		[@b.form action="!save" title="ui.groupInfo" theme="list"]
			[@b.textfield name="userGroup.name" label="common.name" value="${userGroup.name!}" required="true" maxlength="50"/]
			[@b.radios label="common.status"  name="userGroup.enabled"value=userGroup.enabled items="1:action.activate,0:action.freeze"/]
			[@b.select label="上级组" name="parent.id" value=(userGroup.parent.id)! style="width:200px;"  items=parents option="id,name" empty="..."/]
			[@b.textfield label="同级顺序号" name="indexno" value="${userGroup.indexno!}" required="true" maxlength="2" check="match('integer').range(1,100)" /]
			[@b.textfield name="userGroup.remark" label="common.remark" value="${userGroup.remark!}" maxlength="50"/]
			[@b.formfoot]
				<input type="hidden" name="userGroup.id" value="${userGroup.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
	[#if userGroup.id??]
	[@b.tab label="全局数据权限" target="group_restriction"/]
	[@b.div href="restriction!info?forEdit=1&restrictionType=group&restriction.holder.id=${userGroup.id}" id="group_restriction"/]
	[/#if]
[/@]
[@b.foot/]