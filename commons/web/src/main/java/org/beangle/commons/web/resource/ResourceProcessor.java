package org.beangle.commons.web.resource;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.io.ResourceLoader;

public class ResourceProcessor {

  private ResourceLoader loader = null;

  private PathResolver resolver = null;

  private List<ResourceFilter> filters = null;

  public void process(String uri, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    List<String> names = resolver.resolve(uri);
    List<URL> resources = loader.load(names);
    if (resources.size() != names.size()){
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    ProcessContext pc = new ProcessContext(uri, names, resources);
    ProcessChain chain = new ProcessChain(filters.iterator());
    chain.process(pc, request, response);
    if (response.getStatus() == HttpServletResponse.SC_OK) {
      boolean isText = null != response.getContentType() && response.getContentType().startsWith("text/");
      for (ProcessContext.Resource res : pc.resources) {
        response.getOutputStream().write(res.data);
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
