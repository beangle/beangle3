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

import java.io.Writer;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.util.ValueStack;

public class Anchor extends ClosingUIBean {

  public static final Set<String> reservedTargets = CollectUtils.newHashSet("_blank", "_top", "_self",
      "_parent", "new");

  private String href;

  private String target;

  private String onclick;

  public Anchor(ValueStack stack) {
    super(stack);
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

  public boolean isReserved() {
    return reservedTargets.contains(target);
  }

  @Override
  protected void evaluateParams() {
    this.href = render(this.href);
    if (!isReserved()) {
      if (null == onclick) {
        if (null != target) {
          onclick = Strings.concat("return bg.Go(this,'", target, "')");
          target = null;
        } else {
          onclick = "return bg.Go(this,null)";
        }
      }
    }
  }

  @Override
  public boolean doEnd(Writer writer, String body) {
    if (getTheme().equals(Theme.DefaultTheme)) {
      try {
        writer.append("<a href=\"");
        writer.append(href).append("\"");
        if (null != id) {
          writer.append(" id=\"").append(id).append("\"");
        }
        if (null != target) {
          writer.append(" target=\"").append(target).append("\"");
        }
        if (null != onclick) {
          writer.append(" onclick=\"").append(onclick).append("\"");
        }
        writer.append(getParameterString());
        writer.append(">").append(body).append("</a>");
      } catch (Exception e) {
        e.printStackTrace();
      }
      return false;
    } else {
      return super.doEnd(writer, body);
    }
  }

  public String getOnclick() {
    return onclick;
  }

  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }

}
