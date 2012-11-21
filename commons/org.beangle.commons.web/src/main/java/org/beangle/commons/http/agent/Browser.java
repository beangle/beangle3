/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.http.agent;

import java.io.Serializable;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web browser
 * 
 * @author chaostone
 */
public class Browser implements Serializable, Comparable<Browser> {

  private static final long serialVersionUID = -6200607575108416928L;

  private static Logger logger = LoggerFactory.getLogger(BrowserCategory.class);
  public static Map<String, Browser> browsers = CollectUtils.newHashMap();
  public static final Browser UNKNOWN = new Browser(BrowserCategory.UNKNOWN, null);

  public final BrowserCategory category;
  public final String version;

  public Browser(BrowserCategory category, String version) {
    super();
    this.category = category;
    this.version = version;
  }

  /**
   * Iterates over all Browsers to compare the browser signature with the user
   * agent string. If no match can be found Browser.UNKNOWN will be returned.
   * 
   * @param agentString
   * @return Browser
   */
  public static Browser parse(final String agentString) {
    if (Strings.isEmpty(agentString)) { return Browser.UNKNOWN; }
    for (BrowserCategory category : BrowserCategory.values()) {
      String version = category.match(agentString);
      if (version != null) {
        String key = category.getName() + "/" + version;
        Browser browser = browsers.get(key);
        if (null == browser) {
          browser = new Browser(category, version);
          browsers.put(key, browser);
        }
        return browser;
      }
    }
    logger.debug("unknown browser: {}", agentString);
    return Browser.UNKNOWN;
  }

  @Override
  public String toString() {
    return category.getName() + " " + (version == null ? "" : version);
  }

  public int compareTo(Browser o) {
    return category.compareTo(o.category);
  }
}
