/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Head extends ClosingUIBean {

  private boolean loadui = true;

  public Head(ValueStack stack) {
    super(stack);
  }

  public boolean isLoadui() {
    return loadui;
  }

  public void setLoadui(boolean loadui) {
    this.loadui = loadui;
  }

}
