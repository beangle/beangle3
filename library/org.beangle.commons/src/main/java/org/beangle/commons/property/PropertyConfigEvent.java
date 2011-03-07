/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.property;

import java.util.EventObject;

public class PropertyConfigEvent extends EventObject {

	private static final long serialVersionUID = 6125300646790912291L;

	public PropertyConfigEvent(PropertyConfig config) {
		super(config);
	}

	@Override
	public PropertyConfig getSource() {
		return (PropertyConfig) super.getSource();
	}

}
