/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

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
