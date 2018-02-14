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
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.engine.RuleExecutor;
import org.beangle.ems.rule.engine.RuleExecutorBuilder;
import org.beangle.ems.rule.model.RuleConfig;
import org.beangle.ems.rule.model.RuleConfigParam;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DefaultRuleExecutorBuilder implements RuleExecutorBuilder, ApplicationContextAware {

  ApplicationContext appContext;

  public static final String SPRING = "spring";

  public static final String BEAN = "bean";

  public RuleExecutor build(Rule rule) {
    if (SPRING.equals(rule.getFactory())) {
      return (RuleExecutor) appContext.getBean(rule.getServiceName());
    } else if (BEAN.equals(rule.getFactory())) {
      try {
        return (RuleExecutor) Class.forName(rule.getServiceName()).newInstance();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return null;
    }
  }

  public RuleExecutor build(List<Rule> rules, boolean stopWhenFail) {
    CompositeExecutor composite = new CompositeExecutor();
    composite.setStopWhenFail(stopWhenFail);
    for (final Rule rule : rules) {
      composite.add(build(rule));
    }
    return composite;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.appContext = applicationContext;
  }

  public RuleExecutor build(RuleConfig ruleConfig) {
    RuleExecutor executor = build(ruleConfig.getRule());
    if (null == executor) { return null; }
    Map<String, Object> map = CollectUtils.newHashMap();
    for (RuleConfigParam configParam : ruleConfig.getParams()) {
      map.put(configParam.getParam().getName(), configParam.getValue());
    }
    Model.populate(executor, map);
    return executor;
  }

}
