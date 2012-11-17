/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import static org.beangle.struts2.view.bean.ConstantBeanNames.GeneratorName;
import static org.beangle.struts2.view.bean.ConstantBeanNames.TextResourceName;
import static org.beangle.struts2.view.bean.ConstantBeanNames.UrlRenderName;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.i18n.TextResource;
import org.beangle.commons.i18n.TextResourceProvider;
import org.beangle.struts2.view.UITheme;
import org.beangle.struts2.view.bean.ActionUrlRender;
import org.beangle.struts2.view.bean.IndexableIdGenerator;
import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Abstract Freemarker tag models
 * 
 * @author chaostone
 * @since 3.0.2
 */
public abstract class AbstractModels {

  protected final ValueStack stack;

  protected final HttpServletRequest req;

  protected final HttpServletResponse res;

  protected final UITheme theme;

  protected final ActionUrlRender render;

  protected final TextResource textResource;

  protected final Map<Class<?>, TagModel> models = CollectUtils.newHashMap();

  /**
   * New taglibrary.Try to find genertor ,urlrender textresource.
   */
  public AbstractModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    super();
    this.stack = stack;
    this.req = req;
    this.res = res;

    theme = UITheme.getTheme(getUitheme(), req.getContextPath());

    Map<String, Object> ctx = stack.getContext();
    if (null == ctx.get(Theme.Theme)) ctx.put(Theme.Theme, Theme.getTheme(Theme.DefaultTheme));
    if (null == ctx.get(GeneratorName)) {
      String queryString = req.getQueryString();
      String fullpath = (null == queryString) ? req.getRequestURI() : req.getRequestURI() + queryString;
      ctx.put(GeneratorName, new IndexableIdGenerator(Math.abs(fullpath.hashCode())));
    }

    ActionUrlRender urlRender = (ActionUrlRender) ctx.get(UrlRenderName);
    if (null == urlRender) {
      Container container = (Container) stack.getContext().get(ActionContext.CONTAINER);
      urlRender = container.getInstance(ActionUrlRender.class);
      ctx.put(UrlRenderName, urlRender);
    }
    this.render = urlRender;

    TextResource tp = (TextResource) ctx.get(TextResourceName);
    if (null == tp) {
      for (Object o : stack.getRoot()) {
        if (o instanceof TextResourceProvider) {
          tp = ((TextResourceProvider) o).getTextResource(null);
          ctx.put(TextResourceName, tp);
          break;
        }
      }
    }
    this.textResource = tp;
  }

  protected String getUitheme() {
    String uitheme = req.getParameter("ui.theme");
    if (null == uitheme) {
      HttpSession session = req.getSession(false);
      if (null != session && null != session.getAttribute("ui.theme")) {
        uitheme = (String) session.getAttribute("ui.theme");
      }
    }
    if (null == uitheme) uitheme = "default";
    return uitheme;
  }

  protected TagModel get(final Class<? extends Component> clazz) {
    TagModel model = models.get(clazz);
    if (null == model) {
      model = new TagModel(stack, clazz);
      models.put(clazz, model);
    }
    return model;
  }

  public UITheme getTheme() {
    return theme;
  }

}
