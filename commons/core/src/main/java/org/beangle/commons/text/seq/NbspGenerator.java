/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.text.seq;

/**
 * <p>
 * NbspGenerator class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public class NbspGenerator {

  /**
   * <p>
   * generator.
   * </p>
   *
   * @param repeat a int.
   * @return a {@link java.lang.String} object.
   */
  public String generator(int repeat) {
    String repeater = "&nbsp;";
    StringBuilder returnval = new StringBuilder();
    for (int i = 0; i < repeat; i++) {
      returnval.append(repeater);
    }
    return returnval.toString();
  }
}
