/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.util.ValueStack;

public class Radio extends UIBean {
  public static final Map<Object, String> Booleans = new HashMap<Object, String>();
  static {
    Booleans.put(Boolean.TRUE, "1");
    Booleans.put(Boolean.FALSE, "0");
    Booleans.put("y", "1");
    Booleans.put("Y", "1");
    Booleans.put("true", "1");
    Booleans.put("N", "0");
    Booleans.put("n", "0");
    Booleans.put("false", "0");
  }

  public Radio(ValueStack stack) {
    super(stack);
  }

  protected String name;
  protected String label;
  protected String title;
  protected Object value = "";

  @Override
  protected void evaluateParams() {
    if (null == this.id) generateIdIfEmpty();
    label = processLabel(label, name);
    if (null != title) title = getText(title);
    else title = label;
    this.value = booleanize(value);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public static Object booleanize(Object obj) {
    Object booleanValue = Booleans.get(obj);
    return null == booleanValue ? obj : booleanValue;
  }
}
