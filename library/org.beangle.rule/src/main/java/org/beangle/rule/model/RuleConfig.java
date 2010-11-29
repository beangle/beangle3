/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.model;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.rule.Rule;

public class RuleConfig extends LongIdTimeObject {

	/** 业务规则 */
	private Rule rule;

	/** 是否启用 */
	private boolean enabled;

	/** 规则配置参数 */
	private Set<RuleConfigParam> params = CollectUtils.newHashSet();

	public Set<RuleConfigParam> getParams() {
		return params;
	}

	public void setParams(Set<RuleConfigParam> params) {
		this.params = params;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
