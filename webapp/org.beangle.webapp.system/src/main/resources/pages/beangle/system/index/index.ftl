[#ftl]
[#include "/template/head.ftl"/]
[@sj.head /]
<table id="systemBar"></table>
<script>
   var bar = bg.ui.toolbar('systemBar','系统状态查看',null,true,true);
   bar.addHelp();
</script>
	[@sj.tabbedpanel id="systemTabs"]
		[@sj.tab id="systemTab1" href="${base}/system/status.action" label="系统状态"/]
		[@sj.tab id="systemTab2" href="${base}/system/status!properties.action" label="系统属性"/]
		[@sj.tab id="systemTab3" href="${base}/system/file.action" label="文件系统"/]
		[@sj.tab id="systemTab4" target="aboutMe" label="关于我"/]
		<div id="aboutMe">
		<li>ip:${clientProps['client.ip']}</li>
		<li>用户代理:${clientProps['client.useragent']}</li>
		</div>
	[/@]
[#include "/template/foot.ftl"/]