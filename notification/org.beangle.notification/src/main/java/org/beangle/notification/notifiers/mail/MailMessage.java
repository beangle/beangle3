/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.notification.AbstractMessage;

public class MailMessage extends AbstractMessage {

	private List<InternetAddress> to = CollectUtils.newArrayList();

	private List<InternetAddress> cc = CollectUtils.newArrayList();

	private List<InternetAddress> bcc = CollectUtils.newArrayList();

	private Date sendAt;
	
	public MailMessage() {
		super();
	}

	public String getEncoding() {
		return StringUtils.substringAfter(getContentType(), "charset=");
	}

	public List<String> getRecipients() {
		List<String> recipients = new ArrayList<String>();
		for (InternetAddress address : to)
			recipients.add(address.toString());
		for (InternetAddress address : cc)
			recipients.add(address.toString());
		for (InternetAddress address : bcc)
			recipients.add(address.toString());
		return recipients;
	}

	public MailMessage(String subject, String text, String sendTo) {
		to = MimeUtils.parseAddress(sendTo, getEncoding());
		setSubject(subject);
		setText(text);
	}

	public MailMessage(String subject, String text, String sendTo, String sendCc, String sendBcc) {
		to = MimeUtils.parseAddress(sendTo, getEncoding());
		cc = MimeUtils.parseAddress(sendCc, getEncoding());
		bcc = MimeUtils.parseAddress(sendBcc, getEncoding());
		setSubject(subject);
		setText(text);
	}

	public List<InternetAddress> getTo() {
		return to;
	}

	public List<InternetAddress> getCc() {
		return cc;
	}

	public List<InternetAddress> getBcc() {
		return bcc;
	}

	public void addTo(String sendTo) {
		Validate.notNull(sendTo);
		this.to.addAll(MimeUtils.parseAddress(sendTo, getEncoding()));
	}

	public void addCc(String sendCc) {
		Validate.notNull(sendCc);
		this.cc.addAll(MimeUtils.parseAddress(sendCc, getEncoding()));
	}

	public void addBcc(String sendBcc) {
		Validate.notNull(sendBcc);
		this.bcc.addAll(MimeUtils.parseAddress(sendBcc, getEncoding()));
	}

	public Date getSendAt() {
		return sendAt;
	}

	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}
	
}
