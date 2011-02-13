/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.apache.commons.lang.exception.ExceptionUtils;

public class BeangleSecurityException extends RuntimeException {

	private static final long serialVersionUID = 1521217606839712065L;

	public BeangleSecurityException() {
		super();
	}

	/**
	 * @param message
	 */
	public BeangleSecurityException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BeangleSecurityException(Throwable cause) {
		super(ExceptionUtils.getFullStackTrace(cause));
	}
}
