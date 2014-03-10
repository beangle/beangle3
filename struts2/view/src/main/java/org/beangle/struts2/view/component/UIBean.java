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

import static org.beangle.struts2.view.bean.ConstantBeanNames.GeneratorName;
import static org.beangle.struts2.view.bean.ConstantBeanNames.TextResourceName;
import static org.beangle.struts2.view.bean.ConstantBeanNames.UrlRenderName;

import java.io.Writer;
import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.lang.Chars;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.struts2.view.bean.ActionUriRender;
import org.beangle.struts2.view.bean.UIIdGenerator;
import org.beangle.struts2.view.template.TemplateEngine;
import org.beangle.struts2.view.template.Theme;
import org.beangle.struts2.view.template.ThemeStack;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @since 2.0
 */
public abstract class UIBean extends Component {
  protected String id;

  protected Theme theme;

  private static final String NumberFormat = "{0,number,#.##}";

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
      ThemeStack themestack = (ThemeStack) stack.getContext().get(Theme.ThemeStack);
      if (null != themestack) theme = themestack.peek();
      if (null == theme) theme = (Theme) stack.getContext().get(Theme.Theme);
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
    if (-1 == text.indexOf('.') || -1 < text.indexOf(' ')) return defaultText;
    else {
      return ((TextResource) stack.getContext().get(TextResourceName)).getText(text, defaultText);
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

  protected Object getValue(Object obj, String property) {
    stack.push(obj);
    try {
      Object value = stack.findValue(property);
      if (value instanceof Number) { return MessageFormat.format(NumberFormat, value); }
      return value;
    } finally {
      stack.pop();
    }
  }

  protected Container getContainer() {
    return (Container) stack.getContext().get(ActionContext.CONTAINER);
  }

  protected String render(String uri) {
    return ((ActionUriRender) stack.getContext().get(UrlRenderName)).render(getRequestURI(), uri);
  }

  protected void generateIdIfEmpty() {
    if (Strings.isEmpty(id)) {
      id = ((UIIdGenerator) stack.getContext().get(GeneratorName)).generate(getClass());
    }
  }

  /**
   * Process label,convert empty to null
   * @param label
   * @param name
   * @return
   */
  protected String processLabel(String label, String name) {
    if (null != label) {
      if (Strings.isEmpty(label)) return null;
      else return getText(label);
    } else return getText(name);
  }
}
