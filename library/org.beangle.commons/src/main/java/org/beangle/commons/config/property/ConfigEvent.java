/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

import java.util.EventObject;

public class ConfigEvent extends EventObject {

	public ConfigEvent(Config config) {
		super(config);
	}

	@Override
	public Config getSource() {
		return (Config) super.getSource();
	}

}
