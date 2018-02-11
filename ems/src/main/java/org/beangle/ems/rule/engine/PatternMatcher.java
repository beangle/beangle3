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
 * 规则匹配器，用于匹配对应数据起作用的规则
 *
 * @author chaostone
 */
public interface PatternMatcher {

  /**
   * 根据规则集，判断哪些属于这次的执行范围
   *
   * @param base
   * @param context
   */
  Agenda buildAgenda(RuleBase base, Context context);
}
