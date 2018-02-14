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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.RuleParameter;

/**
 * 规则参数
 *
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.rule.RuleParameter")
@Cacheable
public class RuleParameterBean extends IntegerIdObject implements RuleParameter {

  private static final long serialVersionUID = -5534831174352027516L;

  /** 业务规则 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Rule rule;

  /** 参数名称 */
  @NotNull
  @Size(max = 100)
  private String name;

  /** 参数类型 */
  @NotNull
  @Size(max = 100)
  private String type;

  /** 参数标题 */
  @NotNull
  @Size(max = 100)
  private String title;

  /** 参数描述 */
  @NotNull
  @Size(max = 100)
  private String description;

  /** 上级参数 */
  @ManyToOne(fetch = FetchType.LAZY)
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
