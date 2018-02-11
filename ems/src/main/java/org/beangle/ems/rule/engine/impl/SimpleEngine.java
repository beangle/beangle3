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
