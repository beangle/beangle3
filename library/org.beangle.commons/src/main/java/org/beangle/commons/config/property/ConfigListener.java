/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

import java.util.EventListener;

public interface ConfigListener extends EventListener {

	/**
	 * Handle an config event.
	 * 
	 * @param event
	 *            the event to respond to
	 */
	void onConfigEvent(ConfigEvent event);
}
