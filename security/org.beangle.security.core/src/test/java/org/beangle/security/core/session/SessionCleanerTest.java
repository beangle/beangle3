/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author chaostone
 * @version $Id: SessionCleanerTest.java Jun 6, 2011 2:22:42 PM chaostone $
 */
@Test
public class SessionCleanerTest {

	public void testShouldRemove() {
		SessionCleaner cleaner = new SessionCleaner(null);
		SessionInfo info = new SessionInfo(null, "test session id");
		assertFalse(cleaner.shouldRemove(info));
		
		cleaner = new SessionCleaner(null,0);
		assertTrue(cleaner.shouldRemove(info));
	}
}
