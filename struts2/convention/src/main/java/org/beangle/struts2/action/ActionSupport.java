/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.struts2.action;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.lang.Chars;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextBundleRegistry;
import org.beangle.commons.text.i18n.TextCache;
import org.beangle.commons.text.i18n.TextFormater;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.text.i18n.TextResourceProvider;
import org.beangle.commons.web.util.CookieUtils;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.struts2.convention.ActionMessages;
import org.beangle.struts2.convention.Flash;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;

/**
 * Provides a default implementation for the most common actions. See the
 * documentation for all the interfaces this class implements for more detailed
 * information.
 * <p>
 */
public class ActionSupport implements TextResourceProvider {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * The action execution was successful. Show result
   * view to the end user.
   */
  public static final String SUCCESS = "success";

  /**
   * The action execution was a failure.
   * Show an error view, possibly asking the
   * user to retry entering data.
   */
  public static final String ERROR = "error";

  /**
   * The action execution require more input
   * in order to succeed.
   */
  public static final String INPUT = "input";

  public String index() throws Exception {
    return forward();
  }

  /**
   * A default implementation that does nothing an returns "success".
   * <p/>
   * Subclasses should override this method to provide their business logic.
   * <p/>
   * See also {@link com.opensymphony.xwork2.Action#execute()}.
   * 
   * @return returns {@link #SUCCESS}
   * @throws Exception
   *           can be thrown by subclasses.
   */
  public String execute() throws Exception {
    return forward(new Action((Class<?>) null, "index"));
  }

  protected final String forward() {
    return ActionContext.getContext().getActionInvocation().getProxy().getMethod();
  }

  protected final String forward(String view) {
    return view;
  }

  protected final String forward(String view, String message) {
    addMessage(getText(message));
    return view;
  }

  /**
   * @param action
   */
  protected final String forward(Action action) {
    ActionContext.getContext().getContextMap().put("dispatch_action", action);
    return "chain:dispatch_action";
  }

  protected final String forward(Action action, String message) {
    if (Strings.isNotBlank(message)) {
      if (Strings.contains(message, "error")) addError(message);
      else addMessage(message);
    }
    return forward(action);
  }

  /**
   * @param method
   * @param message
   * @param params
   */
  protected final String redirect(String method, String message, String params) {
    return redirect(new Action((String) null, method, params), message);
  }

  protected final String redirect(String method) {
    return redirect(new Action(method), null);
  }

  /**
   * @param method
   * @param message
   */
  protected final String redirect(String method, String message) {
    return redirect(new Action(method), message);
  }

  protected final String redirect(Action action, String message) {
    if (Strings.isNotEmpty(message)) addFlashMessage(message);
    ActionContext.getContext().getContextMap().put("dispatch_action", action);
    return "redirectAction:dispatch_action";
  }

  public Locale getLocale() {
    ActionContext ctx = ActionContext.getContext();
    if (ctx != null) {
      return ctx.getLocale();
    } else {
      logger.debug("Action context not initialized");
      return null;
    }
  }

  public final String getText(String aTextName) {
    return getTextResource().getText(aTextName);
  }

  public final String getText(String key, String defaultValue, Object... args) {
    return getTextResource().getText(key, defaultValue, args);
  }

  protected final TextResource getTextResource() {
    return getTextResource(getLocale());
  }

  public final TextResource getTextResource(Locale locale) {
    ActionContext context = ActionContext.getContext();
    TextResource resource = (TextResource) context.get("textResource");
    if (resource == null) {
      if (null == locale) locale = getLocale();
      Container container = ActionContext.getContext().getContainer();
      TextFormater formater = container.getInstance(TextFormater.class);
      TextBundleRegistry registry = container.getInstance(TextBundleRegistry.class);
      TextCache cache = null;
      String reload = container.getInstance(String.class, "beangle.i18n.reload");
      if (Objects.equals(reload, "false")) {
        cache = container.getInstance(TextCache.class, "flat");
      }
      resource = new ActionTextResource(getClass(), locale, registry, formater, context.getValueStack(),
          cache);
      context.put("textResource", resource);
    }
    return resource;
  }

  protected final String getTextInternal(String msgKey, Object... args) {
    if (Strings.isEmpty(msgKey)) return null;
    if (Chars.isAsciiAlpha(msgKey.charAt(0)) && msgKey.indexOf('.') > 0) {
      return getText(msgKey, msgKey, args);
    } else {
      return msgKey;
    }
  }

  /**
   * Add action message.
   * 
   * @param msgKey
   * @param args
   */
  protected final void addMessage(String msgKey, Object... args) {
    getFlash().addMessageNow(getTextInternal(msgKey, args));
  }

