/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.example;

import javax.persistence.Entity;

import org.beangle.commons.orm.pojo.LongIdTimeObject;

@Entity
public class ContractInfo extends LongIdTimeObject {

  private static final long serialVersionUID = 5751822654667684436L;

  String add1;

  String add2;

  public String getAdd1() {
    return add1;
  }

  public void setAdd1(String add1) {
    this.add1 = add1;
  }

  public String getAdd2() {
    return add2;
  }

  public void setAdd2(String add2) {
    this.add2 = add2;
  }

}
