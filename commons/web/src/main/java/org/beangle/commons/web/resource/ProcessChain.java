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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Arrays;

public class ProcessChain {

  private final Iterator<ResourceFilter> filters;

  public ProcessChain(Iterator<ResourceFilter> filters) {
    super();
    this.filters = filters;
  }

  public void process(ProcessContext context, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    if (filters.hasNext()) {
      filters.next().filter(context, request, response, this);
    } else {
      int i = 0;
      while (i < context.resources.size()) {
        ProcessContext.Resource res = context.resources.get(i);
        if (null == res.data) {
          URL url = res.url;
          List<byte[]> rs = CollectUtils.newArrayList();
          InputStream is = url.openStream();
          do {
            byte[] buffer = new byte[4096];
            int n = is.read(buffer);
            if (-1 == n) break;
            else {
              if (n < buffer.length) buffer = java.util.Arrays.copyOf(buffer, n);
              rs.add(buffer);
            }
          } while (true);
          is.close();
          res.data = Arrays.join(rs);
        }
        i++;
      }
    }

  }
}
