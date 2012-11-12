/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.spring;

import org.springframework.context.ApplicationContext;

/**
 * Spring context hook using ContextLoader
 * 
 * @author chaostone
 * @since 3.0
 */
public interface LazyInitializingHook {

  void lazyInit(ApplicationContext context);
}
