/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.beangle.security.core.session.category.SessionCleanerTask;
import org.mockito.Mockito;
import org.testng.annotations.Test;

/**
 * @author chaostone
 * @version $Id: SessionCleanerTest.java Jun 6, 2011 2:22:42 PM chaostone $
 */
@Test
public class SessionCleanerTest {

	public void testShouldRemove() {
		SessionCleanerTask cleaner = new SessionCleanerTask(null);
		
		Sessioninfo activity =Mockito.mock(Sessioninfo.class);
		//new SessioninfoBean("test session id", "testserver", null, null);
		assertFalse(cleaner.shouldRemove(activity));

		cleaner = new SessionCleanerTask(null, 0);
		assertTrue(cleaner.shouldRemove(activity));
	}
}
