/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.rule.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.RuleParameter;

/**
 * 规则
 *
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.rule.Rule")
@Cacheable
public class RuleBean extends NumberIdTimeObject<Integer> implements Rule {

  private static final long serialVersionUID = -3648535746761474692L;

  /** 规则名称 */
  @NotNull
  @Size(max = 100)
  @Column(unique = true)
  private String name;

  /** 适用业务 */
  @NotNull
  @Size(max = 100)
  private String business;

  /** 规则描述 */
  @NotNull
  @Size(max = 300)
  private String description;

  /** 规则管理容器 */
  @NotNull
  @Size(max = 50)
  private String factory;

  /** 规则服务名 */
  @NotNull
  @Size(max = 80)
  private String serviceName;

  /** 规则参数集合 */
  @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
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
