<table id="accesslogBar"></table>
<script>
	var bar = new ToolBar('accesslogBar','系统资源访问记录(耗时最长的500条)',null,true,true);
	bar.addPrint("<@msg.text "action.print"/>");  
</script>
<@table.table width="100%" sortable="true" id="accesslogTable" target="ui-tabs-4">
	<@table.thead>
		<td width="5%">序号</td>
		<@table.sortTd  width="30%" text="URI" id="uri" />
		<@table.sortTd  width="10%" text="帐号" id="user" />
		<@table.sortTd  width="20%" text="开始~结束" id="beginAt" />
		<@table.sortTd  width="10%" text="持续时间(ms)" id="duration"/>
	</@>
	<@table.tbody datas=accesslogs;accesslog,accesslog_index>
		<td>${accesslog_index+1}</td>
		<td title="${accesslog.params!}">${accesslog.uri!}</td>
		<td><#if accesslog.user??><A target="_blank" href="user.action?method=info&loginName=${accesslog.user}">${(accesslog.user)}</A></#if></td>
		<td>${accesslog.beginTime?string("yy-MM-dd")}  ${accesslog.beginTime?string("HH:mm:ss")}~${(accesslog.endTime?string("HH:mm:ss"))!}</td>
		<td>${accesslog.duration!}</td>
	</@>
</@>
<#if (accesslogs?size==0)>没有记录可能是由于没有启用资源访问过滤器.</#if>
<script>
	function refresh(){
		goPage("accesslogTable");
	}
	if(typeof refreshTime != undefined){
		clearTimeout(refreshTime);
	}
	refreshTime=setTimeout(refresh,${Parameters['interval']!"5"}*1000);

</script>
