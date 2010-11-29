/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import java.util.List;
import java.util.Properties;

//$Id:Message.java Mar 22, 2009 11:16:31 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public interface Message {

	public static final String TEXT = "text/plain";

	public static final String HTML = "text/html";

	public String getSubject();

	public void setSubject(String subject);

	public String getText();

	public void setText(String text);

	public Properties getProperties();

	public List<String> getRecipients();

	public String getContentType();

	public void setContentType(String contentType);
}
