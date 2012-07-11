/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
