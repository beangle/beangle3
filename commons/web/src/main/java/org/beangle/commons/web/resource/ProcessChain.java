package org.beangle.commons.web.resource;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessChain {

  private final Iterator<ResourceFilter> filters;

  public ProcessChain(Iterator<ResourceFilter> filters) {
    super();
    this.filters = filters;
  }

  public void process(ProcessContext context, HttpServletRequest request, HttpServletResponse response) {
    if (filters.hasNext()) {
      filters.next().filter(context, request, response, this);
    }
  }

}
