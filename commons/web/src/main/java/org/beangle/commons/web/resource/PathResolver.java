package org.beangle.commons.web.resource;

import java.util.List;

public interface PathResolver {

  public List<String> resolve(String name);
}
