[#ftl]
[@b.head/]
[@b.toolbar]
bar.setTitle('[@b.a href='index']权限管理[/@]-->限制模式和参数');
bar.addHelp();
[/@]
[@sj.tabbedpanel id="tabs"]
	[@sj.tab id="tab1" label="限制模式" href="${b.url('!patterns')}"/]
	[@sj.tab id="tab2" label="限制参数" href="${b.url('!fields')}"/]
[/@]
[@b.foot/]
