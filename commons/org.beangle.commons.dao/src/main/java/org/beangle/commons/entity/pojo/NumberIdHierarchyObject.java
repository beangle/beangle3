/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.entity.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

/**
 * @author chaostone
 * @version $Id: NumberIdHierarchyObject.java Jul 29, 2011 1:18:17 AM chaostone $
 */
@MappedSuperclass
public abstract class NumberIdHierarchyObject<T, ID extends Number> extends NumberIdObject<ID> implements
    HierarchyEntity<T, ID>, Comparable<T> {
  private static final long serialVersionUID = -968320812584144969L;

  /** 代码 */
  @Size(max = 30)
  @NotNull
  protected String code;

  /** 父级菜单 */
  @ManyToOne(fetch = FetchType.LAZY)
  public T parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  @OrderBy("code")
  protected List<T> children = CollectUtils.newArrayList();

  public T getParent() {
    return parent;
  }

  public void setParent(T parent) {
    this.parent = parent;
  }

  public List<T> getChildren() {
    return children;
  }

  public void setChildren(List<T> children) {
    this.children = children;
  }

  public int getDepth() {
    return (null == getParent()) ? 1 : getParentNode().getDepth() + 1;
  }

  public String getIndexno() {
    String indexno = Strings.substringAfterLast(code, ".");
    if (Strings.isEmpty(indexno)) {
      indexno = code;
    }
    return indexno;
  }

  public void generateCode(String indexno) {
    if (null == getParent()) {
      this.code = indexno;
    } else {
      this.code = Strings.concat(getParentNode().getCode(), ".", indexno);
    }
  }

  public void generateCode() {
    if (null != getParent()) {
      this.code = Strings.concat(getParentNode().getCode(), ".", getIndexno());
    }
  }

  protected NumberIdHierarchyObject<?, ?> getParentNode() {
    return (NumberIdHierarchyObject<?, ?>) getParent();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  /**
   * 不同级的菜单按照他们固有的级联顺序排序.
   */
  public int compareTo(T other) {
    return getCode().compareTo(((NumberIdHierarchyObject<?, ?>) other).getCode());
  }

}
