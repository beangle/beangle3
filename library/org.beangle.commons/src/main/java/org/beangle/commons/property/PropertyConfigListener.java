/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.property;

import java.util.EventListener;

public interface PropertyConfigListener extends EventListener {

	/**
	 * Handle an config event.
	 * 
	 * @param event
	 *            the event to respond to
	 */
	void onConfigEvent(PropertyConfigEvent event);
}
