package org.beangle.web.spring;

import org.springframework.context.ApplicationContext;

public interface LazyInitProcessor {

	void init(ApplicationContext context);
}
