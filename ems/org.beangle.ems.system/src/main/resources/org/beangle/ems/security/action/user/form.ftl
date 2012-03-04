[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
</script>
[#assign labInfo][#if user.id??]${b.text("action.modify")}[#else]${b.text("action.new")}[/#if] ${b.text("entity.user")}[/#assign]
[@b.toolbar title=labInfo]bar.addBack("${b.text("action.back")}");[/@]
[@b.messages/]
[@b.tabs]
[@b.form name="userForm" action="!save" class="listform" theme="list"]
	<input type="hidden" name="user.id" value="${user.id!}" />
	[@b.redirectParams/]

	[@b.tab label="用户信息"]
		[@b.textfield label="user.name"  name="user.name" value="${user.name!}" style="width:200px;" required="true" maxlength="30"/]
		[@b.radios name="user.enabled" label="common.status" value=user.enabled items="1:action.activate,0:action.freeze"/]
		[@b.textfield label="user.fullname" name="user.fullname" value="${user.fullname!}" style="width:200px;" required="true" maxlength="50" /]
		[@b.password label="密码" name="password" value="" comment="默认密码为1"/]
		[@b.email label="common.email" name="user.mail" value="${user.mail!}" style="width:300px;" required="true" maxlength="50"/]
		[#if isadmin|| isme]
		[@b.startend label="common.effective-invalid" name="user.effectAt,user.invalidAt" required="true,false" start=user.effectAt end=user.invalidAt format="datetime" disabled="disabled"/]
		[#else]
		[@b.startend label="common.effective-invalid" name="user.effectAt,user.invalidAt" required="true,false" start=user.effectAt end=user.invalidAt format="datetime"/]
		[/#if]
		[@b.datepicker label="user.passwordExpiredAt" name="user.passwordExpiredAt" value=user.passwordExpiredAt format="datetime"/]
		[@b.textarea label="common.remark" cols="50" rows="1" name="user.remark" value="${user.remark!}" maxlength="50"/]
		[@b.formfoot][@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/][/@]
	[/@]
	[@b.tab label="所在用户组"]
		[@b.grid  items=groups?sort_by("code") var="group" sortable="false"]
			[@b.row]
				<tr [#if group??]id="${group.code}"[/#if]>
				[@b.col title="序号" width="5%"]${group_index+1}[/@]
				[@b.treecol title="用户组" property="name"]<span [#if !group.enabled]class="ui-disabled" title="${b.text('action.freeze')}"[/#if]>${group.code} ${group.name}</span>[/@]
				[@b.col title="成员" width="10%"]
				<input type="checkbox" name="member${group.id}" onchange="changeMember(${group.id},this)" ${(memberMap.get(group).member)?default(false)?string('checked="checked"','')}/>
				[/@]
				[@b.col title="授权" width="10%"]
				[#if !curMemberMap.get(group).manager && !(memberMap.get(group).granter)?default(false)][#else]<input type="checkbox" name="granter${group.id}" [#if !curMemberMap.get(group).manager]disabled="disabled"[/#if] ${(memberMap.get(group).granter)?default(false)?string('checked="checked"','')}/>[/#if]
				[/@]
				[@b.col title="管理" width="10%"]
				[#if !curMemberMap.get(group).manager && !(memberMap.get(group).manager)?default(false)][#else]<input type="checkbox" name="manager${group.id}" [#if !curMemberMap.get(group).manager]disabled="disabled"[/#if] ${(memberMap.get(group).manager)?default(false)?string('checked="checked"','')}/>[/#if]
				[/@]
				[@b.col title="加入时间" width="20%"]${(memberMap.get(group).updatedAt?string("yyyy-MM-dd HH:mm"))!}[/@]
				</tr>
			[/@]
		[/@]
	[/@]
[/@]
	[#if user.id??]
	[@b.tab label="全局数据权限" href="restriction!info?forEdit=1&restrictionType=user&restriction.holder.id=${user.id}" /]
	[/#if]
[/@]
<script  type="text/javascript">
	/**
	 * 改变每行之前的复选框
	 */
	function changeMember(groupId,checkbox){
		if(null==checkbox) return;
		treeToggle(checkbox)
		newStatus=checkbox.checked
		var form=document.userForm;
		if(typeof form['member'+groupId]!="undefined"){
			form['member'+groupId].checked=newStatus;
		}
		/*if(typeof form['granter'+groupId]!="undefined"){
			if(!form['granter'+groupId].disabled) form['granter'+groupId].checked=newStatus;
		}
		if(typeof form['manager'+groupId]!="undefined"){
			if(!form['manager'+groupId].disabled) form['manager'+groupId].checked=newStatus;
		}*/
	}
</script>
[@b.foot/]