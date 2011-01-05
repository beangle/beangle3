[#ftl]
[@b.xhtmlhead/]

[@sj.head /]
[@b.toolbar id="menuBar" title='<a href="dashboard.action">权限管理</a>-->访问监控']
	bar.addHelp();
	//标签卡刷新定时器
	var refreshTime=null;
[/@]
<table id=""></table>
[@sj.tabbedpanel id="monitorTabs"]
	[@sj.tab id="monitorTab1" label="当前会话" href="${base}/security/monitor!activities.action"/]
	[@sj.tab id="monitorTab2" label="会话配置" href="${base}/security/monitor!profiles.action"/]
	[@sj.tab id="monitorTab3" label="历史会话" href="${base}/security/activity.action"/]
	[@sj.tab id="monitorTab4" label="访问日志" href="${base}/security/monitor!accesslogs.action?ordreBy=duration desc"/]
[/@]

[#include "/template/foot.ftl"/]
