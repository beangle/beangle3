/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.security.BeangleSecurityException;

/**
 * 认证异常
 * 
 * @author chaostone
 */
public class AuthenticationException extends BeangleSecurityException {

	private static final long serialVersionUID = -3529782031102169004L;
	private Authentication authentication;
	private Object extraInfo;

	public AuthenticationException() {
		super();
	}

	/**
	 * @param message
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AuthenticationException(Throwable cause) {
		super(ExceptionUtils.getFullStackTrace(cause));
	}

	public AuthenticationException(String msg, Object extraInfo) {
		super(msg);
		this.extraInfo = extraInfo;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public Object getExtraInfo() {
		return extraInfo;
	}

	public void clearExtraInfo() {
		this.extraInfo = null;
	}

	@Override
	public String getMessage() {
		String msg = super.getMessage();
		if (null == msg) {
			return StrUtils.concat("security."
					+ StringUtils.substringBefore(getClass().getSimpleName(), "Exception"));
		} else {
			return msg;
		}
	}

}
