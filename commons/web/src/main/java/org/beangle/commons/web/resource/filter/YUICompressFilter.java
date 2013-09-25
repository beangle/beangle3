/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.web.resource.filter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.io.ResourceLoader;
import org.beangle.commons.lang.Charsets;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.resource.ProcessChain;
import org.beangle.commons.web.resource.ProcessContext;
import org.beangle.commons.web.resource.ResourceFilter;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class YUICompressFilter implements ResourceFilter {

  private int lineBreak = 4000;

  private ResourceLoader loader = null;

  private boolean shouldFilter(HttpServletRequest request, HttpServletResponse response) {
    return (response.getContentType().equals("text/javascript") || response.getContentType().equals(
        "text/css"))
        && !Objects.equals("no", request.getParameter("compress"));
  }

  @Override
  public void filter(ProcessContext context, HttpServletRequest request, HttpServletResponse response,
      ProcessChain chain) throws IOException {
    if (!shouldFilter(request, response)) {
      chain.process(context, request, response);
      return;
    }

    if (response.getContentType().equals("text/javascript")) {
      for (ProcessContext.Resource res : context.resources) {
        if (!res.path.endsWith("min.js")) {
          Option<URL> minurl = loader.load(Strings.replace(res.path, ".js", ".min.js"));
          if (!minurl.isEmpty()) res.url = minurl.get();
        }
      }
    }

    chain.process(context, request, response);

    if (response.getContentType().equals("text/javascript")) {
      for (ProcessContext.Resource res : context.resources) {
        if (null != res.data && null != res.url && !res.url.toString().endsWith("min.js")) {
          StringReader sr = new StringReader(new String(res.data, Charsets.UTF_8));
          JavaScriptCompressor compressor = new JavaScriptCompressor(sr, null);
          StringWriter writer = new StringWriter(res.data.length);
          compressor.compress(writer, lineBreak, false, false, true, true);
          res.data = writer.getBuffer().toString().getBytes();
        }
      }
    } else if (response.getContentType().equals("text/css")) {
      for (ProcessContext.Resource res : context.resources) {
        if (null != res.data) {
          StringReader sr = new StringReader(new String(res.data, Charsets.UTF_8));
          CssCompressor compressor = new CssCompressor(sr);
          StringWriter writer = new StringWriter(res.data.length);
          compressor.compress(writer, lineBreak);
          res.data = writer.getBuffer().toString().getBytes();
        }
      }
    }

  }

  public void setLineBreak(int lineBreak) {
    this.lineBreak = lineBreak;
  }

  public void setLoader(ResourceLoader loader) {
    this.loader = loader;
  }

}
