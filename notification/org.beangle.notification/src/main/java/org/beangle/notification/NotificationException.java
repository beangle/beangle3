/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

//$Id:NotificationException.java Mar 22, 2009 11:13:53 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class NotificationException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotificationException(String message) {
		super(message);
	}

	public NotificationException(String message, Throwable cause) {
		super(message, cause);
	}
}
