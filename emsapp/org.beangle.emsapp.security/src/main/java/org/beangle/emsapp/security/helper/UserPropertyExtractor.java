/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.helper;

import java.util.Collection;

import org.beangle.commons.text.TextResource;
import org.beangle.model.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.ems.security.User;

public class UserPropertyExtractor extends DefaultPropertyExtractor {

	public UserPropertyExtractor() {
		super();
	}

	public UserPropertyExtractor(TextResource textResource) {
		super(textResource);
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		User user = (User) target;
		if ("enabled".equals(property)) {
			boolean enabled = user.isEnabled();
			if (enabled) return getText("action.activate");
			else return getText("action.freeze");
		}
		if ("groups".equals(property)) {
			return getPropertyIn(user.getGroups(), "group.name");
		} else {
			return super.getPropertyValue(target, property);
		}
	}

	public static final StringBuilder getUserNames(Collection<User> users) {
		StringBuilder sb = new StringBuilder();
		for (final User user : users) {
			sb.append(user.getFullname());
			sb.append('(').append(user.getName()).append(')').append(' ');
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}
}
