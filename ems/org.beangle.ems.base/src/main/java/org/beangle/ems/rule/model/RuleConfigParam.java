/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.ems.rule.RuleParameter;
import org.beangle.model.pojo.LongIdObject;
@Entity
public class RuleConfigParam extends LongIdObject {

	private static final long serialVersionUID = 8711866530914907008L;

	/** 标准-规则 */
	@NotNull
	private RuleConfig config;

	/** 规则参数 */
	@NotNull
	private RuleParameter param;

	/** 参数值 */
	@NotNull
	private String value;

	public RuleParameter getParam() {
		return param;
	}

	public void setParam(RuleParameter param) {
		this.param = param;
	}

	public RuleConfig getConfig() {
		return config;
	}

	public void setConfig(RuleConfig config) {
		this.config = config;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
