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
package org.beangle.ems.io;

import java.net.URL;

public class ClasspathDocLoader implements StaticFileLoader {

  public URL getFile(String filename) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(filename);

    if (url == null) {
      url = ClasspathDocLoader.class.getClassLoader().getResource(filename);
    }
    if ((url == null) && (filename != null) && ((filename.length() == 0) || (filename.charAt(0) != '/'))) { return getFile('/' + filename); }
    return url;
  }

}
