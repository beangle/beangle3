/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.transfer.exporter;

import java.util.Locale;

import org.beangle.commons.transfer.Transfer;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.io.TransferFormat;
import org.beangle.commons.transfer.io.Writer;

/**
 * <p>
 * TemplateExporter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class TemplateExporter implements Exporter {
  /**
   * 数据读取对象
   */
  protected TemplateWriter writer;

  /**
   * {@inheritDoc} 设置数据访问上下文
   */
  public void setContext(Context context) {
    writer.setContext(context);
  }

  /**
   * {@inheritDoc} not supported now
   */
  public Transfer addListener(TransferListener listener) {
    return this;
  }

  /**
   * <p>
   * getCurrent.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object getCurrent() {
    return null;
  }

  /**
   * <p>
   * getDataName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getDataName() {
    return null;
  }

  /**
   * <p>
   * getFail.
   * </p>
   * 
   * @return a int.
   */
  public int getFail() {
    return 0;
  }

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public TransferFormat getFormat() {
    return writer.getFormat();
  }

  /**
   * <p>
   * getLocale.
   * </p>
   * 
   * @return a {@link java.util.Locale} object.
   */
  public Locale getLocale() {
    return null;
  }

  /**
   * <p>
   * getSuccess.
   * </p>
   * 
   * @return a int.
   */
  public int getSuccess() {
    return 0;
  }

  /**
   * <p>
   * getTranferIndex.
   * </p>
   * 
   * @return a int.
   */
  public int getTranferIndex() {
    return 0;
  }

  /** {@inheritDoc} */
  public void transfer(TransferResult tr) {
    writer.write();
    writer.close();
  }

  /**
   * <p>
   * transferItem.
   * </p>
   */
  public void transferItem() {

  }

  /** {@inheritDoc} */
  public void setWriter(Writer writer) {
    if (writer instanceof TemplateWriter) {
      this.writer = (TemplateWriter) writer;
    }
  }

  /**
   * <p>
   * Getter for the field <code>writer</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.exporter.TemplateWriter} object.
   */
  public TemplateWriter getWriter() {
    return writer;
  }

}
