package org.beangle.commons.io;

import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;

public class ClasspathResourceLoader implements ResourceLoader {

  /**
   * Store set of path prefixes to use with static resources.
   * Each prefix not ended with /
   */
  protected String[] pathPrefixes=new String[]{"/"};

  @Override
  public List<URL> loadAll(String resourceName) {
    List<URL> urls = CollectUtils.newArrayList();
    for (String pathPrefix : pathPrefixes)
      urls.addAll(ClassLoaders.getResources(pathPrefix + resourceName, getClass()));
    return urls;
  }

  @Override
  public Option<URL> load(String name) {
    URL url = null;
    for (String pathPrefix : pathPrefixes) {
      url = ClassLoaders.getResource(pathPrefix + name, getClass());
      if (url != null) break;
    }
    if (null == url) return Option.none();
    else return Option.some(url);
  }

  @Override
  public List<URL> load(List<String> names) {
    List<URL> urls = CollectUtils.newArrayList(names.size());
    for (String name : names) {
      Option<URL> url = load(name);
      if (!url.isEmpty()) urls.add(url.get());
    }
    return urls;
  }

  public void setPrefixes(String prefixes) {
    if (Strings.isEmpty(prefixes)) pathPrefixes = new String[] { "/" };
    else this.pathPrefixes = Strings.split(prefixes, " ");
  }
}
