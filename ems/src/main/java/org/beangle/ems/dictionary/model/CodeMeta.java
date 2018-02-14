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
package org.beangle.ems.dictionary.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;

/**
 * 登记系统使用的基础代码
 * </p>
 * 这些代码的名称、英文名称和全称类名
 *
 * @version $Id: Dictionary.java May 4, 2011 7:29:38 PM chaostone $
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.dictionary.model.CodeMeta")
@Cacheable
public class CodeMeta extends IntegerIdObject {

  private static final long serialVersionUID = -2272793754309992189L;

  /** 代码名称 */
  @Column(unique = true)
  @NotNull
  @Size(max = 50)
  private String name;

  /** 中文名称 */
  @NotNull
  @Size(max = 100)
  private String title;

  /** 类名 */
  @NotNull
  @Size(max = 100)
  private String className;

  /** 所在分类 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CodeCategory category;

  /**
   * 代码名称
   *
   * @return 名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置代码名称
   *
   * @param name
   *          新的代码名称
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 查询代码的类名
   *
   * @return 代码的类路径全名
   */
  public String getClassName() {
    return className;
  }

  /**
   * 设置新的类名
   *
   * @param className
   *          类路径全称
   */
  public void setClassName(String className) {
    this.className = className;
  }

  public CodeCategory getCategory() {
    return category;
  }

  public void setCategory(CodeCategory category) {
    this.category = category;
  }

}
