/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.excel;

import java.io.OutputStream;
import java.net.URL;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.beangle.model.transfer.exporter.Context;
import org.beangle.model.transfer.exporter.TemplateWriter;
import org.beangle.model.transfer.io.TransferFormats;

public class ExcelTemplateWriter implements TemplateWriter {

	protected URL template;

	protected XLSTransformer transformer = new XLSTransformer();

	protected Context context;

	protected OutputStream outputStream;

	protected Workbook workbook;

	public ExcelTemplateWriter() {
		super();
	}

	public ExcelTemplateWriter(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	public String getFormat() {
		return TransferFormats.XLS;
	}

	public URL getTemplate() {
		return template;
	}

	public void setTemplate(URL template) {
		this.template = template;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void write() {
		try {
			workbook = transformer.transformXLS(template.openStream(), context.getDatas());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void close() {
		try {
			workbook.write(outputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
