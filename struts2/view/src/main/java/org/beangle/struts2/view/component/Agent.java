/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
