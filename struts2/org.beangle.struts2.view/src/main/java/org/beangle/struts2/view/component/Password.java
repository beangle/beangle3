/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Password.java May 3, 2011 2:18:50 PM chaostone $
 */
public class Password extends AbstractTextBean {

  protected String minlength;

  protected String showStrength = "false";

  public Password(ValueStack stack) {
    super(stack);
    minlength = "6";
    maxlength = "10";
  }

  public String getMinlength() {
    return minlength;
  }

  public void setMinlength(String minlength) {
    this.minlength = minlength;
  }

  public String getShowStrength() {
    return showStrength;
  }

  public void setShowStrength(String showStrength) {
    this.showStrength = showStrength;
  }

}
