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
package org.beangle.security.cas.validation;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chaostone
 * @version $Id: AssertionBean.java Dec 27, 2011 5:57:31 PM chaostone $
 */
public class AssertionBean implements Assertion, Serializable {

  private static final long serialVersionUID = -8053977187578078215L;

  private final String principal;
  private final String ticket;
  private final Date validAt;
  private final Map<String, Object> attributes;

  public AssertionBean(String principal, String ticket, Date validAt, Map<String, Object> attributes) {
    super();
    this.principal = principal;
    this.ticket = ticket;
    this.validAt = validAt;
    this.attributes = attributes;
  }

  public AssertionBean(String principal, String ticket) {
    this(principal, ticket, new Date(), new HashMap<String, Object>());
  }

  public AssertionBean(String principal, String ticket, Map<String, Object> attributes) {
    this(principal, ticket, new Date(), attributes);
  }

  public String getPrincipal() {
    return principal;
  }

  public String getTicket() {
    return ticket;
  }

  public Date getValidAt() {
    return validAt;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

}
