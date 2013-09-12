package org.beangle.commons.web.resource.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.resource.ProcessChain;
import org.beangle.commons.web.resource.ProcessContext;
import org.beangle.commons.web.resource.ResourceFilter;

public class ContentTypeFilter implements ResourceFilter {
  /**
   * Servered content types
   */
  private Map<String, String> contentTypes = CollectUtils.newHashMap();

  public ContentTypeFilter() {
    super();
    contentTypes.put("js", "text/javascript");
    contentTypes.put("css", "text/css");
    contentTypes.put("html", "text/html");
    contentTypes.put("htm", "text/html");
    contentTypes.put("txt", "text/plain");
    contentTypes.put("gif", "image/gif");
    contentTypes.put("jpg", "image/jpeg");
    contentTypes.put("jpeg", "image/jpeg");
    contentTypes.put("png", "image/png");
    contentTypes.put("json", "application/json");
    contentTypes.put("htc", "text/x-component");
  }

  @Override
  public void filter(ProcessContext context, HttpServletRequest request, HttpServletResponse response,
      ProcessChain chain) {
    String contentType = contentTypes.get(Strings.substringAfterLast(context.uri, "."));
    if (contentType != null) response.setContentType(contentType);
    context.contentType = contentType;
    chain.process(context, request, response);
  }

  public void setContentTypes(Map<String, String> contentTypes) {
    this.contentTypes = contentTypes;
  }

}
