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
package org.beangle.commons.transfer.excel;

import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.exporter.TemplateWriter;
import org.beangle.commons.transfer.io.TransferFormat;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

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

  protected PoiTransformer transformer;

  protected Context context;

  protected OutputStream outputStream;

  protected Workbook workbook;

  /**
   * Constructor for ExcelTemplateWriter.
   */
  public ExcelTemplateWriter() {
    super();
  }

  /**
   * Constructor for ExcelTemplateWriter.
   *
   * @param outputStream a {@link java.io.OutputStream} object.
   */
  public ExcelTemplateWriter(OutputStream outputStream) {
    super();
    this.outputStream = outputStream;
  }

  /**
   * getFormat.
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

  public void setTemplate(URL template) {
    this.template = template;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  /**
   * write.
   */
  public void write() {
    try {
      org.jxls.common.Context ctx = new org.jxls.common.Context();
      for (Map.Entry<String, Object> entry : context.getDatas().entrySet()) {
        ctx.putVar(entry.getKey(), entry.getValue());
      }
      JxlsHelper.getInstance().processTemplate(template.openStream(), outputStream, ctx);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * close.
   */
  public void flush() {
  }

  /**
   * Getter for the field <code>outputStream</code>.
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
