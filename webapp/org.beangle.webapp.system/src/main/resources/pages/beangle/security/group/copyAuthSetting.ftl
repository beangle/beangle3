[#ftl]
[@b.head/]
 <script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
 
 [#assign labInfo]${b.text("security.copyAuth")}[/#assign]
 [#include "/template/back.ftl"]
	 <table width="80%"  class="formTable" align="center">
	  <form name="groupForm" action="${b.url('group!copyAuth')}" method="post" onsubmit="return false;">
	   <input type="hidden" name="fromGroupId" value="${fromGroup.id}"/>
	   <input type="hidden" name="toGroupIds" value=""/>
	   [@b.redirectParams/]
	   <tr class="thead">
		 <td  colspan="2">${b.text("security.copyAuth")}</td>
	   </tr>
	   <tr>
		 <td class="title" width="25%" id="f_name">&nbsp;${b.text("security.fromGroup")}:</td>
		 <td >${fromGroup.name}</td>
	   </tr>

	   <tr>
		<td class="title" id="f_studentType"><font color="red">*</font>${b.text("security.toGroup")}:</td>
		<td >
		 <table>
		  <tr>
		   <td>
			<select name="Groups" multiple="multiple" size="10" style="width:200px" onDblClick="JavaScript:bg.select.moveSelected(this.form['Groups'], this.form['SelectedGroup'])" >
			 [#list toGroups?sort_by('name') as group]
			  <option value="${group.id}">[@i18nName group/]</option>
			 [/#list]
			</select>
		   </td>
		   <td  valign="middle">
			<br/><br/>
			<input onclick="JavaScript:bg.select.moveSelected(this.form['Groups'], this.form['SelectedGroup'])" type="button" value="&gt;"/>
			<br/><br/>
			<input onclick="JavaScript:bg.select.moveSelected(this.form['SelectedGroup'], this.form['Groups'])" type="button" value="&lt;"/>
			<br/>
		   </td>
		   <td  class="normalTextStyle">
			<select name="SelectedGroup" multiple="multiple" size="10" style="width:200px;" onDblClick="JavaScript:bg.select.moveSelected(this.form['SelectedGroup'], this.form['Groups'])">

			</select>
		   </td>
		  </tr>
		 </table>
		</td>
	   </tr>
	   <tr class="tfoot">
		 <td colspan="6"  >
		   <button  onclick="copyAuth(this.form)">${b.text("action.submit")}</button>&nbsp;
		   <input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		 </td>
	   </tr>
	   </form>
	 </table>
	 <pre>
		拷贝权限实现的是差异拷贝。如果目标用户组含有该模块，则不会覆盖拷贝。
		新拷贝模块的数据权限是依照用户组配置的数据权限设置的。如果要修改该权限，可以在修改用户组信息中进行设置。
	 </pre>
  <script  >
   function copyAuth(form){
	 form['toGroupIds'].value = bg.select.getValues(form.SelectedGroup);
	 if(""==form['toGroupIds'].value){
		alert("${b.text("action.select")}");
		return;
	 }
	 if(confirm("${b.text("common.confirmAction")}")){
		form.submit();
	 }
   }
  </script>
 
[@b.foot/]
