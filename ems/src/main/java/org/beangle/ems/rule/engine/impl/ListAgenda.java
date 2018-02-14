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
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.engine.Agenda;

/**
 * 顺序布置的规则执行顺序
 *
 * @author chaostone
 */
public class ListAgenda implements Agenda {

  private List<Rule> rules = CollectUtils.newArrayList();

  public ListAgenda() {
    super();
  }

  public ListAgenda(List<Rule> rules) {
    this.rules = rules;
  }

  public List<Rule> getRules() {
    return rules;
  }

  public void setRules(List<Rule> rules) {
    this.rules = rules;
  }

}
