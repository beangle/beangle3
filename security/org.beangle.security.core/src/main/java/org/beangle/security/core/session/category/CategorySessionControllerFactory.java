/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

/**
 * @author chaostone
 * @version $Id: SessionStatusControllerFactory.java Jun 18, 2011 9:23:53 PM chaostone $
 */
public interface CategorySessionControllerFactory {

	public CategorySessionController getInstance(Object category);
}
