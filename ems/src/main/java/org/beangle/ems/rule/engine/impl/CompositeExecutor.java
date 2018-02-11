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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.engine.RuleExecutor;

/**
 * 组合规则执行者
 *
 * @author chaostone
 */
public class CompositeExecutor implements RuleExecutor {

  private List<RuleExecutor> executors = CollectUtils.newArrayList();

  /** 是否在单个规则失败后停止 默认为否 */
  private boolean stopWhenFail = false;

  public boolean execute(Context context) {
    boolean result = true;
    for (final RuleExecutor executor : executors) {
      result &= executor.execute(context);
      if (stopWhenFail && !result) { return result; }
    }
    return result;
  }

  public void add(RuleExecutor executor) {
    executors.add(executor);
  }

  public List<RuleExecutor> getExecutors() {
    return executors;
  }

  public void setExecutors(List<RuleExecutor> executors) {
    this.executors = executors;
  }

  public boolean isStopWhenFail() {
    return stopWhenFail;
  }

  public void setStopWhenFail(boolean stopWhenFailure) {
    this.stopWhenFail = stopWhenFailure;
  }

}
