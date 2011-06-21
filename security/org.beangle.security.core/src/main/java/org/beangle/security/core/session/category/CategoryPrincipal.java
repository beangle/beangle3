/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

/**
 * 分类帐户
 * 
 * @author chaostone
 */
public interface CategoryPrincipal {

	public Object getCategory();

	public void changeCategory(Object newCategory);
}
