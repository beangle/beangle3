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
package org.beangle.struts2.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.HostConfig;
import org.apache.struts2.dispatcher.StaticContentLoader;
import org.beangle.commons.inject.Containers;
import org.beangle.commons.web.resource.ResourceProcessor;

/**
 * BeangleStaticContentLoader provide serval features
 * <ul>
 * <li>1 Avoid load resource when get not expired content using ETag</li>
 * <li>2 Multi resource in one request</li>
 * <li>3 Realy detect resource modify datetime.</li>
 * </ul>
 * <p>
 * This loader can find content of new version using url's lastmodifed .So different resource path
 * (rename beagle-3.0.js to beangle-3.1.js,or using beangle-3.0.js?version=m1 etc.) was not
 * necessary;
 * </p>
 *
 * @author chaostone
 * @version $Id: BeangleStaticContentLoader.java Dec 25, 2011 9:19:06 PM chaostone $
 */
public class BeangleStaticContentLoader implements StaticContentLoader {

  private ResourceProcessor processor;

  /**
   * Locate a static resource and copy directly to the response, setting the
   * appropriate caching headers.
   */
  public void findStaticResource(String path, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    processor.process(cleanupPath(path), request, response);
  }

  public boolean canHandle(String resourcePath) {
    return resourcePath.startsWith("/static");
  }

  /**
   * @param path requested path
   * @return path without leading "/static"
   */
  protected String cleanupPath(String path) {
    return path.substring("/static".length());
  }

  /**
   * Catch struts2 init parameters
   */
  public void setHostConfig(HostConfig filterConfig) {
    processor = Containers.getRoot().getBean(ResourceProcessor.class).get();
  }

}
