[#ftl]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
[#assign labInfo]修改参数信息[/#assign]
[#include "/template/back.ftl"/]
<form name="fieldForm" action="${b.url('!saveField')}" method="post" target="限制参数">
	<input type="hidden" name="field.id" value="${(field.id)!}" style="width:200px;" />
	<table width="80%" class="formTable" align="center">
		<tr class="thead"><td  colspan="2">数据限制参数</td></tr>
		<tr>
		 <td class="title" id="f_name">${b.text("common.name")}<font color="red">*</font>:</td>
		 <td>
		  <input type="text" name="field.name" value="${field.name!}" style="width:200px;" />
		 </td>
	   </tr>
	   <tr>
		 <td class="title" id="f_type">类型<font color="red">*</font>:</td>
		 <td >
		  <input type="text" name="field.type" value="${field.type!}" style="width:200px;" />
		 </td>
	   </tr>
	   <tr>
		 <td class="title" id="f_remark">标题<font color="red">*</font>:</td>
		 <td >
			<input name="field.remark" value="${field.remark!}"/>
		 </td>
	   </tr>
	   <tr>
		 <td class="title" id="f_referenceType">引用类型:</td>
		 <td >
		  <input type="text" name="field.source" value="${(field.source)!}" style="width:400px;" />
		 </td>
	   </tr>
	   <tr>
	<td class="title" id="f_group"><font color="red">*</font>限制对象:</td>
	<td >
	 <table>
	  <tr>
	   <td>
		<select name="Entitys" multiple="multiple" size="10" style="width:200px" onDblClick="JavaScript:bg.select.moveSelected(this.form['Entitys'], this.form['SelectedEntity'])" >
		 [#list entities?sort_by('name') as o]
		  <option value="${o.id}">${o.name}</option>
		 [/#list]
		</select>
	   </td>
	   <td  valign="middle">
		<br/><br/>
		<input onclick="JavaScript:bg.select.moveSelected(this.form['Entitys'], this.form['SelectedEntity'])" type="button" value="&gt;"/>
		<br/><br/>
		<input onclick="JavaScript:bg.select.moveSelected(this.form['SelectedEntity'], this.form['Entitys'])" type="button" value="&lt;"/>
		<br/>
	   </td>
	   <td  class="normalTextStyle">
		<select name="SelectedEntity" multiple="multiple" size="10" style="width:200px;" onDblClick="JavaScript:bg.select.moveSelected(this.form['SelectedEntity'], this.form['Entitys'])">
		 [#list field.entities! as o]
		  <option value="${o.id}">${o.name}</option>
		 [/#list]
		</select>
	   </td>
	  </tr>
	 </table>
	</td>
   </tr>
	   <tr class="tfoot">
		 <td colspan="6"  >
		   <input type="hidden" name="entityIds" value=""/>
		   <input type="button" value="${b.text("action.submit")}" name="button1" onclick="save(this.form)" class="buttonStyle" />
		   <input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		 </td>
	   </tr>
	 </table>
   </form>
  <script  >
   function save(form){
	 form['entityIds'].value = bg.select.getValues(form.SelectedEntity);
	 var a_fields = {
		 'field.name':{'l':'${b.text("common.name")}', 'r':true,'t':'f_name'},
		 'field.remark':{'l':'标题', 'r':true, 't':'f_remark'},
		 'field.type':{'l':'类型', 'r':true, 't':'f_type'}
	 };
	 var v = new validator(form, a_fields, null);
	 if (v.exec()) {
		bg.form.submit(form);
	 }
   }
  </script>