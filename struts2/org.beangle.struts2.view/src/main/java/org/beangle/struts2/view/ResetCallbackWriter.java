/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.beangle.struts2.view.component.Component;

import freemarker.template.TemplateModelException;
import freemarker.template.TransformControl;

public class ResetCallbackWriter extends Writer implements TransformControl {
	private Component bean;
	private Writer writer;
	private StringWriter body;
	private boolean afterBody = false;

	public ResetCallbackWriter(Component bean, Writer writer) {
		this.bean = bean;
		this.writer = writer;

		if (bean.usesBody()) {
			this.body = new StringWriter();
		}
	}

	public void close() throws IOException {
		if (bean.usesBody()) {
			body.close();
		}
	}

	/**
	 * let's just not do it (it will be flushed eventually anyway)
	 */
	public void flush() throws IOException {
		// writer.flush();
	}

	public void write(char cbuf[], int off, int len) throws IOException {
		if (bean.usesBody() && !afterBody) {
			body.write(cbuf, off, len);
		} else {
			writer.write(cbuf, off, len);
		}
	}

	public int onStart() throws TemplateModelException, IOException {
		boolean result = bean.start(this);
		if (result) {
			return EVALUATE_BODY;
		} else {
			return SKIP_BODY;
		}
	}

	public int afterBody() throws TemplateModelException, IOException {
		afterBody = true;
		boolean result = bean.end(this, bean.usesBody() ? body.toString() : "");
		if (result) {
			if (bean.usesBody()) {
				afterBody = false;
				body.getBuffer().delete(0, body.getBuffer().length());
			}
			return REPEAT_EVALUATION;
		} else {
			return END_EVALUATION;
		}
	}

	public void onError(Throwable throwable) throws Throwable {
		throw throwable;
	}

	public Component getBean() {
		return bean;
	}

}
