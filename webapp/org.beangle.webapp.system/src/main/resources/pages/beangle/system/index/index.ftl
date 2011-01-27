[#ftl]
[@b.head/]

[@b.toolbar id="systemBar" title="系统状态查看"]
	bar.addHelp();
[/@]
	[@sj.tabbedpanel id="systemTabs"]
		[@sj.tab id="systemTab1" href="${b.url('status')}" label="系统状态"/]
		[@sj.tab id="systemTab2" href="${b.url('status!properties')}" label="系统属性"/]
		[@sj.tab id="systemTab3" href="${b.url('file')}" label="文件系统"/]
		[@sj.tab id="systemTab4" target="aboutMe" label="关于我"/]
		<div id="aboutMe">
		<li>ip:${clientProps['client.ip']}</li>
		<li>用户代理:${clientProps['client.useragent']}</li>
		</div>
	[/@]

[@b.foot/]