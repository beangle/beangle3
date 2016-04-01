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

import com.opensymphony.xwork2.util.ValueStack;

public class Head extends ClosingUIBean {

  private boolean loadui = true;

  private boolean compressed = true;

  public Head(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    String devMode = getRequestParameter("devMode");
    if (null != devMode) compressed = !("true".equals(devMode) || "on".equals(devMode));
  }

  public boolean isCompressed() {
    return compressed;
  }

  public void setCompressed(boolean compress) {
    this.compressed = compress;
  }

  public boolean isLoadui() {
    return loadui;
  }

  public void setLoadui(boolean loadui) {
    this.loadui = loadui;
  }

}
