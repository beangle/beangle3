/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.agent;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
@Test
public class OsTest {

	String[] windows = {
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows 98; Rogers HiáSpeed Internet; (R1 1.3))",
			"Mozilla/5.0 (Windows; U; Win98; en-US; rv:1.8b3) Gecko/20050713 SeaMonkey/1.0a",
			"Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 ( .NET CLR 3.5.30729)",
			"Mozilla/5.0 (compatible; MSIE 7.0; Windows NT 5.2; WOW64; .NET CLR 2.0.50727)",
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; SLCC1; .NET CLR 2.0.50727; .NET CLR 3.0.04506)" };

	String [] linux={
			"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.12) Gecko/20101027 Fedora/3.6.12-1.fc14 Firefox/3.6.12"
	};
	
	public void testParse() {
		Os os = Os.parse(windows[0]);
		assertEquals(os.category, OsCategory.WINDOWS);
		assertEquals(os.version,"98");
		
		os = Os.parse(windows[2]);
		assertEquals(os.version,"XP");
		
		os = Os.parse(windows[4]);
		assertEquals(os.version,"Vista");
		
		os = Os.parse(linux[0]);
		assertEquals(os.version,"Fedora fc14");
	}
}
