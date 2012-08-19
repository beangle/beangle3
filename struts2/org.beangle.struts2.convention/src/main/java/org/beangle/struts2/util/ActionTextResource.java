package org.beangle.struts2.util;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.beangle.commons.i18n.TextBundle;
import org.beangle.commons.i18n.TextBundleRegistry;
import org.beangle.commons.i18n.TextFormater;
import org.beangle.commons.i18n.impl.DefaultTextResource;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.reflect.Reflections;

import com.opensymphony.xwork2.util.ValueStack;

public class ActionTextResource extends DefaultTextResource {

  private final Class<?> actionClass;
  private final ValueStack valueStack;

  public ActionTextResource(Class<?> actionClass, Locale locale, TextBundleRegistry registry,
      TextFormater formater, ValueStack valueStack) {
    super(locale, registry, formater);
    this.actionClass = actionClass;
    this.valueStack = valueStack;
  }
  
  /**
   * 1 remove index key
   * 2 change ModelDriven to EntitySupport
   * 3 remove superclass and interface lookup
   */
  @Override
  protected String getText(String key, Locale locale) {
    if (key == null) { return ""; }
    Set<String> checked = new HashSet<String>(5);
    // search up class hierarchy
    String msg = getMessage(actionClass.getName(), locale, key, checked);
    if (msg != null) { return msg; }
    // nothing still? all right, search the package hierarchy now
    msg= this.getPackageMessage(actionClass, key, checked);
    if (msg != null) { return msg; }

    // see if it's a child property
    int idx = key.indexOf(".");
    if (idx > 0) {
      String prop = key.substring(0, idx);
      Object obj = valueStack.findValue(prop);
      if (null != obj && !prop.equals("action")) {
        Class<?> aClass = obj.getClass();
        String newKey = key;
        while (null != aClass) {
          msg = getPackageMessage(aClass, newKey, checked);
          if (null == msg) {
            int nextIdx = newKey.indexOf(".", idx + 1);
            if (nextIdx == -1) break;
            prop = newKey.substring(idx + 1, nextIdx);
            newKey = newKey.substring(idx + 1);
            idx = nextIdx;
            if (Strings.isNotEmpty(prop)) {
              aClass = Reflections.getPropertyType(aClass, prop);
            } else {
              aClass = null;
            }
          } else {
            return msg;
          }
        }
      }
    }
    return registry.getDefaultText(key, locale);
  }

  private String getPackageMessage(Class<?> aClass, String key, Set<String> checked) {
    String msg = null;
    String basePackageName = aClass.getName();
    while (basePackageName.lastIndexOf('.') != -1) {
      basePackageName = basePackageName.substring(0, basePackageName.lastIndexOf('.'));
      msg = getMessage(basePackageName + ".package", locale, key, checked);
      if (msg != null) { return msg; }
    }
    return null;
  }

  /**
   * Gets the message from the named resource bundle.
   */
  private String getMessage(String bundleName, Locale locale, String key, Set<String> checked) {
    if (checked.contains(bundleName)) { return null; }
    TextBundle bundle = registry.load(locale, bundleName);
    return null == bundle ? null : bundle.getText(key);
  }
}
