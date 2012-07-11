/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.text.replacer;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Replace target with value on any input.
 * 
 * @author chaostone
 * @version $Id: $
 */
public class Replacer {

  Pattern pattern;

  String target;

  String value;

  /**
   * <p>
   * Constructor for Replacer.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param value a {@link java.lang.String} object.
   */
  public Replacer(String key, String value) {
    super();
    this.target = key;
    pattern = Pattern.compile(key);
    this.value = value;
  }

  /**
   * <p>
   * process.
   * </p>
   * 
   * @param input a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public String process(String input) {
    return pattern.matcher(input).replaceAll(value);
  }

  /**
   * <p>
   * process.
   * </p>
   * 
   * @param input a {@link java.lang.String} object.
   * @param replacers a {@link java.util.List} object.
   * @return a {@link java.lang.String} object.
   */
  public static String process(String input, List<Replacer> replacers) {
    if (null == input) { return null; }
    for (Replacer replacer : replacers) {
      input = replacer.process(input);
    }
    return input;
  }

  /**
   * <p>
   * toString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return target + "=" + value;
  }

}
