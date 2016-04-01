/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.transfer.excel;

import java.io.OutputStream;
import java.net.URL;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.exporter.TemplateWriter;
import org.beangle.commons.transfer.io.TransferFormat;

/**
 * <p>
 * ExcelTemplateWriter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ExcelTemplateWriter implements TemplateWriter {

  protected URL template;

  protected XLSTransformer transformer = new XLSTransformer();

  protected Context context;

  protected OutputStream outputStream;

  protected Workbook workbook;

  /**
   * <p>
   * Constructor for ExcelTemplateWriter.
   * </p>
   */
  public ExcelTemplateWriter() {
    super();
  }

  /**
   * <p>
   * Constructor for ExcelTemplateWriter.
   * </p>
   * 
   * @param outputStream a {@link java.io.OutputStream} object.
   */
  public ExcelTemplateWriter(OutputStream outputStream) {
    super();
    this.outputStream = outputStream;
  }

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public TransferFormat getFormat() {
    return TransferFormat.Xls;
  }

  /**
   * <p>
   * Getter for the field <code>template</code>.
   * </p>
   * 
   * @return a {@link java.net.URL} object.
   */
  public URL getTemplate() {
    return template;
  }

  /** {@inheritDoc} */
  public void setTemplate(URL template) {
    this.template = template;
  }

  /** {@inheritDoc} */
  public void setContext(Context context) {
    this.context = context;
  }

  /**
   * <p>
   * write.
   * </p>
   */
  public void write() {
    try {
      workbook = transformer.transformXLS(template.openStream(), context.getDatas());
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * <p>
   * close.
   * </p>
   */
  public void close() {
    try {
      workbook.write(outputStream);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

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
  public void setOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

}
