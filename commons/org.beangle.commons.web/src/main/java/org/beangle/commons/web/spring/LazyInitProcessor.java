package org.beangle.commons.web.spring;

import org.springframework.context.ApplicationContext;

public interface LazyInitProcessor {

  void init(ApplicationContext context);
}
