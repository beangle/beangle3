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
package org.beangle.commons.util.text.replacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

@Test
public class BatchReplaceMainTest {

  public void test() {
    String clause = "<#include \"/template/head.ftl\"/>";
    Pattern pattern = Pattern.compile("<#(.*)/>");
    Matcher m = pattern.matcher(clause);
    System.out.println(m.find());
    System.out.println(m.groupCount());
    System.out.println(Pattern.matches("<#(.*)/>", clause));
    System.out.println(m.group(1));
    StringBuffer sb = new StringBuffer();
    m.appendReplacement(sb, "[#$1/]");
    System.out.println(sb);

    System.out.println(Pattern.matches("template", clause));

    Pattern p = Pattern.compile("(cat)");
    Matcher m1 = p.matcher("one cat two cats in the yard");
    StringBuffer sb1 = new StringBuffer();
    while (m.find()) {
      m1.appendReplacement(sb1, "dog");
    }
    m1.appendTail(sb1);
    System.out.println(sb1.toString());
    System.out.println("one cat two cats in the yard".replaceAll("cat", "dog"));
    System.out.println(clause.replaceAll("<#(.*)/>", "[#$1/]"));

    String test = "aaa    \nbbaad\n";
    Replacer replacer = new Replacer("( +?)\\n", "\n");
    System.out.print(test);
    System.out.print(replacer.process(test));
  }
}
