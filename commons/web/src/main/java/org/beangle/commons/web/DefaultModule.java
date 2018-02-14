/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.web;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.commons.io.ClasspathResourceLoader;
import org.beangle.commons.web.io.SplitStreamDownloader;
import org.beangle.commons.web.resource.ResourceProcessor;
import org.beangle.commons.web.resource.filter.ContentTypeFilter;
import org.beangle.commons.web.resource.filter.HeaderFilter;
import org.beangle.commons.web.resource.impl.PathResolverImpl;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("streamDownloader", SplitStreamDownloader.class);
    bind(ClasspathResourceLoader.class).property("prefixes", "static template");
    bind(PathResolverImpl.class);
    bind(ResourceProcessor.class).property("filters", list(HeaderFilter.class, ContentTypeFilter.class));
  }

}
