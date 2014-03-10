/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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

import org.beangle.commons.lang.Assert;
import org.beangle.commons.notification.Message;
import org.beangle.commons.notification.NotificationException;
import org.beangle.commons.notification.Notifier;
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
      if (null == msg.getFrom() && null != getFrom()) msg.from(getFrom());
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
