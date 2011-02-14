/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import org.beangle.notification.Message;
import org.springframework.mail.javamail.JavaMailSender;

//$Id:DefaultMailNotifier.java Mar 22, 2009 12:36:40 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultMailNotifier extends AbstractMailNotifier {

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
