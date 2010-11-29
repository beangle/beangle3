/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.rule.Rule;
import org.beangle.rule.RuleBase;

public class TestRuleBase implements RuleBase {

	List<Rule> rules = CollectUtils.newArrayList();

	public List<Rule> getRules() {
		return rules;
	}

}
