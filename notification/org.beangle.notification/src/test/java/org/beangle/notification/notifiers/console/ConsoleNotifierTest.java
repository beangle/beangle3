/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.console;

import org.beangle.notification.Notifier;
import org.beangle.notification.SimpleMessage;
import org.testng.annotations.Test;

//$Id:ConsoleNotifierTest.java Mar 22, 2009 11:45:02 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class ConsoleNotifierTest {

	@Test
	public void testSendMessage() throws Exception {
		Notifier notifier = new ConsoleNotifier();
		SimpleMessage context = new SimpleMessage();
		context.setText("hello world");
		notifier.sendMessage(context);
	}
}
