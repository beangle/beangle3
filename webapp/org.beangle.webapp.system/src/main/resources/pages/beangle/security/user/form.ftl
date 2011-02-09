[#ftl]
[@b.head/]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
[#assign labInfo][#if user.name??]${b.text("action.modify")}[#else]${b.text("action.new")}[/#if] ${b.text("user")}[/#assign]
[#include "/template/back.ftl"]
[@b.form name="userForm" action="!save"]
[@sj.tabbedpanel id="userTabs"]
	[@sj.tab id="userTab1" label="用户信息" target="userInfo"/]
	[@sj.tab id="userTab2" label="所在用户组" target="groupmember"/]
	[#if user.id??]
	[@sj.tab id="userTab3" label="全局数据权限" href="${b.url('restriction!info')}?forEdit=1&restrictionType=user&restriction.holder.id=${user.id}"/]
	[/#if]
	<div id="userInfo">
	 <table width="100%"  class="formTable">
	   <tr class="thead">
		 <td  colspan="2">${b.text("ui.userInfo")}</td>
	   </tr>
	   <tr>
		 <td class="title" width="15%" id="f_name"><font color="red">*</font>${b.text("user.name")}:</td>
		 <td >
		 [#if !(user.id??)]
		  <input type="text" name="user.name" value="${user.name!}" style="width:200px;" maxlength="30"/>
		 [#else]
		 ${user.name}
		 [/#if]
		 </td>
		</tr>
		<tr>
		 <td class="title">${b.text("common.status")}:</td>
		 <td >
		 <input value="1" id="user_status_1" type="radio" name="user.status" [#if (user.status!1)==1]checked="checked"[/#if] />
		 <label for="user_status_1">${b.text("action.activate")}</label>
		 <input value="0" id="user_status_0" type="radio" name="user.status" [#if (user.status!1)==0]checked="checked"[/#if] />
		 <label for="user_status_0">${b.text("action.freeze")}</label>
		 </td>
	   </tr>
	   <tr>
		 <td class="title" id="f_fullname"><font color="red">*</font>${b.text("user.fullname")}:</td>
		 <td ><input type="text" name="user.fullname" value="${user.fullname!}" style="width:200px;" maxlength="60" /></td>
	   </tr>
	   <tr>
		 <td class="title" id="f_password">密码:</td>
		 <td><input type="password" name="password" value="" style="width:200px;" maxlength="60" /> 默认密码为1</td>
	   </tr>
	   <tr>
		 <td class="title" id="f_email"><font color="red">*</font>${b.text("common.email")}:</td>
		 <td ><input type="text" name="user.mail" value="${user.mail!}" style="width:300px;" maxlength="70" /></td>
	   </tr>
	   <tr>
		 <td class="title">&nbsp;<font color="red">*</font>${b.text("entity.userCategory")}:</td>
		 <td>
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
		  </td>
		</tr>
		<tr>
		 <td id="f_remark" class="title">&nbsp;${b.text("common.remark")}:</td>
		 <td><textarea cols="50" rows="1" name="user.remark">${user.remark!}</textarea></td>
	   </tr>
	   <tr class="tfoot">
		<td colspan="6">
			<input type="hidden" name="user.id" value="${user.id!}" />
			[@b.redirectParams/]
			[@b.submit value="action.submit" onsubmit="validate" /]&nbsp;
			<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		</td>
	   </tr>
	 </table>
	</div>
	<div id="groupmember">
	[@b.grid    items=members var="m"]
		[@b.row]
			[@b.col title=""]<input name="groupId" type="checkbox" onchange="changeMember(${m.group.id},this)"/>[/@]
			[@b.col title="序号"]${m_index+1}[/@]
			[@b.col title="用户组" property="group.name"/]
			[@b.col title="成员"]
			<input type="checkbox" name="member${m.group.id}" ${(memberMap.get(m.group).member)?default(false)?string('checked="checked"','')}/>
			[/@]
			[@b.col title="授权"]
			[#if m.granter]<input type="checkbox" name="granter${m.group.id}" ${(memberMap.get(m.group).granter)?default(false)?string('checked="checked"','')}/>[/#if]
			[/@]
			[@b.col title="管理"]
			<input type="checkbox" name="manager${m.group.id}" ${(memberMap.get(m.group).manager)?default(false)?string('checked="checked"','')}/>
			[/@]
			[@b.col title="加入时间"]${(m.updatedAt?string("yyyy-MM-dd HH:mm"))!}[/@]
		[/@]
	[/@]
	</div>
[/@]
[/@]
<script  type="text/javascript">
	function validate(form){
		var a_fields = {
			 'user.mail':{'l':'${b.text("common.email")}', 'r':true, 'f':'email', 't':'f_email'},
			  [#if !(user.id??)]
			 'user.name':{'l':'${b.text("user.name")}', 'r':true, 't':'f_name'},
			 [/#if]
			 'user.fullname':{'l':'真实姓名', 'r':true, 't':'f_fullname'},
			 'user.remark':{'l':'${b.text("common.remark")}','r':false,'t':'f_remark','mx':80}
		};

		var v = new validator(form, a_fields, null);
		if (v.exec()) {
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