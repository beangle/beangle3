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
package org.beangle.commons.web.resource;

import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

public class ProcessContext {

  public final String uri;

  public final List<Resource> resources;

  public ProcessContext(String uri, List<String> paths, List<URL> urls) {
    super();
    this.uri = uri;
    this.resources = CollectUtils.newArrayList(paths.size());
    for (int i = 0; i < paths.size(); i++) {
      resources.add(new Resource(paths.get(i), urls.get(i)));
    }
  }

  public static class Resource {
    public final String path;
    public URL url;
    public byte[] data;

    public Resource(String path, URL url) {
      super();
      this.path = path;
      this.url = url;
    }

  }

}
