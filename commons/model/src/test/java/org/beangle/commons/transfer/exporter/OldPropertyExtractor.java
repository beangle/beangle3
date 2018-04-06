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
package org.beangle.commons.transfer.exporter;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextResource;

/**
 * 缺省和简单的属性提取类
 *
 * @author chaostone
 */
public class OldPropertyExtractor implements PropertyExtractor {

  protected TextResource textResource = null;

  public OldPropertyExtractor() {
    super();
  }

  public OldPropertyExtractor(TextResource textResource) {
    setTextResource(textResource);
  }

  protected Object extract(Object target, String property) throws Exception {
    Object value = null;
    try {
      String[] subProperties = Strings.split(property, '.');
      if (subProperties.length >= 1) {
        StringBuilder passedProperty = new StringBuilder(subProperties[0]);
        for (int i = 0; i < subProperties.length - 1; i++) {
          if (null != PropertyUtils.getProperty(target, passedProperty.toString())) passedProperty
              .append(".").append(subProperties[i + 1]);
          else return "";
        }
      }
      value = PropertyUtils.getProperty(target, property);
    } catch (Exception e) {
      return null;
    }
    if (value instanceof Boolean) {
      if (null == textResource) {
        return value;
      } else {
        if (Boolean.TRUE.equals(value)) return getText("yes");
        else return getText("no");
      }
    } else {
      return value;
    }
  }

  public Object getPropertyValue(Object target, String property) throws Exception {
    return extract(target, property);
  }

  public String getPropertyIn(Collection<?> collection, String property) throws Exception {
    StringBuilder sb = new StringBuilder();
    if (null != collection) {
      for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
        Object one = (Object) iter.next();
        sb.append(extract(one, property)).append(",");
      }
    }
    // 删除逗号
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  protected String getText(String key) {
    return textResource.getText(key, key);
  }

  public TextResource getTextResource() {
    return textResource;
  }

  public void setTextResource(TextResource textResource) {
    this.textResource = textResource;
  }
}
