/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.seq;

/**
 * <p>
 * ArabicSeqStyle class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ArabicSeqStyle implements SeqNumStyle {
  /** {@inheritDoc} */
  public String build(int seq) {
    return String.valueOf(seq);
  }
}
