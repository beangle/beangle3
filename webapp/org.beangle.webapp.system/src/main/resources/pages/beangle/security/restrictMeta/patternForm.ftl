[#ftl]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
[#assign labInfo]${b.text("security.restrictionPattern.info")}[/#assign]
[#include "/template/back.ftl"/]
[@b.form action="!savePattern"]
<table width="70%" class="formTable" align="center">
	<tr class="thead">
		<td  colspan="2">数据限制模式</td>
	</tr>
	<tr>
		<td class="title" id="f_description">描述<font color="red">*</font>:</td>
		<td><input name="pattern.remark" value="${pattern.remark!}"/></td>
	</tr>
	<tr>
	 <td class="title" id="f_pattern">模式<font color="red">*</font>:</td>
	 <td >
	  <textarea  style="width:500px;" rows="4" name="pattern.content" >${pattern.content!}</textarea>
	 </td>
	</tr>
	<tr>
		<td class="title" id="f_params">${b.text('entity.restrictEntity')}:</td>
		<td >
			<select name="pattern.entity.id"  style="width:200px">
			 [#list entities as entity]
			  <option value="${entity.id}" [#if entity.id=(pattern.entity.id)!(0)]selected="selected"[/#if]>${entity.name}</option>
			 [/#list]
			</select>
		</td>
	</tr>
	<tr class="tfoot">
		<td colspan="2">
			<input type="hidden" name="pattern.id" value="${(pattern.id)!}" style="width:200px;" />
			[@b.submit value="action.submit" onsubmit="validatePattern"/]
			<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		</td>
	</tr>
</table>
[/@]
<script>
function validatePattern(form){
	var a_fields = {
		'pattern.remark':{'l':'标题', 'r':true, 't':'f_description'},
		'pattern.content':{'l':'模式', 'r':true, 't':'f_pattern'}
	};
	var v = new validator(form, a_fields, null);
	return v.exec();
}
</script>