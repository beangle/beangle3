/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.rule.engine;

import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.RuleBase;

/**
 * 规则引擎<br>
 * 具体负责执行规则
 *
 * @author chaostone
 */
public interface Engine {

  void execute(Context context);

  void setPatternMatcher(PatternMatcher matcher);

  PatternMatcher getPatternMatcher();

  RuleBase getRuleBase();

  void setRuleBase(RuleBase base);

  RuleExecutorBuilder getRuleExecutorBuilder();

  void setRuleExecutorBuilder(RuleExecutorBuilder executorBuilder);

}
