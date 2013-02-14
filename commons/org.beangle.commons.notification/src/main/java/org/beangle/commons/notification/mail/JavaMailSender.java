/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.notification.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;
import org.beangle.commons.notification.NotificationException;
import org.beangle.commons.notification.NotificationSendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaMailSender implements MailSender {

  protected static final Logger logger = LoggerFactory.getLogger(JavaMailSender.class);

  private static final String HEADER_MESSAGE_ID = "Message-ID";

  private Properties javaMailProperties = new Properties();

  private Session session;

  private String protocol = "smtp";

  private String host;

  private int port = -1;

  private String username;

  private String password;

  private String defaultEncoding;

  public void send(MailMessage... messages) {
    List<MimeMessage> mimeMsgs = CollectUtils.newArrayList();
    for (MailMessage m : messages) {
      try {
        mimeMsgs.add(createMimeMessage(m));
      } catch (MessagingException e) {
        logger.error("Cannot mapping message" + m.getSubject(), e);
      }
    }
    doSend(mimeMsgs.toArray(new MimeMessage[mimeMsgs.size()]));
  }

  protected MimeMessage createMimeMessage(MailMessage mailMsg) throws MessagingException {
    MimeMessage mimeMsg = new MimeMessage(getSession());

    mimeMsg.setSentDate(null == mailMsg.getSentAt() ? new Date() : mailMsg.getSentAt());
    if (null != mailMsg.getFrom()) mimeMsg.setFrom(mailMsg.getFrom());
    addRecipient(mimeMsg, mailMsg);

    String encoding = Strings.substringAfter(mailMsg.getContentType(), "charset=");
    try {
      mimeMsg.setSubject(MimeUtility.encodeText(mailMsg.getSubject(), encoding, "B"));
    } catch (UnsupportedEncodingException e) {
      Throwables.propagate(e);
    }
    final String text = mailMsg.getText();
    boolean html = Strings.contains(mailMsg.getContentType(), "html");
    if (html) {
      if (Strings.isEmpty(encoding)) mimeMsg.setContent(text, "text/html");
      else mimeMsg.setContent(text, "text/html;charset=" + encoding);
    } else {
      if (Strings.isEmpty(encoding)) mimeMsg.setText(text);
      else mimeMsg.setText(text, encoding);
    }
    return mimeMsg;
  }

  protected synchronized Session getSession() {
    if (this.session == null) this.session = Session.getInstance(this.javaMailProperties);
    return this.session;
  }

  protected Transport getTransport(Session session) throws NoSuchProviderException {
    String protocol = getProtocol();
    if (protocol == null) protocol = session.getProperty("mail.transport.protocol");
    return session.getTransport(protocol);
  }

  protected void doSend(MimeMessage[] mimeMessages) {
    Map<Object, Exception> failedMessages = new LinkedHashMap<Object, Exception>();
    Transport transport;
    try {
      transport = getTransport(getSession());
      transport.connect(getHost(), getPort(), getUsername(), getPassword());
    } catch (AuthenticationFailedException ex) {
      throw new NotificationException(ex);
    } catch (MessagingException ex) {
      // Effectively, all messages failed...
      for (int i = 0; i < mimeMessages.length; i++) {
        Object original = mimeMessages[i];
        failedMessages.put(original, ex);
      }
      throw new NotificationException("Mail server connection failed", ex);
    }

    try {
      for (MimeMessage mimeMessage : mimeMessages) {
        try {
          if (mimeMessage.getSentDate() == null) mimeMessage.setSentDate(new Date());
          String messageId = mimeMessage.getMessageID();
          mimeMessage.saveChanges();
          if (messageId != null) {
            // Preserve explicitly specified message id...
            mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);
          }
          transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        } catch (MessagingException ex) {
          failedMessages.put(mimeMessage, ex);
        }
      }
    } finally {
      try {
        transport.close();
      } catch (MessagingException ex) {
        if (!failedMessages.isEmpty()) {
          throw new NotificationSendException("Failed to close server connection after message failures", ex,
              failedMessages);
        } else {
          throw new NotificationException("Failed to close server connection after message sending", ex);
        }
      }
    }

    if (!failedMessages.isEmpty()) { throw new NotificationSendException(failedMessages); }
  }

  private int addRecipient(MimeMessage mimeMsg, MailMessage mailMsg) throws MessagingException {
    int recipients = 0;
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

  public Properties getJavaMailProperties() {
    return javaMailProperties;
  }

  public void setJavaMailProperties(Properties javaMailProperties) {
    this.javaMailProperties = javaMailProperties;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDefaultEncoding() {
    return defaultEncoding;
  }

  public void setDefaultEncoding(String defaultEncoding) {
    this.defaultEncoding = defaultEncoding;
  }

  public void setSession(Session session) {
    this.session = session;
  }

}
