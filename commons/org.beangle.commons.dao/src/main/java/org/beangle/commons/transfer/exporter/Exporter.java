/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.exporter;

import org.beangle.commons.transfer.Transfer;
import org.beangle.commons.transfer.io.Writer;

/**
 * 数据导出转换器
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Exporter extends Transfer {

  /**
   * <p>
   * setContext.
   * </p>
   * 
   * @param context a {@link org.beangle.commons.transfer.exporter.Context} object.
   */
  void setContext(Context context);

  /**
   * <p>
   * setWriter.
   * </p>
   * 
   * @param writer a {@link org.beangle.commons.transfer.io.Writer} object.
   */
  void setWriter(Writer writer);

  /**
   * <p>
   * getWriter.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.io.Writer} object.
   */
  Writer getWriter();
}
