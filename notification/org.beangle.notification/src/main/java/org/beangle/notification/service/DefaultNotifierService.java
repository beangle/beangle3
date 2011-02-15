/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.service;

import java.util.Map;

import org.beangle.notification.Notifier;

public class DefaultNotifierService implements NotifierService {

	private Map<String, Notifier<?>> notifiers;

	public Notifier<?> getNotifier(String notifierId) {
		return notifiers.get(notifierId);
	}

	public Map<String, Notifier<?>> getNotifiers() {
		return notifiers;
	}

	public void setNotifiers(Map<String, Notifier<?>> notifiers) {
		this.notifiers = notifiers;
	}
}
