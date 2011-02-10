/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

/**
 * 账户锁定异常
 */
public class LockedException extends AccountStatusException {
	private static final long serialVersionUID = 1L;

	public LockedException() {
		super("security.principalLocked");
	}

	public LockedException(String msg) {
		super(msg);
	}

	public LockedException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
