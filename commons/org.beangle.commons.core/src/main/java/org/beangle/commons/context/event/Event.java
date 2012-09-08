/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.event;

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

  /**event subject*/
  private String subject;

  /**event details*/
  private String detail;
  
  /**location where the event happened*/
  private String location;
  
  /**
   * Create a new ApplicationEvent.
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
