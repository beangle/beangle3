<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
    <div class="ui-widget-header ui-corner-all"><span class="title">默认数据权限</span><span class="ui-icon ui-icon-plusthick"></span></div>
    <div class="portlet-content">
	<#if (restrictions?size==0)>没有设置</#if>
		 <#list restrictions as restriction>
		    <fieldSet  align=center> 
		    <legend>${restriction.paramGroup.name}(${restriction.enabled?string("启用","禁用")})</legend>
		    <#list restriction.paramGroup.params as param>
		      <li>${param.description}</li>
		          <#if param.editor??>
		          <br><#list paramMaps[restriction.id?string][param.name]! as value>${value[param.editor.properties]}<#if value_has_next>,</#if></#list></td>
		          <#else>
		          <br>${paramMaps[restriction.id?string][param.name]!}
		          </#if>
		    </#list>
		    </fieldSet>
		</#list>
    </div>
</div>
