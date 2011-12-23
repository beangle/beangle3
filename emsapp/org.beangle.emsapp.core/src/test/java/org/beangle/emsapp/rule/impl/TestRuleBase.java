/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.rule.impl;

import java.util.List;

import org.beangle.collection.CollectUtils;
import org.beangle.emsapp.rule.Rule;
import org.beangle.emsapp.rule.RuleBase;

public class TestRuleBase implements RuleBase {

	List<Rule> rules = CollectUtils.newArrayList();

	public List<Rule> getRules() {
		return rules;
	}

}
