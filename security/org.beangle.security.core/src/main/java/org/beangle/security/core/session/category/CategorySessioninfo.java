/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import org.beangle.security.core.session.Sessioninfo;

/**
 * @author chaostone
 * @version $Id: CategorySessioninfo.java Jul 11, 2011 7:13:38 PM chaostone $
 */
public interface CategorySessioninfo extends Sessioninfo {

  String getCategory();
}
