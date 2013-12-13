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
package org.beangle.struts2.view.bean;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.url.UriRender;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultActionUriRender implements ActionUriRender {

  private UriRender render;

  public String render(String referer, String uri) {
    return render.render(referer, uri);
  }

  @Inject(StrutsConstants.STRUTS_ACTION_EXTENSION)
  public void setSuffix(String suffix) {
    String firstSuffix = null;
    if (Strings.isNotEmpty(suffix)) {
      int commaIndex = suffix.indexOf(",");
      if (-1 == commaIndex) firstSuffix = suffix;
      else firstSuffix = suffix.substring(0, commaIndex);
    }
    ServletContext sc = ServletActionContext.getServletContext();
    String context = null;
    if (null != sc) {
      context = sc.getContextPath();
    }
    render = new UriRender(context, firstSuffix);
  }

}
