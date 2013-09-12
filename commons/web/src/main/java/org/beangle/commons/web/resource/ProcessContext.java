package org.beangle.commons.web.resource;

import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

public class ProcessContext {

  public int status=200;

  public String contentType;

  public final String uri;

  public final List<URL> urls;

  public final List<String> paths;

  public final List<byte[]> datas;

  public ProcessContext(String uri, List<String> paths, List<URL> urls) {
    super();
    this.uri = uri;
    this.paths = paths;
    this.urls = urls;
    this.datas = CollectUtils.newArrayList(paths.size());
  }

}
