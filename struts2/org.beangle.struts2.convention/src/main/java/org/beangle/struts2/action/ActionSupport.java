/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.i18n.TextBundleRegistry;
import org.beangle.commons.i18n.TextFormater;
import org.beangle.commons.i18n.TextResource;
import org.beangle.commons.i18n.TextResourceProvider;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Chars;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.CookieUtils;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.struts2.convention.ActionMessages;
import org.beangle.struts2.convention.Flash;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.PopulateHelper;
import org.beangle.struts2.helper.QueryHelper;
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

  protected Logger logger = LoggerFactory.getLogger(ActionSupport.class);

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
    TextResource textResource = (TextResource) context.get("textResource");
    if (textResource == null) {
      if (null == locale) locale = getLocale();
      Container container = ActionContext.getContext().getContainer();
      TextFormater formater = container.getInstance(TextFormater.class);
      TextBundleRegistry registry = container.getInstance(TextBundleRegistry.class);
      textResource = new ActionTextResource(getClass(), locale, registry, formater, context.getValueStack());
      context.put("textResource", textResource);
    }
    return textResource;
  }

  protected final String getTextInternal(String msgKey, Object... args) {
    if (null == msgKey) return null;
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

  protected final int getInt(String name) {
    Integer rs = Params.getInteger(name);
    return null == rs ? 0 : rs.intValue();
  }

  protected final Integer getInteger(String name) {
    return Params.getInteger(name);
  }

  protected final Long getLong(String name) {
    return Params.getLong(name);
  }

  protected final Long getId(String shortName) {
    return getId(Long.class, shortName);
  }

  /**
   * Get entity's id from shortname.id,shortnameId,id
   * 
   * @param shortName
   * @param clazz
   */
  protected final <T> T getId(Class<T> clazz, String shortName) {
    String entityId = get(shortName + ".id");
    if (null == entityId) {
      entityId = get(shortName + "Id");
    }
    if (null == entityId) {
      entityId = get("id");
    }
    if (null == entityId) return null;
    else return Params.converter.convert(entityId, clazz);
  }

  /**
   * Get entity's long id array from parameters shortname.id,shortname.ids,shortnameIds
   * 
   * @param shortName
   */
  protected final Long[] getIds(String shortName) {
    return getIds(Long.class, shortName);
  }

  /**
   * Get entity's id array from parameters shortname.id,shortname.ids,shortnameIds
   * 
   * @param shortName
   * @param clazz
   * @return empty array if not found
   */
  protected final <T> T[] getIds(Class<T> clazz, String shortName) {
    T[] datas = Params.getAll(shortName + ".id", clazz);
    if (null == datas) {
      String datastring = Params.get(shortName + ".ids");
      if (null == datastring) datastring = Params.get(shortName + "Ids");
      if (null == datastring) {
        Array.newInstance(clazz, 0);
      } else {
        return Params.converter.convert(Strings.split(datastring, ","), clazz);
      }
    }
    return datas;
  }

  // populate------------------------------------------------------------------

  /**
   * 将request中的参数设置到clazz对应的bean。
   * 
   * @param clazz
   * @param shortName
   */
  protected final <T> T populate(Class<T> clazz, String shortName) {
    return PopulateHelper.populate(clazz, shortName);
  }

  protected final void populate(Object obj, String shortName) {
    Model.populate(Params.sub(shortName), obj);
  }

  protected final Object populate(String entityName) {
    return PopulateHelper.populate(entityName);
  }

  protected final Object populate(Class<?> clazz) {
    return PopulateHelper.populate(clazz);
  }

  protected final Object populate(String entityName, String shortName) {
    return PopulateHelper.populate(entityName, shortName);
  }

  protected final Object populate(Object obj, String entityName, String shortName) {
    return PopulateHelper.populate(obj, entityName, shortName);
  }

  protected final void populate(Map<String, Object> params, Entity<?> entity, String entityName) {
    Assert.notNull(entity, "Cannot populate to null.");
    Model.getPopulator().populate(entity, entityName, params);
  }

  protected final void populate(Map<String, Object> params, Entity<?> entity) {
    Assert.notNull(entity, "Cannot populate to null.");
    Model.populate(params, entity);
  }

  // query------------------------------------------------------
  protected final int getPageNo() {
    return QueryHelper.getPageNo();
  }

  protected final int getPageSize() {
    return QueryHelper.getPageSize();
  }

  /**
   * 从request的参数或者cookie中(参数优先)取得分页信息
   */
  protected final PageLimit getPageLimit() {
    return QueryHelper.getPageLimit();
  }

  protected final void populateConditions(OqlBuilder<?> builder) {
    QueryHelper.populateConditions(builder);
  }

  protected final void populateConditions(OqlBuilder<?> builder, String exclusiveAttrNames) {
    QueryHelper.populateConditions(builder, exclusiveAttrNames);
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
    if (url == null) {
      logger.error("Cannot load template {}", name);
    }
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
