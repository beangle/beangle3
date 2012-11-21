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
package org.beangle.commons.util.seq;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * MultiLevelSeqGenerator class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class MultiLevelSeqGenerator {

  private final Map<Integer, SeqPattern> patterns = CollectUtils.newHashMap();

  /**
   * <p>
   * getSytle.
   * </p>
   * 
   * @param level a int.
   * @return a {@link org.beangle.commons.util.seq.SeqPattern} object.
   */
  public SeqPattern getSytle(int level) {
    return patterns.get(level);
  }

  /**
   * <p>
   * next.
   * </p>
   * 
   * @param level a int.
   * @return a {@link java.lang.String} object.
   */
  public String next(int level) {
    return getSytle(level).next();
  }

  /**
   * <p>
   * add.
   * </p>
   * 
   * @param style a {@link org.beangle.commons.util.seq.SeqPattern} object.
   */
  public void add(SeqPattern style) {
    style.setGenerator(this);
    patterns.put(style.getLevel(), style);
  }

  /**
   * <p>
   * reset.
   * </p>
   * 
   * @param level a int.
   */
  public void reset(int level) {
    patterns.get(level).reset();
  }
}
