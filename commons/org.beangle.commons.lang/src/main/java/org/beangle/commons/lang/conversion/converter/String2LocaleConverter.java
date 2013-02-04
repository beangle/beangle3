package org.beangle.commons.lang.conversion.converter;

import java.util.Locale;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.conversion.Converter;

/**
 * 
 * @author chaostone
 *
 */
public class String2LocaleConverter implements Converter<String, Locale> {

  @Override
  public Locale apply(String localeStr) {
    if (Strings.isBlank(localeStr)) return null;

    int index = localeStr.indexOf('_');
    if (index < 0) return new Locale(localeStr);

    String language = localeStr.substring(0, index);
    if (index == localeStr.length()) return new Locale(language);

    localeStr = localeStr.substring(index + 1);
    index = localeStr.indexOf('_');
    if (index < 0) return new Locale(language, localeStr);

    String country = localeStr.substring(0, index);
    if (index == localeStr.length()) return new Locale(language, country);

    localeStr = localeStr.substring(index + 1);
    return new Locale(language, country, localeStr);
  }

}
