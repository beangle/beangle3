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
package org.beangle.ems.app;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.io.IOs;
import org.beangle.commons.lang.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class Ems {

  private static final Logger logger = LoggerFactory.getLogger(Ems.class);

  private final String home;

  private final String base;

  private final String cas;

  private final String api;

  private final String webapp;

  private final String portal;

  private final String cdn;

  private final String key;

  private final Map<String, String> properties;

  public Ems(String home, Map<String, String> properties) {
    this.home = home;
    this.properties = properties;
    logger.info("Ems Home:{}", this.home);

    if (properties.containsKey("base")) {
      this.base = processUrl(properties.get("base"));
    } else {
      throw new RuntimeException("Cannot find base");
    }
    this.cas = readBase("cas");
    this.api = readBase("api");
    this.webapp = readBase("webapp");
    this.portal = readBase("portal");
    this.cdn = readBase("static");

    this.key = properties.getOrDefault("key", "openurp");
  }

  private String processUrl(String b) {
    if (b.endsWith("/")) {
      b = b.substring(0, b.length() - 1);
    }
    if (!b.startsWith("http")) {
      b = "http://" + b;
    }
    return b;
  }

  private String readBase(String property) {
    String b = properties.get(property);
    if (null == b) {
      if (property.equals("webapp")) {
        b = properties.get("base");
      } else {
        b = properties.get("base") + "/" + property;
      }
    }
    return processUrl(b);
  }

  public String getHome() {
    return home;
  }

  public String getWebappPath(String appPath) {
    return webapp + appPath;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public String getBase() {
    return base;
  }

  public String getApi() {
    return api;
  }

  public String getCas() {
    return cas;
  }

  public String getPortal() {
    return portal;
  }

  public String getCdn() {
    return cdn;
  }

  public String getWebapp() {
    return webapp;
  }

  public String getStatic() {
    return cdn;
  }

  public String getKey() {
    return key;
  }

  public static Ems getInstance() {
    return Instance;
  }

  public static Ems Instance = new Ems(findHome(), readConfig(findHome()));

  public static String findHome() {
    String home = System.getProperty("ems.home");
    if (home == null) {
      String profile = System.getProperty("ems.profile");
      if (null == profile) {
        home = SystemInfo.getUser().getHome() + "/.ems";
      } else {
        home = SystemInfo.getUser().getHome() + "/.ems/" + profile;
      }
    }
    return home;
  }

  public static Map<String, String> readConfig(String home) {
    Map<String, String> properties = CollectUtils.newHashMap();
    File f = new File(home + "/conf.properties");
    if (f.exists()) {
      Properties p = new Properties();
      InputStream is = null;
      try {
        is = new FileInputStream(f);
        p.load(is);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        IOs.close(is);
      }
      for (Object k : p.keySet()) {
        properties.put(k.toString(), p.getProperty(k.toString()).toString());
      }
    }
    return properties;
  }
}
