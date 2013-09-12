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
    public final URL url;
    public byte[] data;

    public Resource(String path, URL url) {
      super();
      this.path = path;
      this.url = url;
    }

  }

}
