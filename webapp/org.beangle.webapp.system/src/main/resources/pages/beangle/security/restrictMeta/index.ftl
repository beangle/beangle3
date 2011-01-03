[#ftl]
[@b.xhtmlhead/]
<body>
[@sj.head /]
<table id="menuBar"></table>
[@sj.tabbedpanel id="tabs"]
	[@sj.tab id="tab1" label="限制模式" href="restrict-meta!patterns.action"/]
	[@sj.tab id="tab2" label="限制参数" href="restrict-meta!fields.action"/]
[/@]
<script type="text/javascript">
 var menuBar=bg.ui.toolbar('menuBar','<a href="dashboard.action">权限管理</a>-->限制模式和参数');
 menuBar.addHelp();
</script>
</body>
[#include "/template/foot.ftl"/]
