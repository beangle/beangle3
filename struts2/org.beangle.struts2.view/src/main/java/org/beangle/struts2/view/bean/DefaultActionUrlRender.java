/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.bean;

import org.apache.struts2.StrutsConstants;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.url.UrlRender;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultActionUrlRender implements ActionUrlRender {

  private UrlRender render;

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
    render = new UrlRender(firstSuffix);
  }

}
