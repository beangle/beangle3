[#ftl]
[@b.head/]
<table id="menuBar"></table>
[@sj.tabbedpanel id="tabs"]
	[@sj.tab id="tab1" label="限制模式" href="${b.url('!patterns')}"/]
	[@sj.tab id="tab2" label="限制参数" href="${b.url('!fields')}"/]
[/@]
<script type="text/javascript">
 var menuBar=bg.ui.toolbar('menuBar','<a href="${b.url('dashboard')}">权限管理</a>-->限制模式和参数');
 menuBar.addHelp();
</script>

[@b.foot/]
