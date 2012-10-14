/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.spring;

import org.springframework.context.ApplicationContext;

public interface InitializingContextAware {

  void init(ApplicationContext context);
}
