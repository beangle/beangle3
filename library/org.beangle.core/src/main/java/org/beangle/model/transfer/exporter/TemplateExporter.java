/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import java.util.Locale;

import org.beangle.model.transfer.Transfer;
import org.beangle.model.transfer.TransferListener;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.io.Writer;

public class TemplateExporter implements Exporter {
	/**
	 * 数据读取对象
	 */
	protected TemplateWriter writer;

	/**
	 * 设置数据访问上下文
	 */
	public void setContext(Context context) {
		writer.setContext(context);
	}

	/**
	 * not supported now
	 */
	public Transfer addListener(TransferListener listener) {
		return this;
	}

	public Object getCurrent() {
		return null;
	}

	public String getDataName() {
		return null;
	}

	public int getFail() {
		return 0;
	}

	public String getFormat() {
		return writer.getFormat();
	}

	public Locale getLocale() {
		return null;
	}

	public int getSuccess() {
		return 0;
	}

	public int getTranferIndex() {
		return 0;
	}

	public void transfer(TransferResult tr) {
		writer.write();
		writer.close();
	}

	public void transferItem() {

	}

	public void setWriter(Writer writer) {
		if (writer instanceof TemplateWriter) {
			this.writer = (TemplateWriter) writer;
		}
	}

	public TemplateWriter getWriter() {
		return writer;
	}

}
