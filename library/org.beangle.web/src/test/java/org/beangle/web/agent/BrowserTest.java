/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.agent;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

@Test
public class BrowserTest {
	String firefox3 = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.14) Gecko/2009090216 Ubuntu/9.04 (jaunty) Firefox/3.0.14";

	String[] firefox4 = new String[] {
			"Mozilla/5.0 (X11; Linux x86_64; rv:2.0b4) Gecko/20100818 Firefox/4.0b4",
			"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0b9pre) Gecko/20101228 Firefox/4.0b9pre",
			"Firefox"};

	String[] ie = new String[] {
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; T312461)",
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)",
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; Zune 4.0; InfoPath.3; MS-RTC LM 8; .NET4.0C; .NET4.0E)" };

	String[] chrome = new String[] {
			"Mozilla/5.0 (Windows; U; Windows NT 5.2; en-US) AppleWebKit/532.9 (KHTML, like Gecko) Chrome/5.0.310.0 Safari/532.9",
			"Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/532.9 (KHTML, like Gecko) Chrome/5.0.309.0 Safari/532.9"
	};

	String[] validator = new String[] {
			"Total Validator"
	};
	public void testPaser() {
		Browser browser = Browser.parse(firefox3);
		assertEquals(browser.version, "3.0.14");
		assertEquals(browser.category, BrowserCategory.FIREFOX);

		browser = Browser.parse(firefox4[0]);
		assertEquals(browser.version, "4.0b4");
		browser = Browser.parse(firefox4[1]);
		assertEquals(browser.version, "4.0b9pre");
		
		browser = Browser.parse(firefox4[2]);
		assertEquals(browser.version, "");
		assertEquals(browser.category,BrowserCategory.FIREFOX);
		
		browser = Browser.parse(ie[0]);
		assertEquals(browser.version, "6.0");
		assertEquals(browser.category, BrowserCategory.IE);

		browser = Browser.parse(chrome[0]);
		assertEquals(browser.version, "5.0.310.0");
		assertEquals(browser.category, BrowserCategory.CHROME);
		
		browser = Browser.parse(validator[0]);
		assertEquals(browser.version, null);
		assertEquals(browser.category, BrowserCategory.UNKNOWN);
	}
}
