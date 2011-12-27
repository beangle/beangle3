/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
