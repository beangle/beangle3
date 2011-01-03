[#ftl]
<table id="accesslogBar"></table>
<script type="text/javascript">
	var bar = bg.ui.toolbar('accesslogBar','系统资源访问记录(耗时最长的500条)');
	bar.addPrint("[@b.text "action.print"/]");
</script>
[@b.grid width="100%"  id="accesslogTable" target="ui-tabs-4"]
	[@b.gridhead]
		<td width="5%">序号</td>
		[@b.gridth  width="20%" text="URI" sort="uri" /]
		[@b.gridth  width="10%" text="帐号" sort="user.name" /]
		[@b.gridth  width="15%" text="地址" sort="user.details.agent.ip"/]
		[@b.gridth  width="10%" text="操作系统" sort="user.details.agent.os"/]
		[@b.gridth  width="10%" text="浏览器" sort="user.details.agent.name"/]
		[@b.gridth  width="20%" text="开始~结束" sort="beginAt" /]
		[@b.gridth  width="10%" text="持续时间(ms)" sort="duration"/]
	[/@]
	[@b.gridbody datas=accesslogs;accesslog,accesslog_index]
		<td>${accesslog_index+1}</td>
		<td title="${accesslog.params!}">${accesslog.uri!}</td>
		[#if accesslog.user??]
		<td><a target="_blank" href="user.action?method=info&loginName=${accesslog.user.name}">${(accesslog.user.name)}</a></td>
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
<script type="text/javascript">
	function refresh(){
		bg.page.goPage("accesslogTable");
	}
	if(typeof refreshTime != undefined){
		clearTimeout(refreshTime);
	}
	refreshTime=setTimeout(refresh,${Parameters['interval']!"5"}*1000);

</script>
