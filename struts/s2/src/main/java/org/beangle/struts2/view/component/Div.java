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

import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

public class Div extends ClosingUIBean {

  private String href;

  private String astarget;

  public Div(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == astarget && (null != id || null != href)) astarget = "true";
    if (null != href) {
      generateIdIfEmpty();
      href = render(this.href);
    }
    if (!Objects.equals(astarget, "false")) {
      String className = "ajax_container";
      if (null != parameters.get("class")) {
        className = Strings.concat(className, " ", parameters.get("class").toString());
      }
      parameters.put("class", className);
    }
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getAstarget() {
    return astarget;
  }

  public void setAstarget(String astarget) {
    this.astarget = astarget;
  }

}
