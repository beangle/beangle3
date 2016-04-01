/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
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
