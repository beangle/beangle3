package org.beangle.commons.lang.conversion.converter;

import java.util.HashSet;
import java.util.Set;

import org.beangle.commons.lang.conversion.Converter;

/**
 * @author chaostone
 */
public class String2BooleanConverter implements Converter<String, Boolean> {
  private static final Set<String> trueValues = new HashSet<String>(4);

  private static final Set<String> falseValues = new HashSet<String>(4);

  static {
    trueValues.add("true");
    trueValues.add("on");
    trueValues.add("yes");
    trueValues.add("Y");
    trueValues.add("1");

    falseValues.add("false");
    falseValues.add("off");
    falseValues.add("no");
    falseValues.add("N");
    falseValues.add("0");
  }

  @Override
  public Boolean apply(String input) {
    String value = input.toLowerCase();
    if (trueValues.contains(value)) return Boolean.TRUE;
    else if (falseValues.contains(value)) return Boolean.FALSE;
    return null;
  }

}
