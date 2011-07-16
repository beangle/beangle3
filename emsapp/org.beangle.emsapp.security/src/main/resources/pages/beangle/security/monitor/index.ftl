[#ftl]
[@b.head/]
<script>
	//标签卡刷新定时器
	var refreshTime=null;
</script>
[@b.tabs]
	[@b.tab label="当前会话" href="!activities"/]
	[@b.tab label="会话配置" href="!profiles"/]
	[@b.tab label="历史会话" href="/security/sessioninfo-log"/]
	[@b.tab label="访问日志" href="!accesslogs?ordreBy=duration%20desc"/]
[/@]
[@b.foot/]