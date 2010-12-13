[#ftl]
<table id="accesslogBar"></table>
<script>
	var bar = new ToolBar('accesslogBar','系统资源访问记录(耗时最长的500条)',null,true,true);
	bar.addPrint("[@msg.text "action.print"/]");  
</script>
[@table.table width="100%" sortable="true" id="accesslogTable" target="ui-tabs-4"]
	[@table.thead]
		<td width="5%">序号</td>
		[@table.sortTd  width="20%" text="URI" id="uri" /]
		[@table.sortTd  width="10%" text="帐号" id="user.name" /]
		[@table.sortTd  width="15%" text="地址" id="user.details.agent.ip"/]
		[@table.sortTd  width="10%" text="操作系统" id="user.details.agent.os"/]
		[@table.sortTd  width="10%" text="浏览器" id="user.details.agent.name"/]
		[@table.sortTd  width="20%" text="开始~结束" id="beginAt" /]
		[@table.sortTd  width="10%" text="持续时间(ms)" id="duration"/]
	[/@]
	[@table.tbody datas=accesslogs;accesslog,accesslog_index]
		<td>${accesslog_index+1}</td>
		<td title="${accesslog.params!}">${accesslog.uri!}</td>
		[#if accesslog.user??]
		<td><A target="_blank" href="user.action?method=info&loginName=${accesslog.user.name}">${(accesslog.user.name)}</A></td>
		<td>${(accesslog.user.details.agent.ip)!}</td>
		<td>${(accesslog.user.details.agent.os)!}</td>
		<td>${(accesslog.user.details.agent.fullname)!}</td>
		[#else]
		<td></td><td></td><td></td><td></td>
		[/#if]
		<td title="${accesslog.beginTime?string("yyyy-MM-dd")}">${accesslog.beginTime?string("HH:mm:ss")}~${(accesslog.endTime?string("HH:mm:ss"))!}</td>
		<td>${accesslog.duration!}</td>
	[/@]
[/@]
[#if (accesslogs?size==0)]没有记录可能是由于没有启用资源访问过滤器.[/#if]
<script>
	function refresh(){
		goPage("accesslogTable");
	}
	if(typeof refreshTime != undefined){
		clearTimeout(refreshTime);
	}
	refreshTime=setTimeout(refresh,${Parameters['interval']!"5"}*1000);

</script>
