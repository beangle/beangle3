/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.notification;

import java.util.Properties;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;

public abstract class AbstractMessage implements Message {

  private String subject;

  private String text;

  private Properties properties = new Properties();

  private String contentType = TEXT;

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    Assert.notEmpty(contentType);
    Assert.isTrue(Strings.contains(contentType, "charset="), "contentType should contain charset");
    this.contentType = contentType;
  }

  public Properties getProperties() {
    return properties;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

}
