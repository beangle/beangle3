/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.inject;

import java.util.List;
import java.util.Set;

public interface BindRegistry {

	List<String> getBeanNames(Class<?> type);

	Class<?> getBeanType(String beanName);

	void register(Class<?> type, String name, Object... args);

	boolean contains(String beanName);

	Set<String> getBeanNames();

}
