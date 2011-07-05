[#ftl/][@b.head/]
[@b.form action="!save" title="参数信息" theme="list"]
	[@b.textfield label="参数名称" name="ruleParameter.name" required="true" value=ruleParameter.name! maxlength="50"/]
	[@b.textfield label="参数类型" name="ruleParameter.type" required="true" value=ruleParameter.type!/]
	[@b.select label="上级参数" items=ruleParams value=(ruleParameter.parent.id)! name="ruleParameter.parent.id" empty="..."/]
	[@b.textfield label="标题" name="ruleParameter.title" required="true" value=ruleParameter.title! maxlength="50"/]
	[@b.textfield label="参数描述" name="ruleParameter.description" required="true" value=ruleParameter.description! maxlength="100"/]
	
	[@b.formfoot]
	<input type="hidden" value="${(ruleParameter.rule.id)}" name="ruleParameter.rule.id"/>
	<input type="hidden" value="${(ruleParameter.id)!}" name="ruleParameter.id"/>
	[@b.redirectParams/][@b.submit value="action.submit"/]
	[/@]
[/@]
[@b.foot/]
