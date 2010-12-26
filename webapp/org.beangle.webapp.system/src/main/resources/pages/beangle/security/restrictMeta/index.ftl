[#ftl]
[#include "/template/head.ftl"/]
<BODY>
[@sj.head /]
<table id="menuBar"></table>
[@sj.tabbedpanel id="tabs"]
	[@sj.tab id="tab1" label="限制模式" href="restrict-meta!patterns.action"/]
	[@sj.tab id="tab2" label="限制参数" href="restrict-meta!fields.action"/]
[/@]
<script>
 var menuBar=bg.ui.toolbar('menuBar','<a href="dashboard.action">权限管理</a>-->限制模式和参数',null,true,true);
 menuBar.addHelp();
</script>
</body>
[#include "/template/foot.ftl"/]
