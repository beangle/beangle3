/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.importer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.beangle.commons.entity.pojo.LongIdEntity;

public class Example implements LongIdEntity {

  private static final long serialVersionUID = 1467632544620523331L;

  Long id;

  String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("id", this.getId())
        .append("name", this.name).toString();
  }

  public Long getIdentifier() {
    return id;
  }

  public boolean isPersisted() {
    return id != null;
  }

  public boolean isTransient() {
    return id == null;
  }
}
