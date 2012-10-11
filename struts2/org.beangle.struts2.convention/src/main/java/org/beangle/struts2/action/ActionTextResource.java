package org.beangle.struts2.action;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.beangle.commons.i18n.TextBundle;
import org.beangle.commons.i18n.TextBundleRegistry;
import org.beangle.commons.i18n.TextFormater;
import org.beangle.commons.i18n.impl.DefaultTextResource;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.reflect.Reflections;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
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
    String msg = getMessage(actionClass.getName(), locale, key);
    if (msg != null) { return msg; }
    // nothing still? all right, search the package hierarchy now
    msg = getPackageMessage(actionClass.getName(), key, checked);
    if (msg != null) { return msg; }

    if (EntityActionSupport.class.isAssignableFrom(actionClass)) {
      ActionContext context = ActionContext.getContext();
      // search up model's class hierarchy
      ActionInvocation actionInvocation = context.getActionInvocation();
      // ActionInvocation may be null if we're being run from a Sitemesh filter
      if (actionInvocation != null) {
        Object action = actionInvocation.getAction();
        if (action instanceof EntityActionSupport) {
          String entityName = ((EntityActionSupport) action).getEntityName();
          if (entityName != null) {
            msg = getPackageMessage(entityName, key, checked);
            if (msg != null) { return msg; }
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
          msg = getPackageMessage(aClass.getName(), newKey, checked);
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

  private String getPackageMessage(String className, String key, Set<String> checked) {
    String msg = null;
    String baseName = className;
    while (baseName.lastIndexOf('.') != -1) {
      baseName = baseName.substring(0, baseName.lastIndexOf('.'));
      if (checked.contains(baseName)) {
        continue;
      }
      msg = getMessage(baseName + ".package", locale, key);
      if (msg != null) { return msg; }
      checked.add(baseName);
    }
    return null;
  }

  /**
   * Gets the message from the named resource bundle.
   */
  private String getMessage(String bundleName, Locale locale, String key) {
    Option<TextBundle> bundle = registry.load(locale, bundleName);
    return bundle.isDefined() ? bundle.get().getText(key) : null;
  }
}
