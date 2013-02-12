/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.lang.conversion.converter;

import java.util.HashSet;
import java.util.Set;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.conversion.Converter;

/**
 * Convert String to Boolean.
 * <p>
 * Convert true,on,yes,Y,1 to Boolean.TRUE.<br>
 * Convert false,off,no,N,0 to Boolean.FALSE. <br>
 * Otherwise null.
 * 
 * @author chaostone
 * @since 3.2.0
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
    if(Strings.isEmpty(input))return null;
    String value = input.toLowerCase();
    if (trueValues.contains(value)) return Boolean.TRUE;
    else if (falseValues.contains(value)) return Boolean.FALSE;
    return null;
  }

}
