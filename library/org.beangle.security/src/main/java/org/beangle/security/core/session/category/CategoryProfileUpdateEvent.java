/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import org.beangle.context.event.Event;

/**
 * 会话配置的调整事件
 * @author chaostone
 * @version $Id: CategoryProfileModifyEvent.java Jul 14, 2011 7:52:50 AM chaostone $
 */
public class CategoryProfileUpdateEvent extends Event {

	private static final long serialVersionUID = 803263309728051161L;

	public CategoryProfileUpdateEvent(CategoryProfile source) {
		super(source);
	}

}
