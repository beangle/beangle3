/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.excel;

import java.io.OutputStream;
import java.net.URL;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.exporter.TemplateWriter;
import org.beangle.commons.transfer.io.TransferFormats;

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
  public String getFormat() {
    return TransferFormats.XLS;
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
