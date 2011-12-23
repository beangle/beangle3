/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.rule.engine.impl;

import java.util.List;
import java.util.Map;

import org.beangle.collection.CollectUtils;
import org.beangle.emsapp.rule.Rule;
import org.beangle.emsapp.rule.engine.RuleExecutor;
import org.beangle.emsapp.rule.engine.RuleExecutorBuilder;
import org.beangle.emsapp.rule.model.RuleConfig;
import org.beangle.emsapp.rule.model.RuleConfigParam;
import org.beangle.model.entity.Model;
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
		Model.populate(map, executor);
		return executor;
	}

}
