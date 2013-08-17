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

import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

public class Submit extends UIBean {

  String formId;
  String onsubmit;
  String action;
  String value;
  String target;

  public Submit(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == formId) {
      Form f = findAncestor(Form.class);
      if (null != f) formId = f.getId();
    }
    if (null != onsubmit && -1 != onsubmit.indexOf('(')) {
      onsubmit = Strings.concat("'", onsubmit, "'");
    }
    if (null != value) {
      value = getText(value);
    }
    if (null != action) {
      action = render(action);
    }
  }

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }

  public String getOnsubmit() {
    return onsubmit;
  }

  public void setOnsubmit(String onsubmit) {
    this.onsubmit = onsubmit;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String href) {
    this.action = href;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

}
