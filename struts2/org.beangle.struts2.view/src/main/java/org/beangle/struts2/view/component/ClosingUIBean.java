/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
    boolean evaluatedAgain = doEnd(writer, body);
    if (useNewTheme) popTheme();
    if (!evaluatedAgain) popComponentStack();
    return evaluatedAgain;
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
    this.theme = new Theme(newTheme);
    pushTheme(theme);
    useNewTheme = true;
  }

  private void pushTheme(Theme theme) {
    ThemeStack themestack = (ThemeStack) stack.getContext().get(Theme.THEME_STACK);
    if (null == themestack) {
      themestack = new ThemeStack();
      stack.getContext().put(Theme.THEME_STACK, themestack);
    }
    themestack.push(theme);
  }

  private void popTheme() {
    ThemeStack themestack = (ThemeStack) stack.getContext().get(Theme.THEME_STACK);
    themestack.pop();
    if (themestack.isEmpty()) stack.getContext().remove(Theme.THEME_STACK);
  }
}
