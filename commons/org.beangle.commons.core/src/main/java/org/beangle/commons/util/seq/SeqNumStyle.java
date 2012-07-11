/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.seq;

/**
 * <p>
 * SeqNumStyle interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface SeqNumStyle {
  /** 中文数字 */
  public static final SeqNumStyle HANZI = new HanZiSeqStyle();

  /** 数字 */
  public static final SeqNumStyle ARABIC = new ArabicSeqStyle();

  /**
   * <p>
   * build.
   * </p>
   * 
   * @param seq a int.
   * @return a {@link java.lang.String} object.
   */
  public String build(int seq);
}
