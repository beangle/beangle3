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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdHierarchyObject;

/**
 * 代码分类
 *
 * @author chaostone
 * @version $Id: CodeCategory.java Jun 28, 2011 8:32:18 PM chaostone $
 */
@Entity(name = "org.beangle.ems.dictionary.model.CodeCategory")
public class CodeCategory extends  NumberIdHierarchyObject<CodeCategory,Integer> {

  private static final long serialVersionUID = -8865890399079481866L;

  /** 类别名称 */
  @NotNull
  @Size(max = 50)
  @Column(unique = true)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
