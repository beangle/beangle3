[#ftl]
<style type="text/css">
.data {margin:0 auto;padding-bottom: 5px;overflow-y:auto;}
</style>
<script type="text/javascript">
	function executeSql(isBatch){
		var form=document.form1;
		form.action=${b.url('query')}";
		bg.form.submit(form);
	}
</script>
[#if !(Parameters['sql']??)]
[@sj.tabbedpanel id="localtabs" ]
	[@sj.tab id="tab1" target="t1" label="SQL语句"/]
	[@sj.tab id="tab2" href="${b.url('!history')}" label="SQL历史"/]
	<div id="t1">
	<form name="form1" method="post" action="${b.url('query')}" onsubmit="return false;" target="sqlResult">
	  <table cellpadding="1" cellspacing="1">
		<tr>
		  <td>
		  <textarea name="sql" cols="40" rows="5" id="sql" style="width:100%">${Parameters['sql']!}</textarea>
		  </td>
		</tr>
		<tr>
		  <td align="right">
		  输入sql语句进行查询,使用分号分隔多条语句。更新语句自动提交，无法会滚!
		  <button onclick="executeSql(1)">-执行-</button>
		  <input type="checkbox" value="1" name="isBatch"/>批量
		  </td>
		</tr>
	  </table>
	</form>
	</div>
[/@]
<div id="sqlResult"></div>
[#else]

[#if (results?size>0)]
<label style="padding-left:20px;"><em>查询结果</em></label>
[@sj.tabbedpanel id="datatabs"]
[#list results  as result]
	[@sj.tab id="datatab${result_index}" target="datat${result_index}" label="sql${result_index+1}"/]
	<div id="datat${result_index}" class="data">
	${result.sql}
	[#if result.datas?? && (result.datas?size>0)]
		<button onclick="exportData(${result_index},0)">-导出全部-</button>
		<button onclick="exportData(${result_index},1)">-导出本页-</button>
	[/#if]
	[#if result.datas??]
		[#if (result.datas?size>0)]
		[@b.grid id="table${result_index}" items=result.datas  var="data" width="100%" sortable="false"]
			[@b.row]
				[@b.col width="5%" name="序号"]${data_index+1}[/@]
				[#list result.columns?if_exists as columnName]
				[@b.col name=columnName ]
				[#if data[columnName]??][#if data[columnName]?is_boolean]${data[columnName]?string("1","0")}[#else]${data[columnName]}[/#if][/#if]
				[/@]
				[/#list]
			[/@]
		[/@]
		[#else]
			无数据
		[/#if]
	[#else]
		[#if (result.updateCount>0)]影响记录数:<em>${result.updateCount}</em>[/#if]
	[/#if]
	[#if result.msg??]
	[@sj.div id="msg" cssClass="result ui-widget-content ui-corner-all data" ]${result.msg?html}[/@]
	[/#if]
	</div>
[/#list]
[/@]
[/#if]

<form name="exportForm" target="contentFrame" method="post"></form>
<iframe  src="#" id="contentFrame" name="contentFrame"
	  marginwidth="0" marginheight="0"
	  scrolling="no" frameborder="0"></iframe>
<script type="text/javascript">
	var keys=[];
	var sqls=[];
	[#list results as result]
	[#if result.columns??]
	keys[${result_index}]="[#list result.columns as columnName]${columnName}[#if columnName_has_next],[/#if][/#list]";
	sqls[${result_index}]="${result.sql?js_string}";
	[/#if]
	[/#list]
	function exportData(sqlIndex,thisPage){
		var form=document.exportForm;
		addInput(form,"keys",keys[sqlIndex]);
		addInput(form,"titles",keys[sqlIndex]);
		form.action="${b.url('!export')}";
		addInput(form,"thisPage",thisPage);
		addInput(form,"sql",sqls[sqlIndex]);
		form.submit();
	}
</script>
[/#if]