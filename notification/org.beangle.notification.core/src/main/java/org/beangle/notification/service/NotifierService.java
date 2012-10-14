/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.service;

import org.beangle.notification.Notifier;

/**
 * @author chaostone
 */
public interface NotifierService {
  /**
   * Returns notifier by id
   * 
   * @param notifierId
   */
  Notifier<?> getNotifier(String notifierId);
}
