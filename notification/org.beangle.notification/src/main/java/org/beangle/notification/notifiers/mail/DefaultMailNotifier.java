/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import org.beangle.notification.Message;
import org.springframework.mail.javamail.JavaMailSender;

public class DefaultMailNotifier<T extends MailMessage> extends AbstractMailNotifier<T> {

	public DefaultMailNotifier() {
		super();
	}

	public DefaultMailNotifier(JavaMailSender javaMailSender) {
		this.mailSender = javaMailSender;
	}

	protected String buildSubject(Message context) {
		return context.getSubject();
	}

	protected String buildText(Message context) {
		return context.getText();
	}

}
