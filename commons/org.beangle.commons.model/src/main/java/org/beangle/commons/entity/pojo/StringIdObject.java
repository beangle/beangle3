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

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.util.ValidEntityKeyPredicate;

@MappedSuperclass
public class StringIdObject implements Entity<String> {

  private static final long serialVersionUID = -6898498932182877104L;

  /** 非业务主键 */
  @Id
  protected String id;

  public StringIdObject() {
    super();
  }

  public StringIdObject(String id) {
    super();
    this.id = id;
  }

  public String getIdentifier() {
    return id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isPersisted() {
    return ValidEntityKeyPredicate.INSTANCE.evaluate(id);
  }

  public boolean isTransient() {
    return !ValidEntityKeyPredicate.INSTANCE.evaluate(id);
  }

  /**
   * Return 37 * 17 if id is null else id's hashCode
   */
  public int hashCode() {
    return (null == id) ? 629 : this.id.hashCode();
  }

  /**
   * 比较id,如果任一方id是null,则不相等
   * 
   * @see java.lang.Object#equals(Object)
   */
  public boolean equals(final Object object) {
    if (this == object) return true;
    if (!(object instanceof StringIdObject)) { return false; }
    StringIdObject rhs = (StringIdObject) object;
    if (null == getId() || null == rhs.getId()) { return false; }
    return getId().equals(rhs.getId());
  }

}
