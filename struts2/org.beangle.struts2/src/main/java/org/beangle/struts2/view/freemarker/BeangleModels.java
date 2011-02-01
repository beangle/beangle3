/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import static org.apache.struts2.util.TextProviderHelper.getText;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.struts2.view.component.Anchor;
import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.component.Foot;
import org.beangle.struts2.view.component.Grid;
import org.beangle.struts2.view.component.Head;
import org.beangle.struts2.view.component.Messages;
import org.beangle.struts2.view.component.Pagebar;
import org.beangle.struts2.view.component.RedirectParams;
import org.beangle.struts2.view.component.Toolbar;
import org.beangle.web.url.UrlRender;

import com.opensymphony.xwork2.util.ValueStack;

public class BeangleModels {

	protected ValueStack stack;
	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected Map<Class<?>, TagModel> models = CollectUtils.newHashMap();
	private static final UrlRender render = new UrlRender(".action");

	public BeangleModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		this.stack = stack;
		this.req = req;
		this.res = res;
	}

	public String url(String url) {
		return render.render(req.getRequestURI(), url);
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
		TagModel model = models.get(Head.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Head(stack);
				}
			};
			models.put(Head.class, model);
		}
		return model;
	}

	public TagModel getFoot() {
		TagModel model = models.get(Foot.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Foot(stack);
				}
			};
			models.put(Foot.class, model);
		}
		return model;
	}

	public TagModel getToolbar() {
		TagModel model = models.get(Toolbar.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Toolbar(stack);
				}
			};
			models.put(Toolbar.class, model);
		}
		return model;
	}

	public TagModel getGrid() {
		TagModel model = models.get(Grid.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Grid(stack);
				}
			};
			models.put(Grid.class, model);
		}
		return model;
	}

	public TagModel getGridbar() {
		TagModel model = models.get(Grid.Bar.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Grid.Bar(stack);
				}
			};
			models.put(Grid.Bar.class, model);
		}
		return model;
	}

	public TagModel getRow() {
		TagModel model = models.get(Grid.Row.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Grid.Row(stack);
				}
			};
			models.put(Grid.Row.class, model);
		}
		return model;
	}

	public TagModel getCol() {
		TagModel model = models.get(Grid.Col.class);
		if (null == model) {
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
		TagModel model = models.get(Grid.Boxcol.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Grid.Boxcol(stack);
				}
			};
			models.put(Grid.Boxcol.class, model);
		}
		return model;
	}

	public TagModel getPagebar() {
		TagModel model = models.get(Pagebar.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Pagebar(stack);
				}
			};
			models.put(Pagebar.class, model);
		}
		return model;
	}

	public TagModel getA() {
		TagModel model = models.get(Anchor.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Anchor(stack);
				}
			};
			models.put(Anchor.class, model);
		}
		return model;
	}

	public TagModel getRedirectParams() {
		TagModel model = models.get(RedirectParams.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new RedirectParams(stack);
				}
			};
			models.put(RedirectParams.class, model);
		}
		return model;
	}

	public TagModel getMessages() {
		TagModel model = models.get(Messages.class);
		if (null == model) {
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Messages(stack);
				}
			};
			models.put(Messages.class, model);
		}
		return model;
	}
}
