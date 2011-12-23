[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">默认数据权限</span></div>
	<div class="portlet-content">
	[#if (restrictions?size==0)]没有设置[/#if]
		 [#list restrictions as restriction]
			<fieldSet  align="center">
			<legend>${restriction.pattern.remark}(${restriction.enabled?string("启用","禁用")})</legend>
			[#list restriction.pattern.entity.fields as field]
				<li>${field.remark}</li>
				[#if field.multiple]
				[#list fieldMaps[restriction.id?string][field.name]! as value][#list field.propertyNames?split(",") as pName]${value[pName]!} [/#list][#if value_has_next],[/#if][/#list]</td>
				[#else]
				${fieldMaps[restriction.id?string][field.name]!}
				[/#if]
			[/#list]
			</fieldSet>
		[/#list]
	</div>
</div>
