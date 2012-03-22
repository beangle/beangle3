/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.rule.RuleParameter;

/**
 * 规则参数配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.rule.model.RuleConfigParam")
public class RuleConfigParam extends LongIdObject {

	private static final long serialVersionUID = 8711866530914907008L;

	/** 标准-规则 */
	@NotNull
	@ManyToOne
	private RuleConfig config;

	/** 规则参数 */
	@NotNull
	@ManyToOne
	private RuleParameter param;

	/** 参数值 */
	@NotNull
	@Size(max = 500)
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
