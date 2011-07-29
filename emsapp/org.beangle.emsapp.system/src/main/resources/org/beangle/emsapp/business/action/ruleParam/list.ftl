[#ftl/][@b.head/]
[@b.grid items=ruleParameters var="ruleParameter"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col title="参数名称" property="name"/]
		[@b.col title="参数类型" property="type"/]
		[@b.col title="上级参数" property="parent.name"/]
		[@b.col title="规则名称" property="rule.name"/]
	[/@]
[/@]
[@b.foot/]
