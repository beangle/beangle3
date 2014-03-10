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
package org.beangle.struts2.view.component;

import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

public abstract class IterableUIBean extends ClosingUIBean {

  public IterableUIBean(ValueStack stack) {
    super(stack);
  }

  protected abstract boolean next();

  protected void iterator(Writer writer, String body) {
    this.body = body;
    try {
      mergeTemplate(writer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean start(Writer writer) {
    evaluateParams();
    return next();
  }

  @Override
  public boolean doEnd(Writer writer, String body) {
    iterator(writer, body);
    return next();
  }

}
