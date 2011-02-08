/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.rule.Parameter;

public class RuleConfigParam extends LongIdObject {

	private static final long serialVersionUID = 8711866530914907008L;

	/** 标准-规则 */
	private RuleConfig ruleConfig;

	/** 规则参数 */
	private Parameter param;

	/** 参数值 */
	private String value;

	public Parameter getParam() {
		return param;
	}

	public void setParam(Parameter param) {
		this.param = param;
	}

	public RuleConfig getRuleConfig() {
		return ruleConfig;
	}

	public void setRuleConfig(RuleConfig ruleConfig) {
		this.ruleConfig = ruleConfig;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
