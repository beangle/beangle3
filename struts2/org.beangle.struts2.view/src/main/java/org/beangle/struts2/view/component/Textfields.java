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
package org.beangle.struts2.view.component;

import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

public class Textfields extends UIBean {

  private String names;

  private Textfield[] fields;

  public Textfields(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    String[] nameArray = Strings.split(names, ',');
    fields = new Textfield[nameArray.length];
    for (int i = 0; i < nameArray.length; i++) {
      fields[i] = new Textfield(stack);
      String name = nameArray[i];
      String title = name;
      int semiconIndex = name.indexOf(';');
      if (-1 != semiconIndex) {
        title = name.substring(semiconIndex + 1);
        name = name.substring(0, semiconIndex);
      }
      fields[i].setName(name);
      fields[i].setLabel(title);
      fields[i].evaluateParams();
    }
  }

  public void setNames(String names) {
    this.names = names;
  }

  public Textfield[] getFields() {
    return fields;
  }

}
