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

import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Navmenu.java Apr 20, 2011 8:47:00 AM chaostone $
 */
public class Navmenu extends ClosingUIBean {

  private String title;

  private String uri;

  /** 是有已经有标签卡被选中了 */
  private boolean selected;

  public Navmenu(ValueStack stack) {
    super(stack);
    StringBuilder sb = new StringBuilder(Strings.substringBeforeLast(getRequestURI(), "."));
    if (-1 == sb.lastIndexOf("!")) {
      sb.append("!index");
    }
    this.uri = sb.toString();
  }

  boolean isSelected(String givenUri) {
    if (selected) return false;
    else {
      selected = sameAction(givenUri);
      return selected;
    }
  }

  /**
   * 去除后缀比较是否是同一个resource(action!method)
   * 
   * @param first
   * @param second
   */
  private boolean sameAction(String first) {
    StringBuilder firstSb = new StringBuilder(Strings.substringBefore(first, "."));
    if (-1 == firstSb.lastIndexOf("!")) {
      firstSb.append("!index");
    }
    return firstSb.toString().equals(uri);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setLabel(String label) {
    this.title = label;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

}
