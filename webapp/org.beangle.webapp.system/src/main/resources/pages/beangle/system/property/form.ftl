[#ftl]
[@b.form action="!save" ]
<table class="grid" align="center" width="90%">
   <tr>
	 <td width="20%"  class="title">参数名称*</td>
	 <td style="text-align:left"><input name="configNew.name"  value=""/></td>
   </tr>
   <tr>
	 <td class="title">参数值*</td>
	 <td><input name="configNew.value" value="" maxLength="300" style="width:100%"/></td>
   </tr>
   <tr>
	 <td class="title">类型*</td>
	 <td><input name="configNew.type" value=""/></td>
   </tr>
   <tr>
	 <td class="title">说明</td>
	 <td><input name="configNew.description" value="" style="width:100%"/></td>
   </tr>
   <tr>
	<td colspan="2" align="center">[@b.submit value="保存"/]</td>
   </tr>
 </table>
[/@]