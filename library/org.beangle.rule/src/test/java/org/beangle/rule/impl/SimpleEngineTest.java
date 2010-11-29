/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.impl;

import org.beangle.rule.Context;
import org.beangle.rule.Rule;
import org.beangle.rule.RuleBase;
import org.beangle.rule.engine.Engine;
import org.beangle.rule.engine.RuleExecutorBuilder;
import org.beangle.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beangle.rule.engine.impl.FullPatternMatcher;
import org.beangle.rule.engine.impl.SimpleEngine;
import org.beangle.rule.model.BusinessRule;
import org.beangle.rule.model.SimpleContext;
import org.beangle.test.spring.SpringTestCase;

public class SimpleEngineTest extends SpringTestCase {

	public void testEngine() {
		Context context = new SimpleContext();
		Engine engine = new SimpleEngine();
		RuleBase ruleBase = new TestRuleBase();
		// Rule rule1 = new BusinessRule();
		// rule1.setFactory(DefaultRuleExecutorBuilder.SPRING);
		// rule1.setServiceName("ruleExecutor1");

		Rule rule2 = new BusinessRule();
		rule2.setFactory(DefaultRuleExecutorBuilder.BEAN);
		rule2.setServiceName("org.beangle.rule.impl.RuleExecutor2");

		// ruleBase.getRules().add(rule1);
		ruleBase.getRules().add(rule2);
		engine.setRuleExecutorBuilder((RuleExecutorBuilder) applicationContext
				.getBean("ruleExecutorBuilder"));
		engine.setRuleBase(ruleBase);
		engine.setPatternMatcher(new FullPatternMatcher());
		engine.execute(context);
	}
}
