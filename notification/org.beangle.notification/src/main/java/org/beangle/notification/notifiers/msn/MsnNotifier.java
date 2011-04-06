/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.msn;

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
