/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

import java.util.List;

public interface BindRegistry {

	public List<String> getBeanNames(Class<?> type);
	
	public Class<?> getBeanType(String beanName);

	public void register(Class<?> type,String name);

	public boolean contains(String beanName);

}
