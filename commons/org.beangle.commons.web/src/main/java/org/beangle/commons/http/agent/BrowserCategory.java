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
package org.beangle.commons.http.agent;

import static org.beangle.commons.http.agent.Engine.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.tuple.Pair;

/**
 * Enum constants for most common browsers, including e-mail clients and bots.
 * 
 * @author chaostone
 */

public enum BrowserCategory {
  // Gecko series
  Firefox("Firefox", Gecko, "Firefox/(\\S*)->$1", "Firefox"),

  Thunderbird("Thunderbird", Gecko, "Thunderbird/(\\S*)->$1", "Thunderbird"),

  Camino("Camino", Gecko, "Camino/(\\S*)->$1", "Camino"),

  Flock("Flock", Gecko, "Flock/(\\S*)->$1"),

  FirefoxMobile("Firefox Mobile", Gecko, "Firefox/3.5 Maemo->3"), // ??? need validate

  SeaMonkey("SeaMonkey", Gecko, "SeaMonkey"),

  // IE series
  Tencent("Tencent Traveler", Trident, "TencentTraveler (\\S*);->$1"),

  Sogo("Sogo", Trident, "SE(.*)MetaSr"),

  TheWorld("The World", Trident, "theworld"),

  IE360("Internet Explorer 360", Trident, "360SE"),

  IeMobile("IE Mobile", Trident, "IEMobile (\\S*)->$1"),

  IE("Internet Explorer", Trident, "MSIE (\\S*);->$1", "MSIE"),

  OutlookExpress("Windows Live Mail", Trident, "Outlook-Express/7.0->7.0"),

  // WebKit
  Maxthon("Maxthon", WebKit, "Maxthon/(\\S*)->$1", "Maxthon"),

  Chrome("Chrome", WebKit, "Chrome/(\\S*)->$1", "Chrome"),

  Safari("Safari", WebKit, "Version/(\\S*) Safari->$1", "Safari"),

  Omniweb("Omniweb", WebKit, "OmniWeb"),

  AppleMail("Apple Mail", WebKit, "AppleWebKit"),

  ChromeMobile("Chrome Mobile", WebKit, "CrMo/(\\S*)->$1"), // ??? need validate

  SafariMobile("Mobile Safari", WebKit, "Mobile Safari", "Mobile/5A347 Safari", "Mobile/3A101a Safari",
      "Mobile/7B367 Safari"),

  Silk("Silk", WebKit, "Silk/(\\S*)->$1"),

  Dolfin("Samsung Dolphin", WebKit, "Dolfin/(\\S*)->$1"),

  // Presto
  Opera("Opera", Presto, "Opera/(.*?)Version/(\\S*)->$2", "Opera Mini->Mini", "Opera"),

  // Khtml
  Konqueror("Konqueror", Khtml, "Konqueror"),

  // Word
  Outlook("Outlook", Word, "MSOffice 12->2007", "MSOffice 14->2010", "MSOffice"),

  // Others
  LotusNotes("Lotus Notes", Other, "Lotus-Notes"),

  Bot("Robot/Spider", Other, "Googlebot", "bot", "spider", "crawler", "Feedfetcher", "Slurp", "Twiceler",
      "Nutch", "BecomeBot"),

  Mozilla("Mozilla", Other, "Mozilla", "Moozilla"),

  CFNetwork("CFNetwork", Other, "CFNetwork"),

  Eudora("Eudora", Other, "Eudora", "EUDORA"),

  PocoMail("PocoMail", Other, "PocoMail"),

  TheBat("The Bat!", Other, "The Bat"),

  NetFront("NetFront", Other, "NetFront"),

  Evolution("Evolution", Other, "CamelHttpStream"),

  Lynx("Lynx", Other, "Lynx"), // ??? need validate

  UC("UC", Other, "UCWEB"),

  Download("Downloading Tool", Other, "cURL", "wget"),

  Unknown("Unknown", Other);

  private final String name;
  private final Engine engine;
  private final List<Pair<Pattern, String>> versionPairs = CollectUtils.newArrayList();

  private BrowserCategory(String name, Engine renderEngine, String... versions) {
    this.name = name;
    this.engine = renderEngine;
    engine.addCategory(this);
    for (String version : versions) {
      String matcheTarget = version;
      String versionNum = "";
      if (Strings.contains(version, "->")) {
        // regular expression match ignore case
        matcheTarget = "(?i)" + Strings.substringBefore(version, "->");
        versionNum = Strings.substringAfter(version, "->");
      }
      versionPairs.add(Pair.of(Pattern.compile(matcheTarget), versionNum));
    }
  }

  public String getName() {
    return name;
  }

  /**
   * @return the rendering engine
   */
  public Engine getEngine() {
    return engine;
  }

  public String match(String agentString) {
    for (Pair<Pattern, String> pair : versionPairs) {
      Matcher m = pair.getKey().matcher(agentString);
      if (m.find()) {
        StringBuffer sb = new StringBuffer();
        m.appendReplacement(sb, pair.getValue());
        sb.delete(0, m.start());
        return sb.toString();
      }
    }
    return null;
  }
}
