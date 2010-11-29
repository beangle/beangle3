/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;


/**
 * Over max session limit Exception
 * @author chaostone
 *
 */
public class OvermaxSessionException extends SessionException {
	private static final long serialVersionUID = -2827989849698493720L;

	int maxUserLimit;

	public OvermaxSessionException() {
		super();
	}

	public OvermaxSessionException(int maxUserLimit) {
		super(String.valueOf(maxUserLimit));
		this.maxUserLimit = maxUserLimit;
	}

	public int getMaxUserLimit() {
		return maxUserLimit;
	}

}
