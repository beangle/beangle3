/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.util.List;

/**
 * 分类配置提供者
 * 
 * @author chaostone
 * @version $Id: CategoryProfileProvider.java Jul 11, 2011 10:14:02 AM chaostone $
 */
public interface CategoryProfileProvider {

	public List<CategoryProfile> getCategoryProfiles();

	public String getServerName();
}
