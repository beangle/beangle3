/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;
import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.lang.Chars;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.util.TextResourceHelper;
import org.beangle.struts2.view.UIIdGenerator;
import org.beangle.struts2.view.template.TemplateEngine;
import org.beangle.struts2.view.template.Theme;
import org.beangle.struts2.view.template.ThemeStack;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 */
public abstract class UIBean extends Component {
  protected String id;

  protected Theme theme;

  public UIBean(ValueStack stack) {
    super(stack);
  }

  protected void evaluateParams() {
  }

  @Override
  public boolean end(Writer writer, String body) {
    evaluateParams();
    try {
      mergeTemplate(writer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    popComponentStack();
    return false;
  }

  protected void mergeTemplate(Writer writer) throws Exception {
    TemplateEngine engine = getContainer().getInstance(TemplateEngine.class);
    engine.render(getTheme().getTemplatePath(getClass(), engine.getSuffix()), stack, writer, this);
  }

  /**
   * 将所有额外参数链接起来
   * 
   * @return 空格开始 空格相隔的参数字符串
   */
  public String getParameterString() {
    StringBuilder sb = new StringBuilder(parameters.size() * 10);
    for (Map.Entry<String, Object> entry : parameters.entrySet()) {
      String key = entry.getKey();
      if ("cssClass".equals(key)) key = "class";
      sb.append(" ").append(key).append("=\"").append(entry.getValue().toString()).append("\"");
    }
    return sb.toString();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  protected Theme getTheme() {
    if (null == theme) {
      ThemeStack themestack = (ThemeStack) stack.getContext().get(Theme.THEME_STACK);
      if (null != themestack) theme = themestack.peek();
      if (null == theme) theme = (Theme) stack.getContext().get(Theme.THEME);
      return theme;
    } else {
      return theme;
    }
  }

  public void setTheme(String theme) {
    this.theme = Theme.getTheme(theme);
  }

  /**
   * 获得对应的国际化信息
   * 
   * @param text
   * @return 当第一个字符不是字母或者不包含.或者包含空格的均返回原有字符串
   */
  protected String getText(String text) {
    return getText(text, text);
  }

  protected String getText(String text, String defaultText) {
    if (Strings.isEmpty(text)) return defaultText;
    if (!Chars.isAsciiAlpha(text.charAt(0))) return defaultText;
    if (-1 == text.indexOf('.') || -1 < text.indexOf(' ')) {
      return defaultText;
    } else {
      // long start = System.currentTimeMillis();
      String msg = TextResourceHelper.getText(text, defaultText, stack);
      // System.out.println("I18n:" + text + "->" + msg + " use :" + (System.currentTimeMillis() -
      // start));
      return msg;

    }
  }

  protected HttpServletRequest getRequest() {
    return (HttpServletRequest) stack.getContext().get(ServletActionContext.HTTP_REQUEST);
  }

  protected String getRequestURI() {
    return getRequest().getRequestURI();
  }

  protected String getRequestParameter(String name) {
    return getRequest().getParameter(name);
  }

  private static final String Number_Fmt = "{0,number,#.##}";

  protected Object getValue(Object obj, String property) {
    stack.push(obj);
    try {
      Object value = stack.findValue(property);
      if (value instanceof Number) { return MessageFormat.format(Number_Fmt, value); }
      return value;
    } finally {
      stack.pop();
    }
  }

  protected Container getContainer() {
    return (Container) stack.getContext().get(ActionContext.CONTAINER);
  }

  protected String render(String uri) {
    return getContainer().getInstance(ActionUrlRender.class).render(getRequestURI(), uri);
  }

  protected void generateIdIfEmpty() {
    if (Strings.isEmpty(id)) {
      id = ((UIIdGenerator) stack.getContext().get(UIIdGenerator.GENERATOR)).generate(getClass());
    }
  }
}
