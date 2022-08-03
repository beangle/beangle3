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
package org.beangle.ems.app.util;

import java.util.HashMap;
import java.util.Map;

public class DatasourceConfig {
  public String name;
  public String driver;
  public String user;
  public String password;

  public Map<String, String> props = new HashMap<String, String>();

  public DatasourceConfig(){

  }
  public DatasourceConfig(Map<String, String> properties) {
    for (Map.Entry<String, String> e : properties.entrySet()) {
      String key = e.getKey();
      String v = e.getValue();
      switch (key) {
        case "user":
          this.user = v;
          break;
        case "password":
          this.password = v;
          break;
        case "name":
          this.name = v;
          break;
        case "driver":
          this.driver = v;
          break;
        case "catalog":
        case "schema":
          break;
        default:
          props.put(key, v);
      }
    }
  }
}
