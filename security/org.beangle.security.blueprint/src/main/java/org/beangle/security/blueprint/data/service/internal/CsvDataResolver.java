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
package org.beangle.security.blueprint.data.service.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.asm.ProxyUtils;
import org.beangle.commons.lang.conversion.Conversion;
import org.beangle.commons.lang.conversion.impl.DefaultConversion;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.service.UserDataProvider;
import org.beangle.security.blueprint.data.service.UserDataResolver;

/**
 * Store list of objects using comma.
 * <p>
 * object's property seperated by ; like this: id;name,1;role1,2;role2
 * 
 * @author chaostone
 */
public class CsvDataResolver implements UserDataResolver, UserDataProvider {

  public String marshal(ProfileField property, Collection<?> items) {
    if (null == items) { return null; }
    List<String> properties = CollectUtils.newArrayList();
    if (null != property.getType().getKeyName()) {
      properties.add(property.getType().getKeyName());
    }
    if (null != property.getType().getProperties()) {
      String[] names = Strings.split(property.getType().getProperties(), ",");
      properties.addAll(Arrays.asList(names));
    }
    StringBuilder sb = new StringBuilder();
    if (properties.isEmpty()) {
      for (Object obj : items) {
        if (null != obj) {
          sb.append(String.valueOf(obj)).append(',');
        }
      }
    } else {
      for (String prop : properties) {
        sb.append(prop).append(';');
      }
      sb.deleteCharAt(sb.length() - 1).append(',');
      for (Object obj : items) {
        for (String prop : properties) {
          Object value = null;
          try {
            value = ProxyUtils.getProperty(obj, prop);
          } catch (Exception e) {
            e.printStackTrace();
          }
          sb.append(String.valueOf(value)).append(';');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(',');
      }
    }
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> unmarshal(ProfileField property, String source) {
    if (Strings.isEmpty(source)) { return Collections.emptyList(); }
    List<String> properties = CollectUtils.newArrayList();
    if (null != property.getType().getKeyName()) {
      properties.add(property.getType().getKeyName());
    }
    if (null != property.getType().getProperties()) {
      String[] names = Strings.split(property.getType().getProperties(), ",");
      properties.addAll(Arrays.asList(names));
    }
    String[] datas = Strings.split(source, ",");
    try {
      Class<?> type = null;
      type = Class.forName(property.getType().getTypeName());
      List<T> rs = CollectUtils.newArrayList();
      if (properties.isEmpty()) {
        Conversion conversion = DefaultConversion.Instance;
        for (String data : datas)
          rs.add((T) conversion.convert(data, type));
        return rs;
      } else {
        properties.clear();
        int startIndex = 0;
        String[] names = new String[] { property.getType().getKeyName() };
        if (-1 != datas[0].indexOf(';')) {
          names = Strings.split(datas[0], ";");
          startIndex = 1;
        }
        properties.addAll(Arrays.asList(names));
        for (int i = startIndex; i < datas.length; i++) {
          Object obj = type.newInstance();
          String[] dataItems = Strings.split(datas[i], ";");
          for (int j = 0; j < properties.size(); j++) {
            ProxyUtils.setProperty(obj, properties.get(j), dataItems[j]);
          }
          rs.add((T) obj);
        }
      }
      return rs;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public <T> List<T> getData(ProfileField property, String source, Object... keys) {
    return unmarshal(property, source);
  }

  public String getName() {
    return "csv";
  }

}
