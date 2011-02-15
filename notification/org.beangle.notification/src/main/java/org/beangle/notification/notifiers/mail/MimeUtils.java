/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import java.util.Collections;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.beangle.commons.collection.CollectUtils;
import org.codehaus.plexus.util.StringUtils;

public class MimeUtils {

	public static final List<InternetAddress> parseAddress(String address, String encoding) {
		if (StringUtils.isEmpty(address)) return Collections.emptyList();
		try {
			InternetAddress[] parsed = InternetAddress.parse(address);
			List<InternetAddress> returned = CollectUtils.newArrayList();
			for (InternetAddress raw : parsed) {
				returned.add((encoding != null ? new InternetAddress(raw.getAddress(), raw.getPersonal(),
						encoding) : raw));
			}
			return returned;
		} catch (Exception ex) {
			throw new RuntimeException("Failed to parse embedded personal name to correct encoding", ex);
		}
	}
}
