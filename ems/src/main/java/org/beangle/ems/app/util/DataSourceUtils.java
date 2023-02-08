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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.io.IOs;
import org.beangle.commons.io.StringBuilderWriter;
import org.beangle.commons.lang.Charsets;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;

public class DataSourceUtils {

  private static Map<String, DriverInfo> drivers = new HashMap<String, DriverInfo>();

  static {
    drivers.put("postgresql", new DriverInfo("org.postgresql.Driver", "org.postgresql.ds.PGSimpleDataSource"));
    drivers.put("oracle", new DriverInfo("org.postgresql.Driver", "oracle.jdbc.pool.OracleDataSource"));
    drivers.put("jtds", new DriverInfo("net.sourceforge.jtds.jdbc.Driver", "net.sourceforge.jtds.jdbcx.JtdsDataSource"));
  }

  public static DataSource build(String driver, String username, String password, Map<String, String> props) {
    return new HikariDataSource(new HikariConfig(buildProperties(driver, username, password, props)));
  }

  private static Properties buildProperties(String driver, String username, String password, Map<String, String> props) {
    Properties properties = new Properties();
    Set<String> writables = PropertyUtils.getWritableProperties(HikariConfig.class);

    for (Map.Entry<String, String> e : props.entrySet()) {
      String key = (e.getKey().equals("url")) ? "jdbcUrl" : e.getKey();
      if (!writables.contains(key)) key = "dataSource." + key;
      properties.put(key, e.getValue());
    }

    if (driver.equals("oracle") && !properties.containsKey("jdbcUrl") && !props.containsKey("driverType")) {
      properties.put("dataSource.driverType", "thin");
    }

    if (null != username) properties.put("username", username);
    if (null != password) properties.put("password", password);

    if (properties.containsKey("jdbcUrl")) {
      try {
        Class.forName(drivers.get(driver).driverClassName);
      } catch (Exception e) {

      }
    } else {
      if (!properties.containsKey("dataSourceClassName"))
        properties.put("dataSourceClassName", drivers.get(driver).dataSourceClassName);
    }
    return properties;
  }

  public static void close(DataSource dataSource) {
    if (dataSource instanceof HikariDataSource) {
      ((HikariDataSource) dataSource).close();
    } else {
      try {
        Method method = dataSource.getClass().getMethod("close");
        if (null != method) {
          method.invoke(dataSource);
        }
      } catch (Exception e) {

      }
    }
  }

  public static DatasourceConfig parseXml(InputStream is, String dsname) {
    DatasourceConfig conf = null;
    try {
      SAXReader reader = new SAXReader();
      Document document = reader.read(is);
      List nodes = document.selectNodes("/app/resources/datasource");
      Set<String> predefined = Set.of("user", "password", "driver", "props");
      for (Object o : nodes) {
        if (o instanceof Node) {
          Node node = (Node) o;
          String name = node.valueOf("@name");
          if (name.equals(dsname)) {
            conf = new DatasourceConfig();
            conf.user = node.selectSingleNode("user").getText();
            conf.password = node.selectSingleNode("password").getText();
            conf.driver = node.selectSingleNode("driver").getText();
            conf.name = name;
            List propNodes = node.selectNodes("props/prop");
            for (Object po : propNodes) {
              Node pn = (Node) po;
              conf.props.put(pn.valueOf("@name"), pn.valueOf("@value"));
            }
            List children = node.selectNodes("*");
            for (Object o1 : children) {
              Node node1 = (Node) o1;
              if (!predefined.contains(node1.getName())) {
                conf.props.put(node1.getName(), node1.getText());
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return conf;
  }

  public static DatasourceConfig parseJson(InputStream is) {
    StringBuilderWriter sw = new StringBuilderWriter();
    Charset charset = Charsets.UTF_8;
    String string = null;
    try {
      IOs.copy(new InputStreamReader(is, charset.name()), sw);
      string = sw.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine engine = sem.getEngineByName("javascript");
    Map<String, String> result = new HashMap<String, String>();
    Map<Object, Object> data = null;
    try {
      data = (Map<Object, Object>) engine.eval("result =" + string);
    } catch (Exception e) {
      e.printStackTrace();
      data = new HashMap<Object, Object>();
    }

    Iterator<Map.Entry<Object, Object>> iter = data.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<Object, Object> one = iter.next();
      String value = null;
      if (one.getValue() instanceof java.lang.Double) {
        java.lang.Double d = (Double) one.getValue();
        if (java.lang.Double.compare(d, d.intValue()) > 0) value = d.toString();
        else value = String.valueOf(d.intValue());
      } else {
        value = one.getValue().toString();
      }
      String key = one.getKey().toString();
      result.put(key, value);
    }
    return new DatasourceConfig(result);
  }

  public static void main(String[] args) {
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine engine = sem.getEngineByName("javascript");
    System.out.println(engine);
  }
}

class DriverInfo {
  public final String driverClassName;
  public final String dataSourceClassName;

  public DriverInfo(String driver, String ds) {
    this.driverClassName = driver;
    this.dataSourceClassName = ds;
  }
}
