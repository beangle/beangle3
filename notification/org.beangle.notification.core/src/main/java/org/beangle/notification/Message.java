/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import java.util.List;
import java.util.Properties;

public interface Message {

  static final String TEXT = "text/plain; charset=UTF-8";

  static final String HTML = "text/html; charset=UTF-8";

  String getSubject();

  void setSubject(String subject);

  String getText();

  void setText(String text);

  Properties getProperties();

  List<String> getRecipients();

  String getContentType();

  void setContentType(String contentType);
}
