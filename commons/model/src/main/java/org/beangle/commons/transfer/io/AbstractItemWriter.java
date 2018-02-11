/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
