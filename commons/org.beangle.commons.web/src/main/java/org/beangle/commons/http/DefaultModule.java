/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.http;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.spring.SpringResources;
import org.beangle.commons.http.mime.MimeTypeProvider;

/**
 * 
 * @author chaostone
 *
 */
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
