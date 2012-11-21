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
package org.beangle.commons.orm.example;

import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.LongIdTimeObject;

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
