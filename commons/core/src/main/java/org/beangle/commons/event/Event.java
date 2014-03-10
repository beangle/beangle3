/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.event;

import java.util.EventObject;

/**
 * <p>
 * Abstract Event class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class Event extends EventObject {

  private static final long serialVersionUID = 6311495014194589511L;

  /** System time when the event happened */
  private final long timestamp;

  /** event subject */
  private String subject;

  /** event details */
  private String detail;

  /** resource where the event happened */
  private String resource;

  /**
   * Create a new Event.
   * 
   * @param source the component that published the event (never <code>null</code>)
   */
  public Event(Object source) {
    super(source);
    this.timestamp = System.currentTimeMillis();
  }

  /**
   * Return the system time in milliseconds when the event happened.
   * 
   * @return a long.
   */
  public final long getTimestamp() {
    return this.timestamp;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

}
