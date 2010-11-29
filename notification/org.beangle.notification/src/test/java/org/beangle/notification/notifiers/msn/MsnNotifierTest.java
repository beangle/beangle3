/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.msn;

import org.codehaus.plexus.msn.DefaultMsnClient;
import org.testng.annotations.Test;

//$Id:MsnNotifier.java Mar 23, 2009 9:54:19 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class MsnNotifierTest {

	@Test
	public void testSend() throws Exception {
		DefaultMsnClient msnClient = new DefaultMsnClient();
		msnClient.enableLogging(new org.codehaus.plexus.logging.console.ConsoleLogger(1, "if"));
		msnClient.setLogin("xx@hotmail.com");
		msnClient.setPassword("bb");

		MsnNotifier notifier = new MsnNotifier();
		notifier.setMsnClient(msnClient);

		// SimpleMessage message = new
		// SimpleMessage("chaostone.duan@hotmail.com", "hello", "hello");
		// notifier.sendMessage(message);
	}
}
