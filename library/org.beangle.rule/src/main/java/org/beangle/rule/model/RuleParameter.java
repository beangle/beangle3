/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.model;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.rule.Parameter;
import org.beangle.rule.Rule;

public class RuleParameter extends LongIdObject implements Parameter {

	private static final long serialVersionUID = -5534831174352027516L;

	/** 业务规则 */
	private Rule rule;

	/** 参数名称 */
	private String name;

	/** 参数类型 */
	private String type;

	/** 参数标题 */
	private String title;

	/** 参数描述 */
	private String description;

	/** 上级参数 */
	private Parameter parent;

	/** 所有的子参数 */
	private Set<Parameter> children = CollectUtils.newHashSet();

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Parameter getParent() {
		return parent;
	}

	public void setParent(Parameter parent) {
		this.parent = parent;
	}

	public Set<Parameter> getChildren() {
		return children;
	}

	public void setChildren(Set<Parameter> children) {
		this.children = children;
	}

}
