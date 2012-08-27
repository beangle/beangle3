/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.net.URL;

import org.beangle.commons.lang.ClassLoaders;

import freemarker.cache.URLTemplateLoader;

/**
 * 搜索带有固定前缀下的资源，排除/org/和/com/
 * 
 * @author chaostone
 */
public class HierarchicalTemplateLoader extends URLTemplateLoader {

  final AbstractTemplateEngine templateEngine;

  public HierarchicalTemplateLoader(AbstractTemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  protected URL getURL(final String name) {
    URL url = ClassLoaders.getResource(name, getClass());
    if (null == url) {
      do {
        String parent = templateEngine.getParentTemplate(name);
        if (null == parent) break;
        url = ClassLoaders.getResource(parent, getClass());
      } while (null == url);
    }
    return url;
  }
}
