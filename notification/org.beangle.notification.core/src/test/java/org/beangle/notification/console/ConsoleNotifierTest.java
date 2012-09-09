/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.console;

import org.beangle.notification.Message;
import org.beangle.notification.Notifier;
import org.beangle.notification.SimpleMessage;
import org.beangle.notification.console.ConsoleNotifier;
import org.testng.annotations.Test;

public class ConsoleNotifierTest {

  @Test
  public void testSendMessage() throws Exception {
    Notifier<Message> notifier = new ConsoleNotifier();
    SimpleMessage context = new SimpleMessage();
    context.setText("hello world");
    notifier.deliver(context);
  }
}
