[#ftl/][@b.head/]

[@b.grid items=rules var="rule"]
	[@b.gridbar title="dd"]
	bar.addItem("${b.text("action.info")}",action.info());
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.modify")}",action.edit());
	bar.addItem("${b.text("action.delete")}",action.remove());
	bar.addItem("编辑参数",action.single('params'));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col title="common.name"  property="name"/]
		[@b.col title="适用业务"  property="business"/]
		[@b.col title="管理容器"  property="factory" /]
		[@b.col title="服务名"  property="serviceName"/]
		[@b.col title="common.updatedAt"  property="updatedAt"]${(rule.updatedAt?string("yyyy-MM-dd"))?if_exists}[/@]
		[@b.col title="common.status"  property="enabled"]${rule.enabled?string("启用","禁用")?if_exists}[/@]
	[/@]
[/@]

[@b.foot/]
