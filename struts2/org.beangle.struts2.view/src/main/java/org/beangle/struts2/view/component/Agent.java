/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.beangle.commons.http.agent.Browser;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Useragent specific javascript
 * 
 * @author chaostone
 * @since 3.0.1
 */
public class Agent extends UIBean {

  private final String browser;

  private final String version;

  public Agent(ValueStack stack) {
    super(stack);
    Browser browser = Browser.parse(getRequest().getHeader("USER-AGENT"));
    this.browser = browser.category.getName();
    this.version = browser.version;
  }

  public String getBrowser() {
    return browser;
  }

  public String getVersion() {
    return version;
  }

}
