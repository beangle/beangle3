/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.model;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.rule.Context;
import org.beangle.rule.Rule;
import org.beangle.rule.engine.RuleExecutor;
import org.beangle.rule.engine.RuleExecutorBuilder;
import org.beangle.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beangle.test.spring.SpringTestCase;

public class RuleTest extends SpringTestCase {

	// public void testSpringBuilder() {
	// RuleExecutorBuilder builder = (DefaultRuleExecutorBuilder)
	// applicationContext
	// .getBean("ruleExecutorBuilder");
	// Rule rule = (Rule) Model.newInstance(Rule.class);
	// rule.setFactory(DefaultRuleExecutorBuilder.SPRING);
	// rule.setServiceName("ruleExecutor1");
	// RuleExecutor exceutor = builder.build(rule);
	// Context context = new SimpleContext();
	// exceutor.execute(context);
	// }

	public void testComposite() {
		RuleExecutorBuilder builder = (DefaultRuleExecutorBuilder) applicationContext
				.getBean("ruleExecutorBuilder");
		List<Rule> rules = CollectUtils.newArrayList();
		// Rule rule1 = (Rule) Model.newInstance(Rule.class);
		// rule1.setFactory(DefaultRuleExecutorBuilder.SPRING);
		// rule1.setServiceName("ruleExecutor1");

		Rule rule2 = new BusinessRule();
		rule2.setFactory(DefaultRuleExecutorBuilder.BEAN);
		rule2.setServiceName("org.beangle.rule.impl.RuleExecutor2");

		// rules.add(rule1);
		rules.add(rule2);

		Context context = new SimpleContext();
		RuleExecutor exceutor = builder.build(rules, false);
		exceutor.execute(context);
	}
}
