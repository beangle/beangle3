/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.io;

import java.io.OutputStream;

import org.beangle.commons.transfer.exporter.Context;

/**
 * <p>
 * Writer interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Writer {

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getFormat();

  /**
   * <p>
   * setContext.
   * </p>
   * 
   * @param context a {@link org.beangle.commons.transfer.exporter.Context} object.
   */
  public void setContext(Context context);

  /**
   * <p>
   * getOutputStream.
   * </p>
   * 
   * @return a {@link java.io.OutputStream} object.
   */
  public OutputStream getOutputStream();

  /**
   * <p>
   * setOutputStream.
   * </p>
   * 
   * @param outputStream a {@link java.io.OutputStream} object.
   */
  public void setOutputStream(OutputStream outputStream);

  /**
   * <p>
   * close.
   * </p>
   */
  public void close();
}
