[#ftl]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
[#assign labInfo][@b.text name="security.restrictionPattern.info"/][/#assign]
[#include "/template/back.ftl"/]
   [@s.form id="moduleForm" action="restrict-meta!savePattern" theme="simple" target="ui-tabs-1"]
   <input type="hidden" name="pattern.id" value="${(pattern.id)!}" style="width:200px;" />
   <tr>
	<td>
	 <table width="70%" class="formTable" align="center">
	   <tr class="thead">
		 <td  colspan="2">数据限制模式</td>
	   </tr>
	   <tr>
		 <td class="title" id="f_description">描述<font color="red">*</font>:</td>
		 <td >
			<input name="pattern.remark" value="${pattern.remark!}"/>
		 </td>
	   </tr>
	   <tr>
		 <td class="title" id="f_pattern">模式<font color="red">*</font>:</td>
		 <td >
		  <textarea  style="width:500px;" rows="4" name="pattern.content" >${pattern.content!}</textarea>
		 </td>
	   </tr>
   <tr>
	<td class="title" id="f_params">适应对象:</td>
	<td >
		<select name="pattern.object.id"  style="width:200px">
		 [#list objects as group]
		  <option value="${group.id}" [#if group.id=(pattern.object.id)!(0)]selected="selected"[/#if]>${group.name}</option>
		 [/#list]
		</select>
	</td>
   </tr>
	   <tr class="tfoot">
		 <td colspan="6">
		   <input type="button" value="[@b.text name="action.submit"/]" name="button1" onclick="save(this.form)" class="buttonStyle" />
		   <input type="reset"  name="reset1" value="[@b.text name="action.reset"/]" class="buttonStyle" />
		 </td>
	   </tr>
	 </table>
	</td>
   </tr>
   [/@s.form]
  </table>
  <script  >
   function save(form){
	 var a_fields = {
		 'pattern.remark':{'l':'标题', 'r':true, 't':'f_description'},
		 'pattern.content':{'l':'模式', 'r':true, 't':'f_pattern'}
	 };
	 var v = new validator(form, a_fields, null);
	 if (v.exec()) {
		submit(form);
	 }
   }
  </script>
