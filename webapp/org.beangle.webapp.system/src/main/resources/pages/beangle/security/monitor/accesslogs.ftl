[#ftl]
[@b.toolbar title="系统资源访问记录(耗时最长的500条)"/]
[@b.grid id="accesslogTable" items=accesslogs var="accesslog"]
	[@b.gridbar]
	bar.addPrint("${b.text("action.print")}");
	[/@]
	[@b.row]
		[@b.col width="5%" title="序号"]${accesslog_index+1}[/@]
		[@b.col width="25%" title="URI" align="left" property="uri"]<span title="${accesslog.params!}">${accesslog.uri!}</span>[/@]
		[@b.col width="10%" title="帐号" property="user.name"][#if accesslog.user??][@b.a target="_blank" href="user!info?name=${accesslog.user.name}"]${(accesslog.user.name)}[/@][/#if][/@]
		[@b.col width="15%" title="地址" property="user.details.agent.ip"/]
		[@b.col width="10%" title="操作系统" property="user.details.agent.os"/]
		[@b.col width="10%" title="浏览器" property="user.details.agent.browser"/]
		[@b.col width="15%" title="开始~结束" property="beginAt" ]<span title="${accesslog.beginTime?string("yyyy-MM-dd")}">${accesslog.beginTime?string("HH:mm:ss")}~${(accesslog.endTime?string("HH:mm:ss"))!}</span>[/@]
		[@b.col width="10%" title="持续时间(ms)" property="duration"/]
	[/@]
[/@]
[#if (accesslogs?size==0)]没有记录可能是由于没有启用资源访问过滤器.[/#if]
<script type="text/javascript">
	function refresh(){
		if(document.getElementById("accesslogTable")){
			bg.page.goPage("accesslogTable");
		}else{
			if(typeof refreshTime != undefined){
				clearTimeout(refreshTime);
			}
		}
	}
	if(typeof refreshTime != undefined){
		clearTimeout(refreshTime);
	}
	refreshTime=setTimeout(refresh,${Parameters['interval']!"5"}*1000);
</script>
