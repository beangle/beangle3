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
package org.beangle.struts2.view.tag;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

public class Static {

  public static Static Default = new Static();
  static {
    Default.resources.put("bui", "0.2.0");
    Default.resources.put("bootstrap", "3.3.7");
    Default.resources.put("jquery", "1.10.2");
    Default.resources.put("requirejs", "2.3.6");
    Default.resources.put("my97", "4.8");
    Default.resources.put("kindeditor", "4.1.12");
    Default.resources.put("jquery-form", "4.2.2");
    Default.resources.put("jquery-colorbox", "1.6.4");
    Default.resources.put("jquery-ui", "1.12.1");
    Default.resources.put("font-awesome", "4.7.0");
    Default.resources.put("struts2-jquery", "3.6.1");
  }

  private Map<String, String> resources = CollectUtils.newHashMap();

  public String url(String base, String bundle, String file) {
    String fileName = null;
    if (Strings.isEmpty(file)) {
      fileName = "";
    } else {
      fileName = (file.charAt(0) == '/') ? file : ("/" + file);
    }
    String version = resources.get(bundle);
    return base + "/" + bundle + "/" + version + fileName;
  }

}