  /**
   * Add action error.
   * 
   * @param msgKey
   * @param args
   */
  protected final void addError(String msgKey, Object... args) {
    getFlash().addErrorNow(getTextInternal(msgKey, args));
  }

  /**
   * Add error to next action.
   * 
   * @param msgKey
   * @param args
   */
  protected final void addFlashError(String msgKey, Object... args) {
    getFlash().addError(getTextInternal(msgKey, args));
  }

  /**
   * Add message to next action.
   * 
   * @param msgKey
   * @param args
   */
  protected final void addFlashMessage(String msgKey, Object... args) {
    getFlash().addMessage(getTextInternal(msgKey, args));
  }

  protected final Flash getFlash() {
    Flash flash = (Flash) ActionContext.getContext().getSession().get("flash");
    if (null == flash) {
      flash = new Flash();
      ActionContext.getContext().getSession().put("flash", flash);
    }
    return flash;
  }

  /**
   * 获得action消息<br>
   */
  public final List<String> getActionMessages() {
    Flash flash = getFlash();
    ActionMessages messages = (ActionMessages) flash.get(Flash.MESSAGES);
    if (null == messages) return Collections.emptyList();
    else return messages.getMessages();
  }

  /**
   * 获得aciton错误消息<br>
   */
  public final List<String> getActionErrors() {
    Flash flash = getFlash();
    ActionMessages messages = (ActionMessages) flash.get(Flash.MESSAGES);
    if (null == messages) return Collections.emptyList();
    else return messages.getErrors();
  }

  // get parameters
  protected final String getRemoteAddr() {
    HttpServletRequest request = ServletActionContext.getRequest();
    if (null == request) return null;
    return RequestUtils.getIpAddr(request);
  }

  protected final void put(String key, Object value) {
    ContextHelper.put(key, value);
  }

  protected final Object[] getAll(String paramName) {
    return Params.getAll(paramName);
  }

  protected final <T> T[] getAll(String paramName, Class<T> clazz) {
    return Params.getAll(paramName, clazz);
  }

  protected final String get(String paramName) {
    return Params.get(paramName);
  }

  @SuppressWarnings({ "unchecked" })
  protected final <T> T get(String paramName, T defaultValue) {
    String value = Params.get(paramName);
    if (null == value || Strings.isEmpty(value)) return defaultValue;
    else return (T) Params.converter.convert(value, defaultValue.getClass());
  }

  protected final Object getAttribute(String name) {
    return ActionContext.getContext().getContextMap().get(name);
  }

  @SuppressWarnings("unchecked")
  protected final <T> Object getAttribute(String name, Class<T> clazz) {
    return (T) ActionContext.getContext().getContextMap().get(name);
  }

  protected final <T> T get(String name, Class<T> clazz) {
    return Params.get(name, clazz);
  }

  protected final Boolean getBoolean(String name) {
    return Params.getBoolean(name);
  }

  protected final boolean getBool(String name) {
    return Params.getBool(name);
  }

  protected final java.sql.Date getDate(String name) {
    return Params.getDate(name);
  }

  protected final Date getDateTime(String name) {
    return Params.getDateTime(name);
  }

  protected final Float getFloat(String name) {
    return Params.getFloat(name);
  }

  protected final Short getShort(String name) {
    return Params.getShort(name);
  }

  protected final Integer getInt(String name) {
    return Params.getInt(name);
  }

  protected final Long getLong(String name) {
    return Params.getLong(name);
  }

  protected final String getCookieValue(String cookieName) {
    return CookieUtils.getCookieValue(ServletActionContext.getRequest(), cookieName);
  }

  protected final void addCookie(String name, String value, String path, int age) {
    try {
      CookieUtils.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name,
          value, path, age);
    } catch (Exception e) {
      logger.error("setCookie error", e);
    }
  }

  protected final void addCookie(String name, String value, int age) {
    try {
      CookieUtils.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name,
          value, age);
    } catch (Exception e) {
      logger.error("setCookie error", e);
    }
  }

  protected final void deleteCookie(String name) {
    CookieUtils.deleteCookieByName(ServletActionContext.getRequest(), ServletActionContext.getResponse(),
        name);
  }

  protected final URL getResource(String name) {
    URL url = ClassLoaders.getResource(name, getClass());
    if (url == null) logger.error("Cannot load template {}", name);
    return url;
  }

  protected final HttpServletRequest getRequest() {
    return ServletActionContext.getRequest();
  }

  protected final HttpServletResponse getResponse() {
    return ServletActionContext.getResponse();
  }

  protected final Map<String, Object> getSession() {
    return ActionContext.getContext().getSession();
  }

}
