/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import java.util.List;
import java.util.Properties;

public interface Message {

	public static final String TEXT = "text/plain; charset=UTF-8";

	public static final String HTML = "text/html; charset=UTF-8";

	public String getSubject();

	public void setSubject(String subject);

	public String getText();

	public void setText(String text);

	public Properties getProperties();

	public List<String> getRecipients();

	public String getContentType();

	public void setContentType(String contentType);
}
