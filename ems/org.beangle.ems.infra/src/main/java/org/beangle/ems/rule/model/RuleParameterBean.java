/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.RuleParameter;
import org.beangle.model.pojo.LongIdObject;
@Entity(name="org.beangle.ems.rule.RuleParameter")
public class RuleParameterBean extends LongIdObject implements RuleParameter {

	private static final long serialVersionUID = -5534831174352027516L;

	/** 业务规则 */
	private Rule rule;

	/** 参数名称 */
	@NotNull
	@Size(max=100)
	private String name;

	/** 参数类型 */
	@NotNull
	@Size(max=100)
	private String type;

	/** 参数标题 */
	@NotNull
	@Size(max=100)
	private String title;

	/** 参数描述 */
	@NotNull
	@Size(max=100)
	private String description;

	/** 上级参数 */
	private RuleParameter parent;

	/** 所有的子参数 */
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private Set<RuleParameter> children = CollectUtils.newHashSet();

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

	public RuleParameter getParent() {
		return parent;
	}

	public void setParent(RuleParameter parent) {
		this.parent = parent;
	}

	public Set<RuleParameter> getChildren() {
		return children;
	}

	public void setChildren(Set<RuleParameter> children) {
		this.children = children;
	}

}
