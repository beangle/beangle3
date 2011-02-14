/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.beangle.notification.Message;
import org.beangle.notification.NotificationException;
import org.beangle.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

//$Id:AbstractMailNotifier.java Mar 22, 2009 12:04:05 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public abstract class AbstractMailNotifier implements Notifier {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractMailNotifier.class);

	protected JavaMailSender mailSender;

	private String fromMailbox;

	private String fromName;

	public String getType() {
		return "mail";
	}

	public void sendMessage(Message msg) throws NotificationException {
		// contruct a MailMessage
		MailMessage mailConext = null;
		if (msg instanceof MailMessage) {
			mailConext = (MailMessage) msg;
		} else {
			mailConext = new MailMessage();
			mailConext.setSubject(msg.getSubject());
			mailConext.setText(msg.getText());
			mailConext.setProperties(msg.getProperties());
			String[] to = new String[msg.getRecipients().size()];
			msg.getRecipients().toArray(to);
			mailConext.setTo(to);
		}

		MimeMessage mimeMsg = mailSender.createMimeMessage();
		try {
			mimeMsg.setSentDate(new Date());
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMsg);
			messageHelper.setText(buildText(mailConext), Message.HTML.equals(mailConext.getContentType()));
			String subject = buildSubject(mailConext);
			messageHelper.setSubject(subject);
			messageHelper.setFrom(fromMailbox, fromName);
			addRecipient(mimeMsg, javax.mail.Message.RecipientType.TO, mailConext.getTo());
			addRecipient(mimeMsg, javax.mail.Message.RecipientType.CC, mailConext.getCc());
			addRecipient(mimeMsg, javax.mail.Message.RecipientType.BCC, mailConext.getBcc());
			beforeSend(mailConext, mimeMsg);
			if (mimeMsg.getAllRecipients() != null && ((Address[]) mimeMsg.getAllRecipients()).length > 0) {
				mailSender.send(mimeMsg);
				logger.info("mail sended from {} to {} with subject {}", new Object[] { fromMailbox,
						mailConext.getRecipients(), subject });
			}
		} catch (AddressException ex) {
			throw new NotificationException("Exception while sending message.", ex);
		} catch (MessagingException ex) {
			throw new NotificationException("Exception while sending message.", ex);
		} catch (UnsupportedEncodingException ex) {
			throw new NotificationException("Exception while sending message.", ex);
		}
		afterSend(mailConext, mimeMsg);
	}

	private void addRecipient(MimeMessage message, RecipientType recipientType, String[] recipients)
			throws AddressException, MessagingException {
		if (null != recipients) {
			for (int i = 0; i < recipients.length; i++) {
				InternetAddress to = new InternetAddress(recipients[i].trim());
				message.addRecipient(recipientType, to);
			}
		}
	}

	abstract protected String buildSubject(Message msg);

	abstract protected String buildText(Message msg);

	protected void beforeSend(Message msg, MimeMessage mimeMsg) {
	}

	protected void afterSend(Message msg, MimeMessage mimeMsg) {
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender javaMailSender) {
		this.mailSender = javaMailSender;
	}

	public String getFromMailbox() {
		return fromMailbox;
	}

	public void setFromMailbox(String fromMailbox) {
		this.fromMailbox = fromMailbox;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

}
