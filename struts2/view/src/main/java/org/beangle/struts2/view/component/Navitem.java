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
 * @version $Id: Navitem.java Apr 20, 2011 8:46:40 AM chaostone $
 */
public class Navitem extends UIBean {

  private String title;
  private String href;

  private String onclick;
  private String target;
  private boolean selected = false;

  public Navitem(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    this.href = render(this.href);
    title = getText(title);
    this.selected = findAncestor(Navmenu.class).isSelected(this.href);
    if (null == onclick) {
      if (null != target) {
        onclick = Strings.concat("return bg.Go(this,'", target, "')");
        target = null;
      } else {
        onclick = "return bg.Go(this,null)";
      }
    }
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getOnclick() {
    return onclick;
  }

  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

}
