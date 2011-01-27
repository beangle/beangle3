[#ftl]
[@b.head/]
 
 <table id="restrictionBar"></table>
 [#if (restrictions?size==0)]没有设置[/#if]
 [#list restrictions as restriction]
	<fieldSet  align="center">
	<legend>${restriction.pattern.name}(${restriction.enabled?string("启用","禁用")}) <a href="#" onclick="edit('${restriction.id}')">修改</a>	<a href="#" onclick="remove('${restriction.id}')">删除</a></legend>
	[#list restriction.paramGroup.params  as param]
	  <li>${param.description}</li>
		  [#if param.editor??]
		  <br/>[#list paramMaps[restriction.id?string][param.name]! as value]${value[param.editor.properties]}[#if value_has_next],[/#if][/#list]</td>
		  [#else]
		  <br/>${paramMaps[restriction.id?string][param.name]!}
		  [/#if]
	[/#list]
	</fieldSet>
[/#list]
<br/>
[#list paramGroups! as paramGroup]
 <li>${paramGroup.name} <a onclick="add('${paramGroup.id}')" href='#'>添加</a></li>
[/#list]
<form name="actionForm" method="post">
	<input type="hidden" name="restrictionType" value="${Parameters['restrictionType']}"/>
	<input type="hidden" name="restriction.holder.id" value="${Parameters['restriction.holder.id']}"/>
	<input type="hidden" name="params" value="&restriction.holder.id=${Parameters['restriction.holder.id']}&restrictionType=${Parameters['restrictionType']}"/>
</form>
<script type="text/javascript">
   var bar = bg.ui.toolbar('restrictionBar','数据权限');
   bar.setMessage('[@b.messages/]');
   bar.addHelp();
   function edit(){
	  self.location="${b.url('!edit')}?restriction.id=";
   }
   function add(patternId){
   		var form =document.actionForm;
   		addInput(form,"restriction.paramGroup.id",patternId);
   		form.action="${b.url('!edit')}";
   		form.submit();
   }
   function edit(restrictionId){
   		var form =document.actionForm;
   		addInput(form,"restriction.id",restrictionId);
   		form.action="${b.url('!edit')}";
   		form.submit();
   }
   function remove(restrictionId){
   		if(!confirm("确定删除?")) return;
   		var form =document.actionForm;
   		addInput(form,"restriction.id",restrictionId);
   		form.action="${b.url('!remove')}";
   		form.submit();
   }
 </script>
 
[@b.foot/]
