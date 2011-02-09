[#ftl]
[@b.head/]
[@b.toolbar title="<a href='${b.url('index')}'>权限管理</a>-->访问监控"]
	bar.addHelp();
	//标签卡刷新定时器
	var refreshTime=null;
[/@]
[@sj.tabbedpanel id="monitorTabs"]
	[@sj.tab id="monitorTab1" label="当前会话" href="${b.url('!activities')}"/]
	[@sj.tab id="monitorTab2" label="会话配置" href="${b.url('!profiles')}"/]
	[@sj.tab id="monitorTab3" label="历史会话" href="${b.url('/security/activity')}"/]
	[@sj.tab id="monitorTab4" label="访问日志" href="${b.url('!accesslogs?ordreBy=duration desc')}"/]
[/@]
[@b.foot/]