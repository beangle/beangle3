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

import org.beangle.notification.Message;
import org.beangle.notification.NotificationException;
import org.beangle.notification.Notifier;
import org.codehaus.plexus.msn.MsnClient;
import org.codehaus.plexus.msn.MsnException;

public class MsnNotifier implements Notifier<Message> {

  private MsnClient msnClient;

  public String getType() {
    return "msn";
  }

  public void deliver(Message msg) throws NotificationException {
    // MSNMessenger msn;
    // msn = new MSNMessenger("youraccount@hotmail.com", "password");
    // msn.setInitialStatus(UserStatus.ONLINE);
    // // msn.addMsnListener(new MSNAdapter(msn));
    // msn.login();
    // System.out.println("Waiting for the response....");
    // 捕捉Ctrl+C的输入以便注销MSN的登录
    // Runtime.getRuntime().addShutdownHook(new MSNDaemon());

    try {
      msnClient.login();
      for (final String recipient : msg.getRecipients()) {
        msnClient.sendMessage(recipient, msg.getText());
      }
    } catch (MsnException e) {
      throw new RuntimeException("Exception while sending message.", e);
    } finally {
      try {
        msnClient.logout();
      } catch (MsnException e) {

      }
    }

  }

  public MsnClient getMsnClient() {
    return msnClient;
  }

  public void setMsnClient(MsnClient msnClient) {
    this.msnClient = msnClient;
  }

}
