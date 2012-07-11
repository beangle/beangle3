/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import org.beangle.commons.lang.Assert;
import org.beangle.notification.Message;
import org.beangle.notification.NotificationException;
import org.beangle.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMailNotifier<T extends MailMessage> implements Notifier<T> {

  protected static final Logger logger = LoggerFactory.getLogger(AbstractMailNotifier.class);

  protected MailSender mailSender;

  private String from;

  public String getType() {
    return "mail";
  }

  public void deliver(T msg) throws NotificationException {
    beforeSend(msg);
    try {
      mailSender.send(msg);
      afterSend(msg);
    } catch (NotificationException e) {
      logger.error("Cannot send message " + msg.getSubject(), e);
    }
  }

  abstract protected String buildSubject(Message msg);

  abstract protected String buildText(Message msg);

  protected void beforeSend(Message msg) {
  }

  protected void afterSend(Message msg) {
  }

  public MailSender getMailSender() {
    return mailSender;
  }

  public void setMailSender(MailSender MailSender) {
    this.mailSender = MailSender;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    Assert.notEmpty(from);
    this.from = from;
  }

}
