/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.io;

import java.io.OutputStream;

import org.beangle.commons.transfer.exporter.Context;

/**
 * <p>
 * Abstract AbstractItemWriter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractItemWriter implements ItemWriter {
  protected OutputStream outputStream;

  protected Context context;

  /** {@inheritDoc} */
  public void setOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  /**
   * <p>
   * Getter for the field <code>outputStream</code>.
   * </p>
   * 
   * @return a {@link java.io.OutputStream} object.
   */
  public OutputStream getOutputStream() {
    return outputStream;
  }

  /** {@inheritDoc} */
  public void setContext(Context context) {
    this.context = context;
  }
}
