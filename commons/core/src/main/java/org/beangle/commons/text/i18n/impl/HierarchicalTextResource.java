/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.text.i18n.impl;

import static org.beangle.commons.lang.Strings.substringBeforeLast;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextBundle;
import org.beangle.commons.text.i18n.TextBundleRegistry;
import org.beangle.commons.text.i18n.TextFormater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HierarchicalTextResource extends DefaultTextResource {
  private static final Logger logger = LoggerFactory.getLogger(HierarchicalTextResource.class);
  protected Class<?> clazz;
 
  public HierarchicalTextResource(Class<?> clazz, Locale locale, TextBundleRegistry registry,
      TextFormater formater) {
    super(locale, registry, formater);
    this.clazz = clazz;
  }

  @Override
  protected String doGetText(String key) {
    return findMessage(key);
  }

  protected final String findMessage(String key) {
    return findMessage(clazz, key, new HashSet<String>());
  }

  /**
   * <li>Look for message in aClass' class hierarchy.
   * <ol>
   * <li>Look for the message in a resource bundle for aClass</li>
   * <li>If not found, look for the message in a resource bundle for any implemented interface</li>
   * <li>If not found, traverse up the Class' hierarchy and repeat from the first sub-step</li>
   * </ol>
   * </li>
   * 
   * @param clazz
   * @param key
   * @param checked
   * @return
   */
  protected final String findMessage(Class<?> clazz, String key, Set<String> checked) {
    logger.debug("find message {} key {}", clazz, key);
    final String className = clazz.getName();

    if (checked.contains(className)) return null;
    checked.add(className);

    String msg = getClassMessage(className, key);
    if (null != msg) return msg;

    // check my package
    msg = findPackageMessage(className, key, checked);
    if (null != msg) return msg;

    // check all interfaces class and package
    Set<Class<?>> interfaces = new HashSet<Class<?>>();
    collectInterfaces(clazz, interfaces);
    for (Class<?> ifc : interfaces) {
      msg = getClassMessage(ifc.getName(), key);
      if (msg != null) return msg;
    }
    for (Class<?> ifc : interfaces) {
      msg = this.findPackageMessage(ifc.getName(), key, checked);
      if (null != msg) return msg;
    }

    // traverse up hierarchy
    if (clazz.isInterface()) {
      for (Class<?> ifc : clazz.getInterfaces()) {
        msg = findMessage(ifc, key, checked);
        if (null != msg) return msg;
      }
    } else {
      Class<?> superClass = clazz.getSuperclass();
      if (!superClass.equals(Object.class) && !clazz.isPrimitive()) {
        msg = findMessage(superClass, key, checked);
        if (null != msg) return msg;
      }
    }
    return null;
  }

  private void collectInterfaces(Class<?> me, Set<Class<?>> interfaces) {
    for (Class<?> ifc : me.getInterfaces()) {
      if (!ifc.getName().startsWith("java.")) interfaces.add(ifc);
      collectInterfaces(ifc, interfaces);
    }
  }

  protected final String findPackageMessage(String className, String key, Set<String> checked) {
    String msg = null;
    String packageName = className;
    while (packageName.lastIndexOf('.') != -1) {
      packageName = substringBeforeLast(packageName, ".");
      if (checked.contains(packageName)) break;
      checked.add(packageName);
      msg = getPackageMessage(packageName, key);
      if (null != msg) return msg;
    }
    return null;
  }

  /**
   * Gets the message from the named resource bundle.
   */
  protected final String getPackageMessage(String packageName, String key) {
    Option<TextBundle> bundle = registry.load(locale, packageName + ".package");
    return bundle.isDefined() ? bundle.get().getText(key) : null;
  }

  /**
   * Gets the message from the named resource bundle.
   */
  protected final String getClassMessage(String className, String key) {
    registry.load(locale, Strings.substringBeforeLast(className, ".") + ".package");
    Option<TextBundle> bundle = registry.load(locale, className);
    return bundle.isDefined() ? bundle.get().getText(key) : null;
  }
}