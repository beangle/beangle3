package org.beangle.commons.web;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.commons.io.ClasspathResourceLoader;
import org.beangle.commons.web.io.SplitStreamDownloader;
import org.beangle.commons.web.resource.ResourceProcessor;
import org.beangle.commons.web.resource.filter.ContentTypeFilter;
import org.beangle.commons.web.resource.filter.HeaderFilter;
import org.beangle.commons.web.resource.filter.YUICompressFilter;
import org.beangle.commons.web.resource.impl.PathResolverImpl;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("streamDownloader", SplitStreamDownloader.class);
    bind(ClasspathResourceLoader.class).property("prefixes", "static template");
    bind(PathResolverImpl.class);
    bind(ResourceProcessor.class).property("filters",
        list(HeaderFilter.class, ContentTypeFilter.class, YUICompressFilter.class));
  }

}
