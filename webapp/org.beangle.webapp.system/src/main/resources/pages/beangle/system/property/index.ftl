[#ftl]
[@b.head/]
[@b.toolbar title="&nbsp;系统参数"]bar.addBack();[/@]

[@sj.accordion id="accordion"]
	[@sj.accordionItem title="固定参数"]
	[@b.grid items=staticNames var="name" width="90%"]
		[@b.row]
		 [@b.col title="序号" width="10%"]${name_index+1}[/@]
		 [@b.col title="参数名称" width="20%"]${name}[/@]
		 [@b.col title="参数值" width="25%"]${config.get(name)}[/@]
		[/@]
	 [/@]
	 [/@sj.accordionItem]

	 [@sj.accordionItem title="可编辑参数"]
	<form name="systemConfigForm" method="post" action="" onsubmit="return false;">
	[@b.grid width="95%" items=propertyConfigs var="config"]
		[@b.row]
		 [@b.col title="序号" width="7%"]${config_index+1}[/@]
		 [@b.col title="参数名称" width="20%" style="text-align:left"]<input name="config${config.id}.name"  value="${config.name}" style="width:100%"/>[/@]
		 [@b.col title="类型" width="15%"]<input name="config${config.id}.type" value="${config.type!}" style="width:100%"/>[/@]
		 [@b.col title="参数值" width="35%"]<input name="config${config.id}.value" value="${config.value?default("")}" maxlength="300" style="width:100%"/>[/@]
		 [@b.col title="说明" width="15%"]<input name="config${config.id}.description" value="${config.description!}" style="width:100%"/>[/@]
		 [@b.col title="删除" width="8%"]<button onclick="remove(${config.id})">删除</button>[/@]
		[/@]
	[/@]
	[#if propertyConfigs?size>0]<div align="center"><br/><br/><input type="button" onclick="save()" value="保存"/></div>[/#if]
	 [/@sj.accordionItem]

	[@sj.accordionItem title="新增参数"]
	<table class="grid" align="center" width="90%">
	   <tr>
		 <td width="20%"  class="title">参数名称*</td>
		 <td style="text-align:left"><input name="configNew.name"  value=""/></td>
	   </tr>
	   <tr>
		 <td class="title">参数值*</td>
		 <td><input name="configNew.value" value="" maxlength="300" style="width:100%"/></td>
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
		 <td colspan="2" align="center"><input type="button" onclick="save()" value="保存"/></td>
	   </tr>
	 </table>
	 </form>
	 [/@sj.accordionItem]

[/@sj.accordion]
<script type="text/javascript">
   var form = document.systemConfigForm;
   function save(){
	  form.action="${b.url('!save')}";
	  form.submit();
   }
   function remove(id){
	if(confirm("确定删除?")){
   		form.action="${b.url('!remove')}?config.id="+id;
   		form.submit();
   	}
   }
</script>
[@b.foot/]