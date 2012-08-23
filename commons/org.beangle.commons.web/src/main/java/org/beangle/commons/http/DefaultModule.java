package org.beangle.commons.http;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.spring.SpringResources;
import org.beangle.commons.http.mime.MimeTypeProvider;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(MimeTypeProvider.class).property(
        "resources",
        bean(SpringResources.class).property("globals",
            "classpath*:org/beangle/commons/http/mime/mimetypes.properties").property("locations",
            "classpath*:META-INF/mimetypes.properties"));
  }

}
