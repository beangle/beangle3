/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Google support online captcha
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class Recaptcha extends UIBean {

  private String theming = "red";

  private String publickey;

  public Recaptcha(ValueStack stack) {
    super(stack);
    generateIdIfEmpty();
  }

  public void setTheming(String theming) {
    this.theming = theming;
  }

  public String getTheming() {
    return theming;
  }

  public String getPublickey() {
    return publickey;
  }

  public void setPublickey(String publickey) {
    this.publickey = publickey;
  }

}
