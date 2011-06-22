/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import org.beangle.ems.event.BusinessEvent;

/**
 * @author chaostone
 * @version $Id: AccountStatusEvent.java Jun 22, 2011 8:58:14 AM chaostone $
 */
public class AccountStatusEvent extends BusinessEvent {

	public AccountStatusEvent(Object source) {
		super(source);
	}

}
