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

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.HierarchyEntity;
import org.beangle.commons.lang.Strings;

/**
 * @author chaostone
 * @version $Id: LongIdHierarchyObject.java Jul 29, 2011 1:18:17 AM chaostone $
 */
@MappedSuperclass
public abstract class HierarchyLongIdObject<T> extends LongIdObject implements HierarchyEntity<T, Long>,
    Comparable<T> {
  private static final long serialVersionUID = -968320812584144969L;

  /** 代码 */
  @Size(max = 30)
  @NotNull
  protected String code;

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

  protected HierarchyLongIdObject<?> getParentNode() {
    return (HierarchyLongIdObject<?>) getParent();
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
    return getCode().compareTo(((HierarchyLongIdObject<?>) other).getCode());
  }

}
