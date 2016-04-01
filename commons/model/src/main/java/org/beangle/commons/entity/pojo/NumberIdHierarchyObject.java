/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
import org.beangle.commons.entity.HierarchyEntity;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;

/**
 * @author chaostone
 * @version $Id: NumberIdHierarchyObject.java Jul 29, 2011 1:18:17 AM chaostone $
 */
@MappedSuperclass
public abstract class NumberIdHierarchyObject<T, ID extends Number> extends NumberIdObject<ID> implements
    HierarchyEntity<T, ID>, Comparable<T> {
  private static final long serialVersionUID = -968320812584144969L;

  /** index no */
  @Size(max = 30)
  @NotNull
  protected String indexno;

  /** 父级菜单 */
  @ManyToOne(fetch = FetchType.LAZY)
  public T parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  @OrderBy("indexno")
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

  public int getIndex() {
    String index = Strings.substringAfterLast(indexno, ".");
    if (Strings.isEmpty(index)) index = indexno;
    int idx = Numbers.toInt(index);
    if (idx <= 0) idx = 1;
    return idx;
  }

  public void genIndexno(String indexno) {
    if (null == getParent()) {
      this.indexno = indexno;
    } else {
      this.indexno = Strings.concat(getParentNode().getIndexno(), ".", indexno);
    }
  }

  public void genIndexno() {
    if (null != getParent())
      this.indexno = Strings.concat(getParentNode().getIndexno(), ".", String.valueOf(getIndex()));
  }

  protected NumberIdHierarchyObject<?, ?> getParentNode() {
    return (NumberIdHierarchyObject<?, ?>) getParent();
  }

  public String getIndexno() {
    return indexno;
  }

  public void setIndexno(String indexno) {
    this.indexno = indexno;
  }

  /**
   * 不同级的菜单按照他们固有的级联顺序排序.
   */
  public int compareTo(T other) {
    return getIndexno().compareTo(((NumberIdHierarchyObject<?, ?>) other).getIndexno());
  }

}
