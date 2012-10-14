/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.service;

import org.beangle.notification.Message;

public interface SendingObserver {

  void onStart(Message msg);

  void onFinish(Message msg);
}
