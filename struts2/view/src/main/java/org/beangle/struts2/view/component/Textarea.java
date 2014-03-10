/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
 * 文本域
 * 
 * @author chaostone
 * @version $Id: Textarea.java May 3, 2011 12:40:21 PM chaostone $
 */
public class Textarea extends AbstractTextBean {

  protected String cols;
  protected String readonly;
  protected String rows;
  protected String wrap;

  public Textarea(ValueStack stack) {
    super(stack);
    maxlength = "400";
  }

  @Override
  protected void evaluateParams() {
    super.evaluateParams();
    Form myform = findAncestor(Form.class);
    if (null != maxlength) myform.addCheck(id, "maxLength(" + maxlength + ")");
  }

  public String getCols() {
    return cols;
  }

  public void setCols(String cols) {
    this.cols = cols;
  }

  public String getReadonly() {
    return readonly;
  }

  public void setReadonly(String readonly) {
    this.readonly = readonly;
  }

  public String getRows() {
    return rows;
  }

  public void setRows(String rows) {
    this.rows = rows;
  }

  public String getWrap() {
    return wrap;
  }

  public void setWrap(String wrap) {
    this.wrap = wrap;
  }

}
