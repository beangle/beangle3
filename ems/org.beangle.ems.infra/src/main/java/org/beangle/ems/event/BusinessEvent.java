/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.event;

import org.springframework.context.ApplicationEvent;

/**
 * 业务事件
 * @author chaostone
 * @version $Id: BusinessEvent.java Jun 22, 2011 8:47:39 AM chaostone $
 */
public class BusinessEvent extends ApplicationEvent {

	private static final long serialVersionUID = -3105001733284410829L;

	public BusinessEvent(Object source) {
		super(source);
	}

}
