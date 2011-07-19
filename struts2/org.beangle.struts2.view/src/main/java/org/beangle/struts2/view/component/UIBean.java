/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TextProviderHelper;
import org.beangle.struts2.view.template.TemplateEngine;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 */
public abstract class UIBean extends Component {
	final protected static transient Random RANDOM = new Random();
	protected String id;

	protected Theme theme;

	protected Theme innerTheme;

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
			theme = (Theme) stack.getContext().get(Theme.INNER_THEME);
			if (null == theme) {
				theme = (Theme) stack.getContext().get(Theme.THEME);
			}
			return theme;
		} else {
			return theme;
		}
	}

	public void setTheme(String theme) {
		this.theme = new Theme(theme);
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
		if (StringUtils.isEmpty(text)) return defaultText;
		if (!CharUtils.isAsciiAlpha(text.charAt(0))) return defaultText;
		if (-1 == text.indexOf('.') || -1 < text.indexOf(' ')) {
			return defaultText;
		} else {
			return TextProviderHelper.getText(text, defaultText, Collections.emptyList(), stack, false);
		}
	}

	protected String getRequestURI() {
		HttpServletRequest req = (HttpServletRequest) stack.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		return req.getRequestURI();
	}

	protected Object getValue(Object obj, String property) {
		stack.push(obj);
		try {
			return stack.findValue(property);
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
		if (StringUtils.isEmpty(this.id)) {
			int nextInt = RANDOM.nextInt();
			nextInt = (nextInt == Integer.MIN_VALUE) ? Integer.MAX_VALUE : Math.abs(nextInt);
			this.id = Theme.getTemplateName(getClass()) + String.valueOf(nextInt);
		}
	}
}
