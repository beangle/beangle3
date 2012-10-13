/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
