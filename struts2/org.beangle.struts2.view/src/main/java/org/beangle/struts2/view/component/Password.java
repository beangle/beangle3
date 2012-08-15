/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Password.java May 3, 2011 2:18:50 PM chaostone $
 */
public class Password extends AbstractTextBean {

  protected String minlength;

  protected String showStrength = "true";

  public Password(ValueStack stack) {
    super(stack);
    minlength = "6";
    maxlength = "10";
  }

  public String getMinlength() {
    return minlength;
  }

  public void setMinlength(String minlength) {
    this.minlength = minlength;
  }

  public String getShowStrength() {
    return showStrength;
  }

  public void setShowStrength(String showStrength) {
    this.showStrength = showStrength;
  }

}
