package org.beangle.commons.web.spring;

import org.springframework.context.ApplicationContext;

public interface InitializingContextAware {

  void init(ApplicationContext context);
}
