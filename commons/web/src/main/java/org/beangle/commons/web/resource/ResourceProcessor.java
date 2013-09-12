package org.beangle.commons.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.io.ResourceLoader;
import org.beangle.commons.lang.Arrays;

public class ResourceProcessor {

  private ResourceLoader loader = null;

  private PathResolver resolver = null;

  private List<ResourceFilter> filters = null;

  public void process(String uri, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    List<String> names = resolver.resolve(uri);
    List<URL> resources = loader.load(names);
    if (resources.size() != names.size()) response.sendError(HttpServletResponse.SC_NOT_FOUND);

    ProcessContext pc = new ProcessContext(uri, names, resources);
    ProcessChain chain = new ProcessChain(filters.iterator());
    chain.process(pc, request, response);
    if (response.getStatus() == HttpServletResponse.SC_OK) {
      if (pc.datas.isEmpty()) {
        for (URL url : pc.urls) {
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
          pc.datas.add(Arrays.join(rs));
        }
      }
      boolean isText = null != response.getContentType() && response.getContentType().startsWith("text/");
      for (byte[] data : pc.datas) {
        response.getOutputStream().write(data);
        if (isText) response.getOutputStream().write('\n');
      }
    }
  }

  public void setLoader(ResourceLoader loader) {
    this.loader = loader;
  }

  public void setResolver(PathResolver resolver) {
    this.resolver = resolver;
  }

  public void setFilters(List<ResourceFilter> filters) {
    this.filters = filters;
  }

}
