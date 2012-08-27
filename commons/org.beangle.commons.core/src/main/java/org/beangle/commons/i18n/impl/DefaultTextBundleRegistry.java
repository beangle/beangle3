/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.i18n.TextBundle;
import org.beangle.commons.i18n.TextBundleRegistry;
import org.beangle.commons.lang.ClassLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class DefaultTextBundleRegistry implements TextBundleRegistry {

  private static final Logger logger = LoggerFactory.getLogger(DefaultTextBundleRegistry.class);

  protected final Map<Locale, Map<String, TextBundle>> caches = CollectUtils.newConcurrentHashMap();
  protected final Map<Locale, Set<String>> missings = CollectUtils.newConcurrentHashMap();
  protected final List<String> defaults = CollectUtils.newArrayList();
  protected boolean reloadBundles = false;

  public void addDefaults(String... bundleNames) {
    for (String name : bundleNames)
      defaults.add(name);
    logger.info("Add {} global message bundles", Arrays.toString(bundleNames));
  }

  public TextBundle load(Locale locale, String bundleName) {
    if (reloadBundles) {
      caches.clear();
      missings.clear();
    }
    Map<String, TextBundle> localeBundles = caches.get(locale);
    if (null == localeBundles) {
      localeBundles = CollectUtils.newConcurrentHashMap();
      caches.put(locale, localeBundles);
    }
    TextBundle bundle = localeBundles.get(bundleName);
    if (null == bundle) {
      Set<String> localeMissing = missings.get(locale);
      if (null != localeMissing && localeMissing.contains(bundleName)) return null;
      bundle = loadJavaBundle(bundleName, locale);
      if (null == bundle) bundle = loadNewBundle(bundleName, locale);
      if (null == bundle) {
        if (null == localeMissing) {
          localeMissing = new HashSet<String>();
          missings.put(locale, localeMissing);
        }
        localeMissing.add(bundleName);
      } else {
        localeBundles.put(bundleName, bundle);
      }
    }
    return bundle;
  }

  protected TextBundle loadNewBundle(String bundleName, Locale locale) {
    Map<String, String> texts = CollectUtils.newHashMap();
    String resource = toDefaultResourceName(bundleName, locale);
    try {
      InputStream is = ClassLoaders.getResourceAsStream(resource,getClass());
      if (null == is) return null;
      LineNumberReader reader = new LineNumberReader(new InputStreamReader(is, "UTF-8"));
      String line;
      while (null != (line = reader.readLine())) {
        int index = line.indexOf('=');
        if (index > 0) {
          texts.put(line.substring(0, index).trim(), line.substring(index + 1).trim());
        }
      }
      is.close();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
    }
    return new DefaultTextBundle(locale, resource, texts);
  }

  /**
   * Load java properties bundle with iso-8859-1
   * 
   * @param bundleName
   * @param locale
   * @return
   */
  protected TextBundle loadJavaBundle(String bundleName, Locale locale) {
    Properties properties = new Properties();
    String resource = toJavaResourceName(bundleName, locale);
    try {
      InputStream is = ClassLoaders.getResourceAsStream(resource,getClass());
      if (null == is) return null;
      properties.load(is);
      is.close();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
    }
    return new DefaultTextBundle(locale, resource, properties);
  }

  /**
   * java properties bundle name
   * 
   * @param bundleName
   * @param locale
   * @return
   */
  protected final String toJavaResourceName(String bundleName, Locale locale) {
    String fullName = bundleName;
    final String localeName = toLocaleStr(locale);
    final String suffix = "properties";
    if (!"".equals(localeName)) fullName = fullName + "_" + localeName;
    StringBuilder sb = new StringBuilder(fullName.length() + 1 + suffix.length());
    sb.append(fullName.replace('.', '/')).append('.').append(suffix);
    return sb.toString();
  }

  /**
   * Generater resource name like bundleName.zh_CN
   * 
   * @param bundleName
   * @param locale
   * @return
   */
  protected final String toDefaultResourceName(String bundleName, Locale locale) {
    String fullName = bundleName;
    final String localeName = toLocaleStr(locale);
    final String suffix = localeName;
    StringBuilder sb = new StringBuilder(fullName.length() + 1 + suffix.length());
    sb.append(fullName.replace('.', '/'));
    if (!"".equals(suffix)) sb.append('.').append(suffix);
    return sb.toString();
  }

  protected final String toLocaleStr(Locale locale) {
    if (locale == Locale.ROOT) { return ""; }
    String language = locale.getLanguage();
    String country = locale.getCountry();
    String variant = locale.getVariant();
    if (language == "" && country == "" && variant == "") { return ""; }

    StringBuilder sb = new StringBuilder();
    if (variant != "") {
      sb.append(language).append('_').append(country).append('_').append(variant);
    } else if (country != "") {
      sb.append(language).append('_').append(country);
    } else {
      sb.append(language);
    }
    return sb.toString();
  }

  public List<TextBundle> getBundles(Locale locale) {
    return CollectUtils.newArrayList(caches.get(locale).values());
  }

  public String getDefaultText(String key, Locale locale) {
    String msg = null;
    for (String defaultBundleName : defaults) {
      TextBundle bundle = load(locale, defaultBundleName);
      if (null != bundle) {
        msg = bundle.getText(key);
        if (null != msg) return msg;
      }
    }
    return null;
  }

  public void setReloadBundles(boolean reloadBundles) {
    this.reloadBundles = reloadBundles;
  }

}
