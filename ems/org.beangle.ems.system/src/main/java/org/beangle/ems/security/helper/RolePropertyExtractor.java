/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.helper;

import org.beangle.ems.security.Role;
import org.beangle.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.util.i18n.TextResource;

public class RolePropertyExtractor extends DefaultPropertyExtractor {

	public RolePropertyExtractor() {
		super();
	}

	public RolePropertyExtractor(TextResource textResource) {
		super(textResource);
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		Role role = (Role) target;
		if ("users".equals(property)) {
			return getPropertyIn(role.getMembers(), "user.name");
		} else {
			return super.getPropertyValue(target, property);
		}
	}

}
