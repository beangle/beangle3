/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

/**
 * 凭证过期异常
 * 
 * @author chaostone
 */
public class CredentialsExpiredException extends AccountStatusException {
	private static final long serialVersionUID = 1L;

	public CredentialsExpiredException(String msg) {
		super(msg);
	}

	public CredentialsExpiredException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
