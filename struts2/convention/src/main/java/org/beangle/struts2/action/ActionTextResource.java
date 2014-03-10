/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.struts2.action;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextBundleRegistry;
import org.beangle.commons.text.i18n.TextCache;
import org.beangle.commons.text.i18n.TextFormater;
import org.beangle.commons.text.i18n.impl.HierarchicalTextResource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class ActionTextResource extends HierarchicalTextResource {

  private final ValueStack valueStack;

  private TextCache cache;

  public ActionTextResource(Class<?> actionClass, Locale locale, TextBundleRegistry registry,
      TextFormater formater, ValueStack valueStack, TextCache cache) {
    super(actionClass, locale, registry, formater);
    this.valueStack = valueStack;
    this.cache = cache;
  }

  protected String doGetText(String key) {
    if (null != cache) {
      String msg = cache.get(locale, clazz, key);
      if (null == msg) {
        msg = innerGetText(key);
        if (null != msg) cache.put(locale, clazz, key, msg);
      }
      return msg;
    } else {
      return innerGetText(key);
    }
  }

  /**
   * 1 remove index key(user.roles[0].name etc.)
   * 2 change ModelDriven to EntitySupport
   */
  protected String innerGetText(String key) {
    if (key == null) return "";
    Set<String> checked = new HashSet<String>(5);
    // search up class hierarchy
    String msg = findMessage(clazz, key, checked);
    if (msg != null) return msg;

    if (EntityDrivenAction.class.isAssignableFrom(clazz)) {
      ActionContext context = ActionContext.getContext();
      // search up model's class hierarchy
      ActionInvocation actionInvocation = context.getActionInvocation();
      // ActionInvocation may be null if we're being run from a Sitemesh filter
      if (actionInvocation != null) {
        Object action = actionInvocation.getAction();
        if (action instanceof EntityDrivenAction) {
          String entityName = ((EntityDrivenAction) action).getEntityName();
          if (entityName != null) {
            msg = findPackageMessage(entityName, key, checked);
            if (msg != null) return msg;
          }
        }
      }
    }

    // see if it's a child property
    int idx = key.indexOf(".");
    if (idx > 0) {
      String prop = key.substring(0, idx);
      Object obj = valueStack.findValue(prop);
      if (null != obj && !prop.equals("action")) {
        Class<?> aClass = obj.getClass();
        String newKey = key;
        while (null != aClass) {
          msg = findMessage(aClass, newKey, new HashSet<String>());
          if (null == msg) {
            int nextIdx = newKey.indexOf(".", idx + 1);
            if (nextIdx == -1) break;
            prop = newKey.substring(idx + 1, nextIdx);
            newKey = newKey.substring(idx + 1);
            idx = nextIdx;
            if (Strings.isNotEmpty(prop)) aClass = PropertyUtils.getPropertyType(aClass, prop);
            else aClass = null;
          } else {
            return msg;
          }
        }
      }
    }
    return registry.getDefaultText(key, locale);
  }
}
