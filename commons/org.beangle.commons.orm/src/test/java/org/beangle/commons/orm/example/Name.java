/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.example;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import org.beangle.commons.entity.Component;

@Embeddable
public class Name implements Component {

  String firstName;

  String lastName;

  @OneToMany(mappedBy = "employer")
  List<OldName> oldNames;

  public List<OldName> getOldNames() {
    return oldNames;
  }

  public void setOldNames(List<OldName> oldNames) {
    this.oldNames = oldNames;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
