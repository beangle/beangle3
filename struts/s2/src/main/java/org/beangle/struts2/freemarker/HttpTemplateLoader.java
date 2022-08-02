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
package org.beangle.struts2.freemarker;

import freemarker.cache.TemplateLoader;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class HttpTemplateLoader implements TemplateLoader {
  private String pattern;
  private Boolean preload;
  private Set<String> files = new HashSet<>();

  public HttpTemplateLoader(String pattern, Boolean preload) {
    this.pattern = pattern;
    this.preload = preload;
    if (preload) {
      loadList();
    }
  }

  private void loadList() {
    String url = getURL("ls");
    String res = HttpUtils.getResponseText(url);
    files = CollectUtils.newHashSet(Strings.split(res));
  }

  @Override
  public Object findTemplateSource(String name) throws java.io.IOException {
    if (preload) {
      if (files.contains(name)) return new URLTemplateSource(new URL(getURL(name)));
      else return null;
    } else {
      URL url = new URL(getURL(name));
      boolean status = HttpUtils.access(url);
      if (status) return new URLTemplateSource(url);
      else return null;
    }
  }

  @Override
  public long getLastModified(Object templateSource) {
    return ((URLTemplateSource) templateSource).lastModified();
  }

  @Override
  public Reader getReader(Object templateSource, String encoding) throws java.io.IOException {
    return new InputStreamReader(((URLTemplateSource) templateSource).getInputStream(), encoding);
  }

  @Override
  public void closeTemplateSource(Object templateSource) throws java.io.IOException {
    ((URLTemplateSource) templateSource).close();
  }

  /**
   * get url corresponding to name
   *
   * @param name shoudnot starts with /
   * @return
   */
  protected String getURL(String name) {
    return Strings.replace(pattern, "{path}", name);
  }
}