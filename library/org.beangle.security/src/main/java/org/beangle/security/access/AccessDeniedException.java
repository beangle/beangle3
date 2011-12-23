/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.access;

import org.beangle.security.BeangleSecurityException;

/**
 * 授权异常
 * 
 * @author chaostone
 */
public class AccessDeniedException extends BeangleSecurityException {
	private static final long serialVersionUID = -2403784040888146039L;

	private Object resource;

	public AccessDeniedException(Object resource, String message) {
		super(message);
		this.resource = resource;
	}

	public Object getResource() {
		return resource;
	}

}
