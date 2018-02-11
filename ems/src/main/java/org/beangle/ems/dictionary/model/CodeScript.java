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

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;

/**
 * 系统编码规则
 *
 * @author chaostone
 * @version $Id: CodeScript.java May 5, 2011 3:56:23 PM chaostone $
 */
@Entity(name = "org.beangle.ems.dictionary.model.CodeScript")
public class CodeScript extends NumberIdTimeObject<Integer> {

  private static final long serialVersionUID = -2771592539431465165L;

  /** 编码对象 */
  @NotNull
  @Size(max = 40)
  private String codeName;

  /** 编码属性 */
  @NotNull
  @Size(max = 20)
  private String attr;

  /** 编码对象的类名 */
  @NotNull
  @Size(max = 100)
  private String codeClassName;

  /** 编码脚本 */
  @NotNull
  @Size(max = 1000)
  private String script;

  /** 编码简要描述 */
  @NotNull
  @Size(max = 100)
  private String description;

  public CodeScript() {
    super();
  }

  public CodeScript(String codeClassName, String attr, String script) {
    this.codeClassName = codeClassName;
    this.attr = attr;
    this.script = script;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getCodeClassName() {
    return codeClassName;
  }

  public void setCodeClassName(String codeClassName) {
    this.codeClassName = codeClassName;
  }

  public String getCodeName() {
    return codeName;
  }

  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public String getAttr() {
    return attr;
  }

  public void setAttr(String attr) {
    this.attr = attr;
  }

}
