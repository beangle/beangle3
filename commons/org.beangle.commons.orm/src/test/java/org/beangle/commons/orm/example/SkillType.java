/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.example;

import javax.persistence.Entity;

import org.beangle.commons.orm.pojo.LongIdObject;

@Entity
public class SkillType extends LongIdObject {

  private static final long serialVersionUID = -3940729242045562128L;

  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
