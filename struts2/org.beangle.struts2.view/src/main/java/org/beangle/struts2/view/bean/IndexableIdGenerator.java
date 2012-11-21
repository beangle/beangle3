/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.struts2.view.bean;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

/**
 * 基于每种ui一个序列的id产生器
 * 
 * @author chaostone
 * @since 3.0
 */
public class IndexableIdGenerator implements UIIdGenerator {

  private final String seed;

  private Map<Class<?>, UIIndex> uiIndexes = CollectUtils.newHashMap();

  public IndexableIdGenerator(int seed) {
    this.seed = String.valueOf(seed);
  }

  public String generate(Class<?> clazz) {
    UIIndex index = uiIndexes.get(clazz);
    if (null == index) {
      index = new UIIndex(Strings.uncapitalize(clazz.getSimpleName()));
      uiIndexes.put(clazz, index);
    }
    return index.genId(seed);
  }

  private static class UIIndex {
    int index = 0;
    final String name;

    UIIndex(String name) {
      super();
      this.name = name;
    }

    public String genId(String seed) {
      index++;
      return Strings.concat(name, seed, String.valueOf(index));
    }
  }
}
