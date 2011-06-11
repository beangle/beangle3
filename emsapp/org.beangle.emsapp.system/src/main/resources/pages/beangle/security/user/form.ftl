[#ftl]
[@b.head/]
[#assign labInfo][#if user.id??]${b.text("action.modify")}[#else]${b.text("action.new")}[/#if] ${b.text("entity.user")}[/#assign]
[@b.toolbar title=labInfo]bar.addBack("${b.text("action.back")}");[/@]
[@b.form name="userForm" action="!save" cssClass="listform"]
[@sj.tabbedpanel id="userTabs"]
	[@sj.tab id="userTab1" label="用户信息" target="userInfo"/]
	[@sj.tab id="userTab2" label="所在用户组" target="groupmember"/]
	[#if user.id??]
	[@sj.tab id="userTab3" label="全局数据权限" target="user_restriction"/]
	[@b.div href="restriction!info?forEdit=1&restrictionType=user&restriction.holder.id=${user.id}" id="user_restriction"/]
	[/#if]
	[@b.div id="userInfo" theme="list" asContainer="false"]
	<fieldset><legend>${b.text('ui.userInfo')}</legend><ol>
		[@b.textfield label="user.name"  name="user.name" value="${user.name!}" style="width:200px;" required="true" maxlength="30"/]
		[@b.field label="common.status" required="true"]
		 <input value="1" id="user_status_1" type="radio" name="user.status" [#if (user.status!1)==1]checked="checked"[/#if] />
		 <label for="user_status_1">${b.text("action.activate")}</label>
		 <input value="0" id="user_status_0" type="radio" name="user.status" [#if (user.status!1)==0]checked="checked"[/#if] />
		 <label for="user_status_0">${b.text("action.freeze")}</label>
		[/@]
		[@b.textfield label="user.fullname" name="user.fullname" value="${user.fullname!}" style="width:200px;" required="true" maxlength="60" /]
		[@b.password label="密码" name="password" value="" axLength="60" comment="默认密码为1"/]
		[@b.textfield label="common.email" name="user.mail" value="${user.mail!}" style="width:300px;" maxlength="70" required="true"/]
		[@b.field label="entity.userCategory" required="true"]
		  [#list categories as category]
		  <input name="categoryIds" id="categoryIds${category.id}" value="${category.id}" type="checkbox" [#if user.categories?seq_contains(category)]checked="checked"[/#if] />
		  <label for="categoryIds${category.id}">${category.name}</label>
		  [/#list]
		&nbsp;&nbsp;&nbsp;默认
		  <select name="user.defaultCategory.id">
		  [#list categories as category]
			 <option value="${category.id}" [#if (user.defaultCategory??)&&(user.defaultCategory.id==category.id)]selected="selected"[/#if]>${category.name}</option>
		  [/#list]
		  </select>
		[/@]
		[@b.textarea label="common.remark" cols="50" rows="1" name="user.remark" value="${user.remark!}" maxlength="100"/]
		[@b.formfoot]
			<input type="hidden" name="user.id" value="${user.id!}" />
			[@b.redirectParams/]
			[@b.submit value="action.submit" onsubmit="validateUser" /]&nbsp;
			<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		[/@]
		</ol></fieldset>
	[/@]
	<div id="groupmember">
	[@b.grid  items=members var="m"]
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
	</div>
[/@]
[/@]
<script  type="text/javascript">
	function validateUser(form){
		var cIds = bg.input.getCheckBoxValues("categoryIds");
		if(""==cIds){
		   alert("请选择身份");return false;
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
			alert("默认身份必须在所选身份中！");
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