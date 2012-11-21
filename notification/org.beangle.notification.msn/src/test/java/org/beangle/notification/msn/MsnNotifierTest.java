/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.notification.msn;

import org.beangle.notification.msn.MsnNotifier;
import org.codehaus.plexus.msn.DefaultMsnClient;
import org.testng.annotations.Test;

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
