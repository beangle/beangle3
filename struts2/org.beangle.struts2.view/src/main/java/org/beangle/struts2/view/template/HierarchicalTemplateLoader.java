/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.io.IOException;
import java.io.Reader;

import freemarker.cache.TemplateLoader;

/**
 * 搜索带有固定前缀下的资源，排除/org/和/com/
 * 
 * @author chaostone
 */
public class HierarchicalTemplateLoader implements TemplateLoader {

  final AbstractTemplateEngine templateEngine;

  final TemplateLoader loader;

  public HierarchicalTemplateLoader(AbstractTemplateEngine templateEngine, TemplateLoader loader) {
    this.templateEngine = templateEngine;
    this.loader = loader;
  }

  public Object findTemplateSource(String name) throws IOException {
    Object source = loader.findTemplateSource(name);
    if (null == source) {
      do {
        String parent = templateEngine.getParentTemplate(name);
        if (null == parent) break;
        source = loader.findTemplateSource(parent);
      } while (null == source);
    }
    return source;
  }

  public long getLastModified(Object templateSource) {
    return loader.getLastModified(templateSource);
  }

  public Reader getReader(Object templateSource, String encoding) throws IOException {
    return loader.getReader(templateSource, encoding);
  }

  public void closeTemplateSource(Object templateSource) throws IOException {
    loader.closeTemplateSource(templateSource);
  }

}
