package org.beangle.commons.web.resource.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.resource.PathResolver;

public class PathResolverImpl implements PathResolver {
  
  public List<String> resolve(String path) {
    String lastPostfix = "." + Strings.substringAfterLast(path, ".");
    String[] names = Strings.split(path, ",");
    List<String> rs = CollectUtils.newArrayList(names.length);
    String pathDir = null;
    for (String name : names) {
      if (name.startsWith("/")) pathDir = Strings.substringBeforeLast(name, "/");
      else name = pathDir + "/" + name;
      if (!name.endsWith(lastPostfix)) name += lastPostfix;
      rs.add(name);
    }
    return rs;
  }
}
