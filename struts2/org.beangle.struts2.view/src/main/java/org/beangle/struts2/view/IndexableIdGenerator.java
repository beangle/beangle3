/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view;

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
