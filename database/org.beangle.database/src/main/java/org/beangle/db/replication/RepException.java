/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

public class RepException extends RuntimeException {

	public RepException() {
		super();
	}

	public RepException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepException(String message) {
		super(message);
	}

	public RepException(Throwable cause) {
		super(cause);
	}

}
