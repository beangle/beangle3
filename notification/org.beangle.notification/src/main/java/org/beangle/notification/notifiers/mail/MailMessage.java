/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import java.util.ArrayList;
import java.util.List;

import org.beangle.notification.AbstractMessage;

//$Id:MailMessage.java Mar 22, 2009 1:31:32 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class MailMessage extends AbstractMessage {

	private String[] to;

	private String[] cc;

	private String[] bcc;

	public MailMessage() {
		super();
	}

	public List<String> getRecipients() {
		List<String> recipients = new ArrayList<String>();
		if (null != to) {
			for (int i = 0; i < to.length; i++) {
				recipients.add(to[i]);
			}
		}
		if (null != cc) {
			for (int i = 0; i < cc.length; i++) {
				recipients.add(cc[i]);
			}
		}
		if (null != bcc) {
			for (int i = 0; i < bcc.length; i++) {
				recipients.add(bcc[i]);
			}
		}
		return recipients;
	}

	public MailMessage(String sendTo, String subject, String text) {
		to = new String[] { sendTo };
		setSubject(subject);
		setText(text);
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

}
