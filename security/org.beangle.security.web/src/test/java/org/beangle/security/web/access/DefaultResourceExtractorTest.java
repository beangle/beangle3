/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import static org.testng.Assert.assertEquals;

import org.beangle.security.web.access.DefaultResourceExtractor;
import org.testng.annotations.Test;

public class DefaultResourceExtractorTest {

	@Test
	public void testExtract() throws Exception {
		assertEquals(new DefaultResourceExtractor().extract("a.jsp"), "a");
		assertEquals(new DefaultResourceExtractor().extract("/b.do"), "b");
		assertEquals(new DefaultResourceExtractor().extract("/c/d.action"), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("c/d.action"), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("//c/d.action"), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("  //c/d.action "), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("  c/d.action "), "c/d");

		assertEquals(new DefaultResourceExtractor().extract("  c/d!remove.action "), "c/d");
	}
}
