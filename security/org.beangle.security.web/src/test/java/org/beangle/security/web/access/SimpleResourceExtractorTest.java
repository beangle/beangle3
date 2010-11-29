/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import static org.testng.Assert.assertEquals;

import org.beangle.security.web.access.SimpleResourceExtractor;
import org.testng.annotations.Test;

public class SimpleResourceExtractorTest {

	@Test
	public void testExtract() throws Exception {
		assertEquals(new SimpleResourceExtractor().extract("a.jsp"), "a.jsp");
		assertEquals(new SimpleResourceExtractor().extract("/b.do"), "b.do");
		assertEquals(new SimpleResourceExtractor().extract("/c/d.action?method=aa"), "c/d.action");
	}
}
