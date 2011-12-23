/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.rule.engine;

import org.beangle.emsapp.rule.Context;
import org.beangle.emsapp.rule.RuleBase;

/**
 * 规则引擎<br>
 * 具体负责执行规则
 * 
 * @author chaostone
 */
public interface Engine {

	public void execute(Context context);

	public void setPatternMatcher(PatternMatcher matcher);

	public PatternMatcher getPatternMatcher();

	public RuleBase getRuleBase();

	public void setRuleBase(RuleBase base);

	public RuleExecutorBuilder getRuleExecutorBuilder();

	public void setRuleExecutorBuilder(RuleExecutorBuilder executorBuilder);

}
