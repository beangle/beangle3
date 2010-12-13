[#ftl]
[#include "/template/head.ftl"/]
 <body>
 <table id="userBar" width="100%"></table>
 [@sj.head /]
 [@sj.accordion id="accordion"]
	 [@sj.accordionItem title="固定参数"]
	 [@table.table id="saticTable" align="center" width="90%"]
	   [@table.thead]
	     [@table.td text="序号" width="10%"/]
	     [@table.td text="参数名称" width="20%"/]
	     [@table.td text="参数值" width="25%"/]
	   [/@]
	   [@table.tbody datas=staticNames;name,name_index]
	   	<td>${name_index+1}</td>
	   	<td>${name}</td>
	   	<td>${config.get(name)}</td>
	   [/@]
	 [/@]
	 [/@sj.accordionItem]
	  
	 [@sj.accordionItem title="可编辑参数"]
	 <form name="systemConfigForm" method="post" action="" onsubmit="return false;">
	 [@table.table id="listTable" align="center" width="95%"]
	   [@table.thead]
	     [@table.td text="序号" width="7%"/]
	     [@table.td text="参数名称" width="20%"/]
	     [@table.td text="类型" width="15%"/]
	     [@table.td text="参数值" width="35%"/]
	     [@table.td text="说明" width="15%"/]
	     [@table.td text="删除" width="8%"/]
	   [/@]
	   [@table.tbody datas=propertyConfigs;config,config_index]
	     <td>${config_index+1}</td>
	     <td style="text-align:left"><input name="config${config.id}.name"  value="${config.name}" style="width:100%"></td>
	     <td><input name="config${config.id}.type" value="${config.type!}" style="width:100%"></td>
	     <td><input name="config${config.id}.value" value="${config.value?default("")}" maxlength="300" style="width:100%"/></td>
	     <td><input name="config${config.id}.description" value="${config.description!}" style="width:100%"></td>
	     <td><button onclick="remove(${config.id})">删除</button></td>
	   [/@]
	 [/@]
	 <div align="center"><br/><br/><input type="button" onclick="save()" value="保存"></div>
	 [/@sj.accordionItem]
	 
	 [@sj.accordionItem title="新增参数"]
	 <table class="grid" align="center" width="90%">
	   <tr>
	     <td width="20%"  class="title">参数名称*</td>
	     <td style="text-align:left"><input name="configNew.name"  value=""></td>
	   </tr>
	   <tr>
	     <td class="title">参数值*</td>
	     <td><input name="configNew.value" value="" maxlength="300" style="width:100%"/></td>
	   </tr>
	   <tr>
	     <td class="title">类型*</td>
	     <td><input name="configNew.type" value=""></td>
	   </tr>
	   <tr>
	     <td class="title">说明</td>
	     <td><input name="configNew.description" value="" style="width:100%"></td>
	   </tr>
	   <tr>
	     <td colspan="2" align="center"><input type="button" onclick="save()" value="保存"></td>
	   </tr>
	 </table>
	 </form>
	 [/@sj.accordionItem]

 [/@sj.accordion]
<script>
   var bar = new ToolBar('userBar','&nbsp;系统参数',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addBack();
   
   var form = document.systemConfigForm;
   function save(){
      form.action="${base}/system/property!save.action";
      form.submit();
   }
   function remove(id){
    if(confirm("确定删除?")){
   		form.action="${base}/system/property!remove.action?config.id="+id;
   		form.submit();
   	}
   }
</script>
[#include "/template/foot.ftl"/]