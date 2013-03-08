package org.beangle.commons.inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

public final class Containers {

  static Container root;

  static final List<ContainerHook> hooks = new ArrayList<ContainerHook>();

  static Map<Long, Container> subContainers = CollectUtils.newHashMap();

  public static Container getRoot() {
    return root;
  }

  public static void setRoot(Container root) {
    Containers.root = root;
  }

  public static List<ContainerHook> getHooks() {
    return hooks;
  }

  public static void register(Long id, Container container) {
    subContainers.put(id, container);
  }

  public static void remove(Long id) {
    subContainers.remove(id);
  }
  public static Container get(Long id) {
    return subContainers.get(id);
  }

}
