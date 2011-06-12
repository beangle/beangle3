/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.rule.RuleParameter;
import org.beangle.ems.rule.Rule;
import org.beangle.model.pojo.LongIdTimeObject;
@Entity(name="org.beangle.ems.rule.Rule")
public class RuleBean extends LongIdTimeObject implements Rule {

	private static final long serialVersionUID = -3648535746761474692L;

	/** 规则名称 */
	@NotNull
	@Size(max=100)
	@Column(unique=true)
	private String name;

	/** 适用业务 */
	@NotNull
	@Size(max=100)	
	private String business;

	/** 规则描述 */
	@NotNull
	@Size(max=300)
	private String description;

	/** 规则管理容器 */
	@NotNull
	@Size(max=50)
	private String factory;

	/** 规则服务名 */
	@NotNull
	@Size(max=80)
	private String serviceName;

	/** 规则参数集合 */
	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
	private Set<RuleParameter> params = CollectUtils.newHashSet();

	/** 是否启用 */
	@NotNull
	private boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Set<RuleParameter> getParams() {
		return params;
	}

	public void setParams(Set<RuleParameter> params) {
		this.params = params;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
