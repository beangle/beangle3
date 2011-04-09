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

import org.apache.commons.lang.xwork.StringUtils;
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

	public String getParameterString() {
		StringBuilder sb = new StringBuilder(parameters.size() * 10);
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue().toString())
					.append("\"");
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
			return (Theme) stack.getContext().get(Theme.THEME);
		} else {
			return theme;
		}
	}

	public void setTheme(String theme) {
		this.theme = new Theme(theme);
	}

	protected String getText(String text) {
		if (StringUtils.isEmpty(text)) return text;
		if (-1 == text.indexOf('.') || -1 < text.indexOf(' ')) {
			return text;
		} else {
			return TextProviderHelper.getText(text, text, Collections.emptyList(), stack, false);
		}
	}

	protected String getText(String text, String defaultText) {
		if (StringUtils.isEmpty(text)) return text;
		if (-1 == text.indexOf('.') || -1 < text.indexOf(' ')) {
			return text;
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
