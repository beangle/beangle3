[#ftl]
[@b.head/]
[#assign labInfo][#if user.id??]${b.text("action.modify")}[#else]${b.text("action.new")}[/#if] ${b.text("entity.user")}[/#assign]
[@b.toolbar title=labInfo]bar.addBack("${b.text("action.back")}");[/@]
[@b.messages/]
[@b.form name="userForm" action="!save" class="listform"]
[@b.tabs]
	[@b.tab label="用户信息" theme="list"]
		<fieldset><legend>${b.text('ui.userInfo')}</legend><ol>
		[@b.textfield label="user.name"  name="user.name" value="${user.name!}" style="width:200px;" required="true" maxlength="30"/]
		[@b.radios name="user.enabled" label="common.status" value=user.enabled items="1:action.activate,0:action.freeze"/]
		[@b.textfield label="user.fullname" name="user.fullname" value="${user.fullname!}" style="width:200px;" required="true" maxlength="50" /]
		[@b.password label="密码" name="password" value="" comment="默认密码为1"/]
		[@b.email label="common.email" name="user.mail" value="${user.mail!}" style="width:300px;" required="true" maxlength="50"/]
		[@b.field label="entity.userCategory" required="true"]
		  [#list categories as category]
		  <input name="categoryIds" id="categoryIds${category.id}" value="${category.id}" type="checkbox" [#if user.categories?seq_contains(category)]checked="checked"[/#if] />
		  <label for="categoryIds${category.id}">${category.title}</label>
		  [/#list]
		&nbsp;&nbsp;&nbsp;默认
		  <select name="user.defaultCategory.id" id="user_defaultCategory_id">
		  [#list categories as category]
			 <option value="${category.id}" [#if (user.defaultCategory??)&&(user.defaultCategory.id==category.id)]selected="selected"[/#if]>${category.title}</option>
		  [/#list]
		  </select>
		[/@]
		[#if isadmin|| isme]
		[@b.startend label="common.effective-invalid" name="user.effectiveAt,user.invalidAt" required="true,false" start=user.effectiveAt end=user.invalidAt format="datetime" disabled="disabled"/]
		[#else]
		[@b.startend label="common.effective-invalid" name="user.effectiveAt,user.invalidAt" required="true,false" start=user.effectiveAt end=user.invalidAt format="datetime"/]
		[/#if]
		[@b.datepicker label="user.passwordExpiredAt" name="user.passwordExpiredAt" value=user.passwordExpiredAt format="datetime"/]
		[@b.textarea label="common.remark" cols="50" rows="1" name="user.remark" value="${user.remark!}" maxlength="50"/]
		[@b.formfoot]
			<input type="hidden" name="user.id" value="${user.id!}" />
			[@b.redirectParams/]
			[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" onsubmit="validateUser" /]&nbsp;
		[/@]
		</ol></fieldset>
	[/@]
	[@b.tab label="所在用户组"]
		[@b.grid  items=members?sort_by(["group","name"]) var="m" sortable="false"]
			[@b.row]
				[@b.col title=""]<input name="groupId" type="checkbox" onchange="changeMember(${m.group.id},this)"/>[/@]
				[@b.col title="序号"]${m_index+1}[/@]
				[@b.col title="用户组" property="group.name"]<span [#if !m.group.enabled]class="ui-disabled" title="${b.text('action.freeze')}"[/#if]>${m.group.name}</span>[/@]
				[@b.col title="成员"]
				<input type="checkbox" name="member${m.group.id}" ${(memberMap.get(m.group).member)?default(false)?string('checked="checked"','')}/>
				[/@]
				[@b.col title="授权"]
				[#if m.granter]<input type="checkbox" name="granter${m.group.id}" ${(memberMap.get(m.group).granter)?default(false)?string('checked="checked"','')}/>[/#if]
				[/@]
				[@b.col title="管理"]
				<input type="checkbox" name="manager${m.group.id}" ${(memberMap.get(m.group).manager)?default(false)?string('checked="checked"','')}/>
				[/@]
				[@b.col title="加入时间"]${(memberMap.get(m.group).updatedAt?string("yyyy-MM-dd HH:mm"))!}[/@]
			[/@]
		[/@]
	[/@]
	[#if user.id??]
	[@b.tab label="全局数据权限" href="restriction!info?forEdit=1&restrictionType=user&restriction.holder.id=${user.id}" /]
	[/#if]
[/@]
[/@]
<script  type="text/javascript">
	function validateUser(form){
		jQuery("#user_defaultCategory_id").parent().find(".error").remove();
		var cIds = bg.input.getCheckBoxValues("categoryIds");
		if(""==cIds){
			jQuery("#user_defaultCategory_id").parent().append("<label class='error'>请选择身份</label>");
			return false;
		}
		var arr = cIds.split(",");
		var defaultValue = form["user.defaultCategory.id"].value;
		var isIn = false;
		for(var i=0;i<arr.length;i++){
			if(defaultValue==arr[i]){
				isIn=true;
				break;
			}
		}
		if(!isIn){
			jQuery("#user_defaultCategory_id").parent().append("<label class='error'>默认身份必须在所选身份中！</label>");
			return false;
		}
		return true;
	}
	/**
	 * 改变每个组行之前的复选框
	 */
	function changeMember(groupId,checkbox){
		if(null==checkbox) return;
		newStatus=checkbox.checked
		var form=document.userForm;
		if(typeof form['member'+groupId]!="undefined"){
			form['member'+groupId].checked=newStatus;
		}
		if(typeof form['manager'+groupId]!="undefined"){
			form['manager'+groupId].checked=newStatus;
		}
		if(typeof form['granter'+groupId]!="undefined"){
			form['granter'+groupId].checked=newStatus;
		}
	}
</script>
[@b.foot/]