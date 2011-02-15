/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import java.util.ArrayList;
import java.util.List;

public class SimpleMessage extends AbstractMessage {

	private List<String> recipients = new ArrayList<String>();

	public SimpleMessage() {
		super();
	}

	public SimpleMessage(String recipient, String subject, String text) {
		recipients.add(recipient);
		setSubject(subject);
		setText(text);
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public String getContentType() {
		return "text/plain";
	}

}
