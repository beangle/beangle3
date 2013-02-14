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
package org.beangle.commons.text.replace;

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
