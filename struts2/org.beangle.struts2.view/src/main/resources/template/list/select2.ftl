[#ftl]
<li${tag.parameterString}>[#if tag.label??]<label class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}:</label>[/#if]
<table><tr>
<td>
<select name="${tag.name1st}" multiple="multiple" size="${tag.size}" style="width:250px" onDblClick="JavaScript:bg.select.moveSelected(this.form['${tag.name1st}'], this.form['${tag.name2nd}'])" >
	[#list tag.items1st as item]
	<option value="${(item[tag.keyName])!}">${(item[tag.valueName])!}</option>
	[/#list]
</select>
</td>
<td style="width:30px">
	<input style="margin:auto" onclick="JavaScript:bg.select.moveSelected(this.form['${tag.name1st}'], this.form['${tag.name2nd}'])" type="button" value="&gt;"/>
	<input style="vertical-align: middle;" onclick="JavaScript:bg.select.moveSelected(this.form['${tag.name2nd}'], this.form['${tag.name1st}'])" type="button" value="&lt;"/>
</td>
<td>
<select name="${tag.name2nd}" multiple="multiple" size="${tag.size}" style="width:250px;" onDblClick="JavaScript:bg.select.moveSelected(this.form['${tag.name2nd}'], this.form['${tag.name1st}'])">
	[#list tag.items2nd as item]
	<option value="${(item[tag.keyName])!}">${(item[tag.valueName])!}</option>
	[/#list]
</select>
</td>
</tr></table>
</li>