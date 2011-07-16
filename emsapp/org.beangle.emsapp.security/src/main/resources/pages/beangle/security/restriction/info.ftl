[#ftl]
[@b.head/]
[@b.toolbar title="数据权限"]
[/@]
[#if (restrictions?size==0)]没有设置[/#if]
 [#list restrictions as restriction]
	<fieldSet  align="center">
	<legend>${restriction.pattern.remark}(${restriction.enabled?string("启用","禁用")}) <a href="#" onclick="edit('${restriction.id}')">修改</a>	<a href="#" onclick="remove('${restriction.id}')">删除</a></legend>
	[#list restriction.pattern.entity.fields as field]
	<li>${field.remark}</li>
		[#if field.multiple &&  field.source??]
		[#list fieldMaps[restriction.id?string][field.name]! as value][#list field.propertyNames?split(",") as pName]${value[pName]!} [/#list][#if value_has_next],[/#if][/#list]</td>
		[#else]
		${fieldMaps[restriction.id?string][field.name]!}
		[/#if]
	[/#list]
	</fieldSet>
[/#list]
<br/>

[#list patterns! as pattern]
 <li>${pattern.remark!} <a onclick="add('${pattern.id}')" href='#'>${b.text('action.new')}</a></li>
[/#list]
[@b.form name="restrictionForm" ]
	<input type="hidden" name="restrictionType" value="${Parameters['restrictionType']}"/>
	<input type="hidden" name="restriction.holder.id" value="${Parameters['restriction.holder.id']}"/>
	<input type="hidden" name="params" value="&restriction.holder.id=${Parameters['restriction.holder.id']}&restrictionType=${Parameters['restrictionType']}"/>
[/@]
<script type="text/javascript">
	function add(patternId){
		var form =document.restrictionForm;
		bg.form.addInput(form,"restriction.pattern.id",patternId);
		form.action="${b.url('!edit')}";
		bg.form.submit(form);
	}
	function edit(restrictionId){
		var form = document.restrictionForm;
		bg.form.addInput(form,"restriction.id",restrictionId);
		form.action="${b.url('!edit')}";
		bg.form.submit(form);
	}
	function remove(restrictionId){
		if(!confirm("确定删除?")) return;
		var form =document.restrictionForm;
		bg.form.addInput(form,"restriction.id",restrictionId);
		form.action="${b.url('!remove')}";
		bg.form.submit(form);
	}
</script>
[@b.foot/]