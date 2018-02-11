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
package org.beangle.commons.entity.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.TemporalOn;
import org.beangle.commons.lang.Objects;

/**
 * 基础代码的基类
 * </p>
 * 很对基础代码数据成员结构相似，仅在数据库中表名和列名 不一样，带都含有这些基类中规定的数据类型，所以把这种结构相似性抽出来，
 * 节省代码的编制量.每个子类代码仍要有自己的类型定义和数据库映射定义. 基类和数据库表没有映射关系，仅仅是数据抽象.
 * 
 * @author chaostone
 * @version $Id: BaseCode.java May 4, 2011 7:28:27 PM chaostone $
 */
@MappedSuperclass
@Cacheable
public abstract class Code<T extends Number> extends NumberIdObject<T>
    implements Comparable<Object>, TemporalOn {

  private static final long serialVersionUID = 5728157880502841506L;

  /**
   * 基础代码的代码关键字
   */
  @Column(unique = true)
  @NotNull
  @Size(max = 32)
  protected String code;

  /**
   * 代码中文名称
   */
  @NotNull
  @Size(max = 100)
  protected String name;

  /**
   * 代码英文名称
   */
  @Size(max = 100)
  protected String enName;

  /**
   * 生效时间
   */
  @NotNull
  protected java.sql.Date beginOn;

  /**
   * 失效时间
   */
  protected java.sql.Date endOn;

  /** 最后修改时间 */
  protected Date updatedAt;

  public Code() {
  }

  public Code(T id) {
    this.id = id;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * 查询基础代码是否具有扩展属性，一般供子类使用。
   */
  public boolean hasExtPros() {
    Field[] fields = getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if (!(Modifier.isFinal(fields[i].getModifiers())
          || Modifier.isStatic(fields[i].getModifiers()))) { return true; }
    }
    return false;
  }

  /**
   * 获得代码
   * 
   * @return 代码
   */
  public String getCode() {
    return code;
  }

  /**
   * 设置代码
   * 
   * @param code 代码
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * 获得名称
   * 
   * @return 名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置名称
   * 
   * @param name 名称
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public int compareTo(Object arg0) {
    Code<?> other = (Code<?>) arg0;
    return this.getCode().compareTo(other.getCode());
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("name", this.name).add("id", this.id).add("code", this.code)
        .add("enName", this.enName).toString();
  }
}
