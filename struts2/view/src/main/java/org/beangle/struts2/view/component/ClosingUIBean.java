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

import org.beangle.struts2.view.template.Theme;
import org.beangle.struts2.view.template.ThemeStack;

import com.opensymphony.xwork2.util.ValueStack;

public class ClosingUIBean extends UIBean {

  protected String body;

  private boolean useNewTheme = false;

  public ClosingUIBean(ValueStack stack) {
    super(stack);
  }

  @Override
  public boolean start(Writer writer) {
    evaluateParams();
    return true;
  }

  @Override
  public final boolean end(Writer writer, String body) {
    boolean again = doEnd(writer, body);
    if (useNewTheme) popTheme();
    if (!again) popComponentStack();
    return again;
  }

  public boolean doEnd(Writer writer, String body) {
    this.body = body;
    try {
      mergeTemplate(writer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  public String getBody() {
    return body;
  }

  @Override
  final public boolean usesBody() {
    return true;
  }

  public final void setTheme(String newTheme) {
    this.theme = Theme.getTheme(newTheme);
    pushTheme(theme);
    useNewTheme = true;
  }

  private void pushTheme(Theme theme) {
    ThemeStack themestack = (ThemeStack) stack.getContext().get(Theme.ThemeStack);
    if (null == themestack) {
      themestack = new ThemeStack();
      stack.getContext().put(Theme.ThemeStack, themestack);
    }
    themestack.push(theme);
  }

  private void popTheme() {
    ThemeStack themestack = (ThemeStack) stack.getContext().get(Theme.ThemeStack);
    themestack.pop();
    if (themestack.isEmpty()) stack.getContext().remove(Theme.ThemeStack);
  }
}
