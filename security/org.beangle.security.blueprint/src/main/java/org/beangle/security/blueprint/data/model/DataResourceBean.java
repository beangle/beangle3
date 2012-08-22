/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.security.blueprint.data.DataResource;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统数据资源
 * 
 * @author chaostone
 * @since 2012-07-24
 */
@Entity(name = "org.beangle.security.blueprint.data.DataResource")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataResourceBean extends LongIdObject implements DataResource {
  private static final long serialVersionUID = -8285208615351119572L;

  /** 类型/名称 */
  @Size(max = 100)
  @NotNull
  @Column(unique = true)
  private String name;

  /** 标题 */
  @Size(max = 100)
  @NotNull
  private String title;

  /** 简单描述 */
  @Size(max = 100)
  private String remark;

  /** 允许的操作 */
  @Size(max = 100)
  private String actions;

  /** 能够访问哪些属性 */
  @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
  protected Set<DataField> fields = CollectUtils.newHashSet();

  /** 模块是否可用 */
  @NotNull
  private boolean enabled = true;

  public DataResourceBean() {
    super();
  }

  public DataResourceBean(Long id) {
    super(id);
  }

  public DataResourceBean(String name, String title) {
    this.name = name;
    this.title = title;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getActions() {
    return actions;
  }

  public void setActions(String actions) {
    this.actions = actions;
  }

  public Set<DataField> getFields() {
    return fields;
  }

  public void setFields(Set<DataField> fields) {
    this.fields = fields;
  }

  public String toString() {
    return new ToStringBuilder(this).append("name", this.name).append("id", this.id)
        .append("remark", this.remark).toString();
  }
}
