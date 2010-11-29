<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/validator.js"></script>
 <body >
 <#assign labInfo><@text name="ui.groupInfo"/></#assign>
 <#include "/template/back.ftl">
     <table width="80%"  class="formTable" align="center">
      <form name="groupForm" action="group.action?method=save" method="post">
       <@searchParams/>
	   <tr class="thead">
	     <td  colspan="2"><@text name="ui.groupInfo"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name"><@text name="common.name"/><font color="red">*</font>:</td>
	     <td >
          <input type="text" name="group.name" value="${group.name!}" style="width:200px;" />
          <#if group.id??><a target="_blank" href="restriction.action?method=info&forEdit=1&restrictionType=group&restriction.holder.id=${group.id}">数据级权限</a></#if>   
         </td>
	   </tr>
	   <tr>
	   	  <td class="title" ><@text "common.status"/>:</td>
	   	  <td>
			<input value="1" id="group_enabled" type="radio" name="group.enabled" <#if (group.enabled)>checked</#if>>
			<label for="group_enabled"><@text name="action.activate"/></label>
			<input value="0" id="group_disabled" type="radio" name="group.enabled" <#if !(group.enabled)>checked</#if>>
			<label for="group_disabled"><@text name="action.freeze"/></label>
		</td>
	   </tr>
	   <tr>
	   	  <td class="title" >适用身份:</td>
	   	  <td><select  name="group.category.id" style="width:100px;" >
		   		<#list categories as category>
		   		<option value="${category.id}" <#if category.id=(group.category.id)!(0)>selected</#if>>${category.name}</option>
		   		</#list>
		   	  </select>
		  </select>
		</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_remark"><@text name="common.description"/>:</td>
	     <td>
	        <textarea cols="30" rows="2" name="group.remark">${group.remark!}</textarea>
         </td>
	   </tr>
	   <tr class="tfoot">
	     <td colspan="6">
	       <input type="hidden" name="group.id" value="${group.id!}" />
	       <input type="button" value="<@text name="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />
	       <input type="reset"  name="reset1" value="<@text name="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
       </form>
     </table>
  <script language="javascript" >
    function save(form){
     var a_fields = {
         'group.name':{'l':'<@text name="common.name"/>', 'r':true, 't':'f_name'}        
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
    }
  </script>
 </body>
<#include "/template/foot.ftl"/>
