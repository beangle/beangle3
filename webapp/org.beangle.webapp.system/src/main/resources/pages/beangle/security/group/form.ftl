[#ftl]
[@b.head/]
 <script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
 
 [#assign labInfo]${b.text("ui.groupInfo")}[/#assign]
 [#include "/template/back.ftl"]
	 <table width="80%"  class="formTable" align="center">
	  <form name="groupForm" action="${b.url('group!save')}" method="post">
	   [@b.redirectParams/]
	   <tr class="thead">
		 <td  colspan="2">${b.text("ui.groupInfo")}</td>
	   </tr>
	   <tr>
		 <td class="title" width="25%" id="f_name">${b.text("common.name")}<font color="red">*</font>:</td>
		 <td >
		  <input type="text" name="group.name" value="${group.name!}" style="width:200px;" />
		  [#if group.id??][@b.a target="_blank" href="restriction!info?forEdit=1&restrictionType=group&restriction.holder.id=${group.id}"]数据级权限[/@][/#if]
		 </td>
	   </tr>
	   <tr>
	   	  <td class="title" >${b.text("common.status")}:</td>
	   	  <td>
			<input value="1" id="group_enabled" type="radio" name="group.enabled" [#if (group.enabled)]checked="checked"[/#if]>
			<label for="group_enabled">${b.text("action.activate")}</label>
			<input value="0" id="group_disabled" type="radio" name="group.enabled" [#if !(group.enabled)]checked="checked"[/#if]>
			<label for="group_disabled">${b.text("action.freeze")}</label>
		</td>
	   </tr>
	   <tr>
	   	  <td class="title" >适用身份:</td>
	   	  <td><select  name="group.category.id" style="width:100px;" >
		   		[#list categories as category]
		   		<option value="${category.id}" [#if category.id=(group.category.id)!(0)]selected="selected"[/#if]>${category.name}</option>
		   		[/#list]
		   	  </select>
		  </select>
		</td>
	   </tr>
	   <tr>
		 <td class="title" id="f_remark">${b.text("common.description")}:</td>
		 <td>
			<textarea cols="30" rows="2" name="group.remark">${group.remark!}</textarea>
		 </td>
	   </tr>
	   <tr class="tfoot">
		 <td colspan="6">
		   <input type="hidden" name="group.id" value="${group.id!}" />
		   <input type="button" value="${b.text("action.submit")}" name="button1" onclick="save(this.form)" class="buttonStyle" />
		   <input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		 </td>
	   </tr>
	   </form>
	 </table>
  <script  >
	function save(form){
	 var a_fields = {
		 'group.name':{'l':'${b.text("common.name")}', 'r':true, 't':'f_name'}
	 };
	 var v = new validator(form, a_fields, null);
	 if (v.exec()) {
		form.submit();
	 }
	}
  </script>
 
[@b.foot/]
