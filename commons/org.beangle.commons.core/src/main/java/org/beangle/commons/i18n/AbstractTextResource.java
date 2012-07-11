/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Abstract AbstractTextResource class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractTextResource implements TextResource{

  /** Constant <code>EMPTY_ARGS</code> */
  protected static final Object[] EMPTY_ARGS = new Object[0];

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param defaultValue a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public String getText(String key, String defaultValue) {
    String text = getText(key);
    if (null == text) { return defaultValue; }
    return text;
  }

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param defaultValue a {@link java.lang.String} object.
   * @param obj a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public String getText(String key, String defaultValue, String obj) {
    List<Object> args = new ArrayList<Object>(1);
    args.add(obj);
    return getText(key, defaultValue, args);
  }

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param args a {@link java.util.List} object.
   * @return a {@link java.lang.String} object.
   */
  public String getText(String key, List<Object> args) {
    Object[] params;
    if (null == args) {
      params = EMPTY_ARGS;
    } else {
      params = args.toArray();
    }
    return getText(key, params);
  }

  /** {@inheritDoc} */
  public String getText(String key, String defaultValue, List<Object> args) {
    Object[] params;
    if (null == args) {
      params = EMPTY_ARGS;
    } else {
      params = args.toArray();
    }
    return getText(key, defaultValue, params);
  }

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param defaultValue a {@link java.lang.String} object.
   * @param args an array of {@link java.lang.Object} objects.
   * @return a {@link java.lang.String} object.
   */
  public String getText(String key, String defaultValue, Object[] args) {
    String text = getText(key, args);
    String defaultVal = defaultValue;
    if (null == text && null == defaultVal) {
      defaultVal = key;
    }
    if (null == text && defaultVal != null) {
      MessageFormat format = new MessageFormat(defaultVal);
      format.setLocale(getLocale());
      format.applyPattern(defaultVal);
      return format.format(args);
    }
    return text;
  }

}
