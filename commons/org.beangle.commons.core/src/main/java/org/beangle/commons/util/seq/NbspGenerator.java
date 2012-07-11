/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.seq;

/**
 * <p>
 * NbspGenerator class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class NbspGenerator {

  /**
   * <p>
   * generator.
   * </p>
   * 
   * @param repeat a int.
   * @return a {@link java.lang.String} object.
   */
  public String generator(int repeat) {
    String repeater = "&nbsp;";
    StringBuilder returnval = new StringBuilder();
    for (int i = 0; i < repeat; i++) {
      returnval.append(repeater);
    }
    return returnval.toString();
  }
}
