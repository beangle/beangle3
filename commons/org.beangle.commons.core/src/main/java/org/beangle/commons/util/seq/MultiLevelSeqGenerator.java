/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
