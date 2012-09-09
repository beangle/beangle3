/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.mail;

/**
 * @author chaostone
 */
public interface MailSender {

  void send(MailMessage... message);
}
