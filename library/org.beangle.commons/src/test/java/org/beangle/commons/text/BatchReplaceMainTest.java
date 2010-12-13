/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

@Test
public class BatchReplaceMainTest {

	public void test(){
		String clause="<#include \"/template/head.ftl\"/>";
		Pattern pattern=Pattern.compile("<#(.*)/>");
		Matcher m=pattern.matcher(clause);
		System.out.println(m.find());
		System.out.println(m.groupCount());
		System.out.println(pattern.matches("<#(.*)/>", clause));
		System.out.println(m.group(1));
		StringBuffer sb=new StringBuffer();
		m.appendReplacement(sb, "[#$1/]");
		System.out.println(sb);
		
		System.out.println(pattern.matches("template", clause));
		
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
	}
}
