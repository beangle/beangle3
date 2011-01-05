[#ftl]
[@b.xhtmlhead/]
 <script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>

<link href="${base}/static/css/tableTree.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="${base}/static/scripts/common/TableTree.js"></script>
<script type="text/javascript"> defaultColumn=1;</script>

 
 [#assign labInfo][@b.text name="info.moduleUpdate"/][/#assign]
 [#include "/template/back.ftl"/]
   <form name="moduleForm" action="menu-profile.action?method=save" method="post">
   [@b.magicParams/]
   <input type="hidden" name="menuProfile.id" value="${menuProfile.id!}" style="width:200px;" />
   <input type="hidden" name="menuProfileIds" />
   <tr>
	<td>
	 <table width="70%" class="formTable" align="center">
	   <tr class="thead">
		 <td  colspan="2">[@b.text name="info.module.detail"/]</td>
	   </tr>

	   <tr>
		 <td class="title" width="25%" id="f_name">&nbsp;名称<font color="red">*</font>:</td>
		 <td >
		  <input type="text" name="menuProfile.name" value="${menuProfile.name!}" style="width:200px;" />
		 </td>
	   </tr>
	   <tr>
	 		<td class="title" width="25%" id="f_category">&nbsp;[@b.text name="userCategory" /]<font color="red">*</font>:</td>
	 		<td>
	 		<select  name="menuProfile.category.id" style="width:100px;" >
		 		[#list categories as category]
		 			<option value="${category.id}"  [#if menuProfile.category??&&menuProfile.category.id==category.id]selected="selected"[/#if]>${category.name}</option>
		  		[/#list]
		  	</select>
		  	</td>
 	 </tr>
	   <tr class="tfoot">
		 <td colspan="6"  >
		   <input type="button" value="[@b.text name="action.submit"/]" name="button1" onclick="save(this.form)" class="buttonStyle" />&nbsp;
		   <input type="reset"  name="reset1" value="[@b.text name="action.reset"/]" class="buttonStyle" />
		 </td>
	   </tr>
	 </table>
	</td>
   </tr>
   </form>
  </table>

  <script  >
   function save(form){
	 var a_fields = {
	 	 'menuProfile.name':{'l':'[@b.text name="common.name"/]', 'r':true,'t':'f_name'},
	 	 'menuProfile.category.id':{'l':'[@b.text name="userCategory"/]', 'r':true,'t':'f_category'}
	 };
	 var v = new validator(form, a_fields, null);
	 if (v.exec()) {
		form.submit();
	 }
   }
  </script>
 
[#include "/template/foot.ftl"/]
