/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.engine.impl;

import java.util.List;

import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.RuleBase;
import org.beangle.ems.rule.engine.Agenda;
import org.beangle.ems.rule.engine.Engine;
import org.beangle.ems.rule.engine.PatternMatcher;
import org.beangle.ems.rule.engine.RuleExecutor;
import org.beangle.ems.rule.engine.RuleExecutorBuilder;

public class SimpleEngine implements Engine {

	protected PatternMatcher matcher;

	protected RuleBase base;

	protected RuleExecutorBuilder executorBuilder;

	protected boolean stopWhenFail = false;

	public void execute(Context context) {
		Agenda agenda = matcher.buildAgenda(base, context);
		List<Rule> rules = agenda.getRules();
		RuleExecutor executor = executorBuilder.build(rules, stopWhenFail);
		executor.execute(context);
	}

	public PatternMatcher getPatternMatcher() {
		return matcher;
	}

	public void setPatternMatcher(PatternMatcher matcher) {
		this.matcher = matcher;
	}

	public RuleBase getRuleBase() {
		return base;
	}

	public void setRuleBase(RuleBase base) {
		this.base = base;
	}

	public RuleExecutorBuilder getRuleExecutorBuilder() {
		return executorBuilder;
	}

	public void setRuleExecutorBuilder(RuleExecutorBuilder executorBuilder) {
		this.executorBuilder = executorBuilder;
	}

	public boolean isStopWhenFail() {
		return stopWhenFail;
	}

	public void setStopWhenFail(boolean stopWhenFail) {
		this.stopWhenFail = stopWhenFail;
	}

}
