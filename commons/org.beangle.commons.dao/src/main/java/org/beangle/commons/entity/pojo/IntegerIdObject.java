/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.beangle.commons.entity.pojo.IntegerIdEntity;
import org.beangle.commons.entity.util.ValidEntityKeyPredicate;

@MappedSuperclass
public class IntegerIdObject implements IntegerIdEntity {
  private static final long serialVersionUID = -7530111699332363124L;

  /** 非业务主键 */
  @Id
  @GeneratedValue(generator = "table_sequence")
  protected Integer id;

  public IntegerIdObject() {
    super();
  }

  public IntegerIdObject(Integer id) {
    super();
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIdentifier() {
    return id;
  }

  public boolean isPersisted() {
    return ValidEntityKeyPredicate.INSTANCE.evaluate(id);
  }

  public boolean isTransient() {
    return !ValidEntityKeyPredicate.INSTANCE.evaluate(id);
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder(-64900959, -454788261).append(this.id).toHashCode();
  }

  /**
   * 比较id,如果任一方id是null,则不相等
   * <p>
   * 因为业务对象被CGlib或者javassist增强的原因，这里只提供一般的基于id的比较,不提供基于Class的比较。如果在存在继承结构， 请重置equals方法。
   * 
   * @see java.lang.Object#equals(Object)
   */
  public boolean equals(final Object object) {
    if (this == object) return true;
    if (!(object instanceof IntegerIdObject)) { return false; }
    IntegerIdObject rhs = (IntegerIdObject) object;
    if (null == getId() || null == rhs.getId()) { return false; }
    return getId().equals(rhs.getId());
  }
}
