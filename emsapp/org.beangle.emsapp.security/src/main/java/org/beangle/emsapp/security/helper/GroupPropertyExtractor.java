/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.helper;

import org.beangle.commons.text.TextResource;
import org.beangle.model.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.ems.security.Group;

public class GroupPropertyExtractor extends DefaultPropertyExtractor {

	public GroupPropertyExtractor() {
		super();
	}

	public GroupPropertyExtractor(TextResource textResource) {
		super(textResource);
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		Group group = (Group) target;
		if ("users".equals(property)) {
			return getPropertyIn(group.getMembers(), "user.name");
		} else {
			return super.getPropertyValue(target, property);
		}
	}

}
