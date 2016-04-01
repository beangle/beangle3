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
package org.beangle.struts2.view.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.beangle.struts2.view.component.Component;

import freemarker.template.TemplateModelException;
import freemarker.template.TransformControl;

/**
 * ResetCallbackWriter
 * 
 * @author chaostone
 * @since 2.4
 */
public class ResetCallbackWriter extends Writer implements TransformControl {
  private Component bean;
  private Writer writer;
  private StringWriter body;
  private boolean afterBody = false;

  public ResetCallbackWriter(Component bean, Writer writer) {
    this.bean = bean;
    this.writer = writer;
    if (bean.usesBody()) this.body = new StringWriter();
  }

  public void close() throws IOException {
    if (bean.usesBody()) body.close();
  }

  /**
   * let's just not do it (it will be flushed eventually anyway)
   */
  public void flush() throws IOException {
    // writer.flush();
  }

  public void write(char cbuf[], int off, int len) throws IOException {
    if (bean.usesBody() && !afterBody) body.write(cbuf, off, len);
    else writer.write(cbuf, off, len);
  }

  public int onStart() throws TemplateModelException, IOException {
    return bean.start(this) ? EVALUATE_BODY : SKIP_BODY;
  }

  public int afterBody() throws TemplateModelException, IOException {
    afterBody = true;
    boolean repeat = bean.end(this, bean.usesBody() ? body.toString() : "");
    if (repeat) {
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
