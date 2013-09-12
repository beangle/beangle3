package org.beangle.commons.web.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResourceFilter {

  void filter(ProcessContext context, HttpServletRequest request, HttpServletResponse response,
      ProcessChain chain) throws IOException;
}
