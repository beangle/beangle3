/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.app;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class EmsApp {

  public static EmsApp Instance = new EmsApp();

  public static String getName() {
    return Instance.name;
  }

  private String name;

  private Map<String, String> properties = CollectUtils.newHashMap();

  public EmsApp() {
    super();
    readProperties();
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public void setName(String name) {
    this.name = name;
  }

  private void readProperties() {
    List<URL> configs = ClassLoaders.getResources("META-INF/beangle/ems-app.properties", this.getClass());
    for (URL config : configs) {
      Properties p = new Properties();
      try {
        p.load(config.openStream());
      } catch (IOException e) {
        e.printStackTrace();
      }
      for (Object k : p.keySet()) {
        properties.put(k.toString(), p.getProperty(k.toString()).toString());
      }
    }

    if (properties.containsKey("name")) {
      name = properties.get("name");
    } else {
      throw new RuntimeException("cannot find META-INF/beangle/ems-app.properties");
    }
  }

  public String getPath() {
    //app path starts with /
    String appPath = Strings.replace(name, "-", "/");
    appPath = "/" + Strings.replace(appPath, ".", "/");
    return appPath;
  }

  public String getSecret() {
    String secret = properties.get("secret");
    if (null == secret) return name;
    else return secret;
  }

  public static File getAppFile() {
    File file= new File(Ems.getInstance().getHome() + EmsApp.Instance.getPath() + ".xml");
    if(!file.exists()){
      file= new File(Ems.getInstance().getHome() + EmsApp.Instance.getPath() + ".json");
    }
    return file;
  }
}
