<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/validator.js"></script>
 <body >
 <#assign labInfo><@text name="security.copyAuth"/></#assign>
 <#include "/template/back.ftl">
     <table width="80%"  class="formTable" align="center">
      <form name="groupForm" action="group.action?method=copyAuth" method="post" onsubmit="return false;">
       <input type="hidden" name="fromGroupId" value="${fromGroup.id}"/>
       <input type="hidden" name="toGroupIds" value=""/>
       <@searchParams/>
	   <tr class="thead">
	     <td  colspan="2"><@text name="security.copyAuth"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name">&nbsp;<@text name="security.fromGroup"/>:</td>
	     <td >${fromGroup.name}</td>
	   </tr>
	   
	   <tr>
	    <td class="title" id="f_studentType"><font color="red">*</font><@text name="security.toGroup"/>:</td>
	    <td >
	     <table>
	      <tr>
	       <td>
	        <select name="Groups" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Groups'], this.form['SelectedGroup'])" >
	         <#list toGroups?sort_by('name') as group>
	          <option value="${group.id}"><@i18nName group/></option>
	         </#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['Groups'], this.form['SelectedGroup'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedGroup'], this.form['Groups'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	        <select name="SelectedGroup" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedGroup'], this.form['Groups'])">
              
	        </select>
	       </td>             
	      </tr>
	     </table>
	    </td>
	   </tr>	   
	   <tr class="tfoot">
	     <td colspan="6"  >
	       <button  onclick="copyAuth(this.form)"><@text name="action.submit"/></button>&nbsp;
	       <input type="reset"  name="reset1" value="<@text name="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
       </form>
     </table>
     <pre>
        拷贝权限实现的是差异拷贝。如果目标用户组含有该模块，则不会覆盖拷贝。
        新拷贝模块的数据权限是依照用户组配置的数据权限设置的。如果要修改该权限，可以在修改用户组信息中进行设置。
     </pre>
  <script language="javascript" >
   function copyAuth(form){
     form['toGroupIds'].value = getAllOptionValue(form.SelectedGroup);
     if(""==form['toGroupIds'].value){
        alert("<@text "action.select"/>");
        return;
     }
     if(confirm("<@text "common.confirmAction"/>")){
        form.submit();
     }
   }
  </script>
 </body>
<#include "/template/foot.ftl"/>
