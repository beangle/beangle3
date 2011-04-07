/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view;

import static org.apache.struts2.util.TextProviderHelper.getText;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.struts2.view.component.ActionUrlRender;
import org.beangle.struts2.view.component.Anchor;
import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.component.Div;
import org.beangle.struts2.view.component.Foot;
import org.beangle.struts2.view.component.Form;
import org.beangle.struts2.view.component.Grid;
import org.beangle.struts2.view.component.Head;
import org.beangle.struts2.view.component.Iframe;
import org.beangle.struts2.view.component.Messages;
import org.beangle.struts2.view.component.Pagebar;
import org.beangle.struts2.view.component.Qfield;
import org.beangle.struts2.view.component.Qfields;
import org.beangle.struts2.view.component.Qform;
import org.beangle.struts2.view.component.Qitem;
import org.beangle.struts2.view.component.Qselect;
import org.beangle.struts2.view.component.RedirectParams;
import org.beangle.struts2.view.component.Select;
import org.beangle.struts2.view.component.Submit;
import org.beangle.struts2.view.component.TextField;
import org.beangle.struts2.view.component.Toolbar;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

public class BeangleTagLibrary extends AbstractTagLibrary {

	private ActionUrlRender render;

	public BeangleTagLibrary() {
		super();
	}

	public BeangleTagLibrary(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
		this.stack.getContext().put(Theme.THEME, theme);
	}

	public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		BeangleTagLibrary models = new BeangleTagLibrary(stack, req, res);
		models.render = this.render;
		return models;
	}

	@Inject
	public void setRender(ActionUrlRender render) {
		this.render = render;
	}

	public String url(String url) {
		return render.render(req.getRequestURI(), url);
	}

	public String iconurl(String name) {
		return iconurl(name, "16x16");
	}

	public String iconurl(String name, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(size).append('x').append(size);
		return iconurl(name, sb.toString());
	}

	public String iconurl(String name, String size) {
		StringBuilder sb = new StringBuilder(80);
		String contextPath = req.getContextPath();
		if (contextPath.length() < 2) {
			sb.append("/static/themes/");
		} else {
			sb.append(contextPath).append("/static/themes/");
		}
		sb.append(theme.getName()).append("/icons/").append(size);
		if (!name.startsWith("/")) sb.append('/');
		sb.append(name);
		return sb.toString();
	}

	/**
	 * query string and form control
	 * 
	 * @return
	 */
	public String getParamstring() {
		StringWriter sw = new StringWriter();
		Enumeration<?> em = req.getParameterNames();
		while (em.hasMoreElements()) {
			String attr = (String) em.nextElement();
			String value = req.getParameter(attr);
			sw.write(attr);
			sw.write('=');
			try {
				StringEscapeUtils.escapeJavaScript(sw, value);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (em.hasMoreElements()) {
				sw.write('&');
			}
		}
		return sw.toString();
	}

	public boolean isPage(Object data) {
		return data instanceof Page<?>;
	}

	public String text(String name) {
		return getText(name, name, Collections.emptyList(), stack, false);
	}

	public String text(String name, Object arg0) {
		return getText(name, name, Collections.singletonList(arg0), stack, false);
	}

	public String text(String name, Object arg0, Object arg1) {
		return getText(name, name, CollectUtils.newArrayList(arg0, arg1), stack, false);
	}

	public TagModel getHead() {
		return get(Head.class);
	}

	public TagModel getIframe() {
		return get(Iframe.class);
	}

	public TagModel getFoot() {
		return get(Foot.class);
	}

	public TagModel getForm() {
		return get(Form.class);
	}

	public TagModel getSubmit() {
		return get(Submit.class);
	}

	public TagModel getToolbar() {
		return get(Toolbar.class);
	}

	public TagModel getGrid() {
		return get(Grid.class);
	}

	public TagModel getGridbar() {
		return get(Grid.Bar.class);
	}

	public TagModel getRow() {
		return get(Grid.Row.class);
	}

	public TagModel getCol() {
		TagModel model = models.get(Grid.Col.class);
		if (null == model) {
			// just for performance
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Grid.Col(stack);
				}
			};
			models.put(Grid.Col.class, model);
		}
		return model;
	}

	public TagModel getBoxcol() {
		return get(Grid.Boxcol.class);
	}

	public TagModel getPagebar() {
		return get(Pagebar.class);
	}

	public TagModel getA() {
		TagModel model = models.get(Anchor.class);
		if (null == model) {
			model = new TagModel(stack) {
				protected Component getBean() {
					return new Anchor(stack);
				}
			};
			models.put(Anchor.class, model);
		}
		return model;
	}

	public TagModel getRedirectParams() {
		return get(RedirectParams.class);
	}

	public TagModel getMessages() {
		return get(Messages.class);
	}

	public TagModel getTextfield() {
		return get(TextField.class);
	}

	public TagModel getQform() {
		return get(Qform.class);
	}

	public TagModel getQitem() {
		return get(Qitem.class);
	}

	public TagModel getQfield() {
		return get(Qfield.class);
	}

	public TagModel getQfields() {
		return get(Qfields.class);
	}

	public TagModel getQselect() {
		return get(Qselect.class);
	}

	public TagModel getDiv() {
		return get(Div.class);
	}

	public TagModel getSelect() {
		return get(Select.class);
	}
}
