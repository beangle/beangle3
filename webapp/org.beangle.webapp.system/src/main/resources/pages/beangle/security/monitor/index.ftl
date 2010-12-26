[#ftl]
[#include "/template/head.ftl"/]
<BODY>
[@sj.head /]
<table id="menuBar"></table>
[@sj.tabbedpanel id="monitorTabs" disabledTabs="[]" collapsible="true"]
	[@sj.tab id="monitorTab1" label="当前会话" href="${base}/security/monitor!activities.action"/]
	[@sj.tab id="monitorTab2" label="会话配置" href="${base}/security/monitor!profiles.action"/]
	[@sj.tab id="monitorTab3" label="历史会话" href="${base}/security/activity.action"/]
	[@sj.tab id="monitorTab4" label="访问日志" href="${base}/security/monitor!accesslogs.action?ordreBy=duration desc"/]
[/@]
<script>
 var menuBar=bg.ui.toolbar('menuBar','<a href="dashboard.action">权限管理</a>-->访问监控',null,true,true);
 menuBar.addHelp();
 //标签卡刷新定时器
 var refreshTime=null;
</script>
</body>
[#include "/template/foot.ftl"/]
