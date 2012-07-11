/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 页面模块(豆腐块)
 * 
 * @author chaostone
 * @version $Id: Module.java May 1, 2011 6:27:46 PM chaostone $
 */
public class Module extends ClosingUIBean {

  private String title;

  public Module(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    generateIdIfEmpty();
    if (null != title) {
      title = getText(title);
    }
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
