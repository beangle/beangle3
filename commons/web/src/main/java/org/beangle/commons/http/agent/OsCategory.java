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
package org.beangle.commons.http.agent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.tuple.Pair;

/**
 * Enum constants for most common operating systems.
 *
 * @author chaostone
 */
public enum OsCategory {

  Windows("Windows", "Windows NT 6.1->7", "Windows NT 6->Vista", "Windows NT 5.0->2000", "Windows NT 5->XP",
      "Win98->98", "Windows 98->98", "Windows Phone OS 7->Mobile 7", "Windows CE->Mobile", "Windows"),

  Android("Android", "Android-4->4.x", "Android 4->4.x", "Xoom->4.x Tablet", "Transformer->4.x Tablet",
      "Android 3->3.x Tablet", "Android 2->2.x", "Kindle Fire->2.x Tablet", "GT-P1000->2.x Tablet",
      "SCH-I800->2.x Tablet", "Android 1->1.x", "GoogleTV->(Google TV)", "Android"),

  Linux("Linux", "Fedora/(\\S*)\\.fc(\\S*)->Fedora fc$2", "Ubuntu/(\\S*)->Ubuntu $1", "Fedora", "Ubuntu",
      "Linux", "CamelHttpStream"),

  Webos("WebOS", "webOS"),

  Palm("PalmOS", "Palm"),

  Ios("iOS", "iPhone OS(\\S*)->$1 (iPhone)", "like Mac OS X", "iOS"),

  MacOs("Mac OS", "iPad->(iPad)", "iPhone->(iPhone)", "iPod->(iPod)", "Mac OS X->X", "CFNetwork->X", "Mac"),

  Maemo("Maemo", "Maemo"),

  Bada("Bada", "Bada"),

  Kindle("Kindle", "Kindle/(\\S*)->(Kindle $1)", "Kindle"),

  Symbian("Symbian OS", "SymbianOS/(\\S*)->$1", "Series60/3->9.x", "Series60/2.6->8.x", "Series60/2.8->8.x",
      "Symbian", "Series60"),

  Series40("Series 40", "Nokia6300"),

  SonyEricsson("Sony Ericsson", "SonyEricsson"),

  SunOs("SunOS", "SunOS"),

  Psp("Sony Playstation", "Playstation"),

  Wii("Nintendo Wii", "Wii"),

  BlackBerry("BlackBerryOS", "(BB|BlackBerry|PlayBook)(.*?)Version/(\\S*)->$3", "BlackBerry"),

  Roku("Roku OS", "Roku"),

  Unknown("Unknown", new String[0]);

  private final String name;

  private final List<Pair<Pattern, String>> versionPairs = CollectUtils.newArrayList();

  private OsCategory(String name, String... versions) {
    this.name = name;
    for (String version : versions) {
      String matcheTarget = version;
      String versionNum = "";
      if (Strings.contains(version, "->")) {
        matcheTarget = "(?i)" + Strings.substringBefore(version, "->");
        versionNum = Strings.substringAfter(version, "->");
      }
      versionPairs.add(Pair.of(Pattern.compile(matcheTarget), versionNum));
    }
  }

  public String match(String agentString) {
    for (Pair<Pattern, String> entry : versionPairs) {
      Matcher m = entry.getKey().matcher(agentString);
      if (m.find()) {
        StringBuffer sb = new StringBuffer();
        m.appendReplacement(sb, entry.getValue());
        sb.delete(0, m.start());
        return sb.toString();
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }
}
