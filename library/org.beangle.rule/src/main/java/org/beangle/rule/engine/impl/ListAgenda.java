/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.engine.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.rule.Rule;
import org.beangle.rule.engine.Agenda;

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
