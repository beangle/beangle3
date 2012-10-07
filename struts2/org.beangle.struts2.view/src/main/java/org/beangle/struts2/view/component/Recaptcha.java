/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Google support online captcha
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class Recaptcha extends ClosingUIBean {

  private String theming = "red";

  private String publickey;

  private static Set<String> buildins = CollectUtils.newHashSet("red", "clean", "white", "blackglass");

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

  public boolean isBuildinTheming() {
    return buildins.contains(theming);
  }

}
