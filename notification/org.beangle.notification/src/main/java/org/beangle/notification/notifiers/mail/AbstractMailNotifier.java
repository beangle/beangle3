/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.beangle.notification.Message;
import org.beangle.notification.NotificationException;
import org.beangle.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public abstract class AbstractMailNotifier<T extends MailMessage> implements Notifier<T> {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractMailNotifier.class);

	protected JavaMailSender mailSender;

	private String from;

	private InternetAddress[] froms;

	public String getType() {
		return "mail";
	}

	public void deliver(T mailMsg) throws NotificationException {
		MimeMessage mimeMsg = mailSender.createMimeMessage();
		try {
			if (null == mailMsg.getSendAt()) {
				mimeMsg.setSentDate(new Date());
			} else {
				mimeMsg.setSentDate(mailMsg.getSendAt());
			}
			MimeMessageHelper messageHelper = null;
			String encoding = StringUtils.substringAfter(mailMsg.getContentType(), "charset=");
			if (StringUtils.isEmpty(encoding)) {
				messageHelper = new MimeMessageHelper(mimeMsg);
			} else {
				messageHelper = new MimeMessageHelper(mimeMsg, encoding);
			}
			messageHelper.setText(buildText(mailMsg), StringUtils.contains(mailMsg.getContentType(), "html"));
			String subject = buildSubject(mailMsg);
			messageHelper.setSubject(subject);
			int recipients = addRecipient(mimeMsg, mailMsg);
			beforeSend(mailMsg, mimeMsg);
			if (recipients > 0) {
				mailSender.send(mimeMsg);
				if (logger.isDebugEnabled()) {
					logger.debug("mail sended from {} to {} with subject {}",
							new Object[] { from, mailMsg.getRecipients(), subject });
				}
			} else {
				logger.warn("{} without any recipients ,sending aborted!", subject);
			}
		} catch (AddressException ex) {
			throw new NotificationException("Exception while sending message.", ex);
		} catch (MessagingException ex) {
			throw new NotificationException("Exception while sending message.", ex);
		}
		afterSend(mailMsg, mimeMsg);
	}

	// add from and other recipients
	private int addRecipient(MimeMessage mimeMsg, MailMessage mailMsg) throws MessagingException {
		String encoding = mailMsg.getEncoding();
		if (null == froms) {
			List<InternetAddress> addresses = MimeUtils.parseAddress(from, encoding);
			InternetAddress[] addressArray = new InternetAddress[addresses.size()];
			if (addressArray.length > 0) {
				addresses.toArray(addressArray);
				froms = addressArray;
			}
		}
		int recipients = 0;
		if (null != froms) mimeMsg.addFrom(froms);
		for (InternetAddress to : mailMsg.getTo()) {
			mimeMsg.addRecipient(javax.mail.Message.RecipientType.TO, to);
			recipients++;
		}
		for (InternetAddress cc : mailMsg.getCc()) {
			mimeMsg.addRecipient(javax.mail.Message.RecipientType.CC, cc);
			recipients++;
		}
		for (InternetAddress bcc : mailMsg.getBcc()) {
			mimeMsg.addRecipient(javax.mail.Message.RecipientType.BCC, bcc);
			recipients++;
		}
		return recipients;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		Validate.notEmpty(from);
		this.from = from;
	}

}
