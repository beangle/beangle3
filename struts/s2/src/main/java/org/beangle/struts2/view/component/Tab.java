/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

import java.io.Writer;

/**
 * @author chaostone
 * @version $Id: Tab.java Jul 16, 2011 3:04:25 PM chaostone $
 */
public class Tab extends ClosingUIBean {

  private String href;

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
    return super.doEnd(writer, body);
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
