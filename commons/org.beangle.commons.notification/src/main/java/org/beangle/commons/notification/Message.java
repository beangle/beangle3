/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.notification;

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
