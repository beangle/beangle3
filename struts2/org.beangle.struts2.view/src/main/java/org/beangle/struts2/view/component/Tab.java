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

import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Tab.java Jul 16, 2011 3:04:25 PM chaostone $
 */
public class Tab extends ClosingUIBean {

  private String href;

  private String target;

  private String label;

  public Tab(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null != href) href = render(href);
    if (null != label) label = getText(label);
    generateIdIfEmpty();
    Tabs tabs = findAncestor(Tabs.class);
    if (null != tabs) {
      tabs.addTab(this);
    }
  }

  @Override
  public boolean doEnd(Writer writer, String body) {
    if (null == target) {
      this.target = id + "_target";
      return super.doEnd(writer, body);
    } else {
      return false;
    }
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
