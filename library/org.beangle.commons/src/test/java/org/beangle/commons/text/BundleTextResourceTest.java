/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class BundleTextResourceTest {

	@Test
	public void testGetText() {
		Locale locale = new Locale("zh", "CN");
		ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
		assertNotNull(bundle);
		BundleTextResource tr = new BundleTextResource();
		tr.setLocale(locale);
		tr.setBundle(bundle);
		assertEquals(tr.getText("hello.world"), "nihao");
	}
}
