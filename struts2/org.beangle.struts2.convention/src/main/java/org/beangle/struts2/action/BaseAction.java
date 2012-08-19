/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Chars;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.CookieUtils;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.struts2.convention.Flash;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.PopulateHelper;
import org.beangle.struts2.helper.QueryHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ClassLoaderUtil;

public abstract class BaseAction extends ActionSupport {

  public String index() throws Exception {
    return forward();
  }

  /**
   * forward to index method
   */
  public String execute() throws Exception {
    return forward(new Action((Class<?>) null, "index"));
  }

  protected String forward() {
    return ActionContext.getContext().getActionInvocation().getProxy().getMethod();
  }

  protected String forward(String view) {
    return view;
  }

  protected String forward(String view, String message) {
    addActionMessage(getText(message));
    return view;
  }

  /**
   * @param action
   * @return
   */
  protected String forward(Action action) {
    ActionContext.getContext().getContextMap().put("dispatch_action", action);
    return "chain:dispatch_action";
  }

  protected String forward(Action action, String message) {
    if (Strings.isNotBlank(message)) {
      if (Strings.contains(message, "error")) {
        addFlashErrorNow(message);
      } else {
        addFlashMessageNow(message);
      }
    }
    return forward(action);
  }

  /**
   * @param method
   * @param message
   * @param params
   * @return
   */
  protected String redirect(String method, String message, String params) {
    return redirect(new Action((String) null, method, params), message);
  }

  protected String redirect(String method) {
    return redirect(new Action(method), null);
  }

  /**
   * @param method
   * @param message
   * @return
   */
  protected String redirect(String method, String message) {
    return redirect(new Action(method), message);
  }

  protected String redirect(Action action, String message) {
    if (Strings.isNotEmpty(message)) {
      addFlashMessage(message);
    }
    ActionContext.getContext().getContextMap().put("dispatch_action", action);
    return "redirectAction:dispatch_action";
  }

  protected String getTextInternal(String msgKey, Object... args) {
    if (null == msgKey) return null;
    if (Chars.isAsciiAlpha(msgKey.charAt(0)) && msgKey.indexOf('.') > 0) {
      return getText(msgKey, msgKey, args);
    } else {
      return msgKey;
    }
  }

  protected void addMessage(String msgKey) {
    addActionMessage(getTextInternal(msgKey));
  }

  protected void addError(String msgKey) {
    addActionError(getTextInternal(msgKey));
  }

  protected void addFlashError(String msgKey, Object... args) {
    getFlash().addError(getTextInternal(msgKey, args));
  }

  protected void addFlashMessage(String msgKey, Object... args) {
    getFlash().addMessage(getTextInternal(msgKey, args));
  }

  protected void addFlashMessageNow(String msgKey, Object... args) {
    getFlash().addMessageNow(getTextInternal(msgKey, args));
  }

  protected void addFlashErrorNow(String msgKey, Object... args) {
    getFlash().addErrorNow(getTextInternal(msgKey, args));
  }

  protected Flash getFlash() {
    Flash flash = (Flash) ActionContext.getContext().getSession().get("flash");
    if (null == flash) {
      flash = new Flash();
      ActionContext.getContext().getSession().put("flash", flash);
    }
    return flash;
  }

  /**
   * 将flash中的消息转移到actionmessage<br>
   * 不要将flash和message混合使用。
   */
  public Collection<String> getActionMessages() {
    Flash flash = getFlash();
    @SuppressWarnings("unchecked")
    List<String> messages = (List<String>) flash.get(Flash.MESSAGES);
    if (null != messages) {
      for (String msg : messages) {
        addActionMessage(msg);
      }
      messages.clear();
    }
    return super.getActionMessages();
  }

  /**
   * 将flash中的错误转移到actionerror<br>
   * 不要将flash和error混合使用。
   */
  public Collection<String> getActionErrors() {
    Flash flash = getFlash();
    @SuppressWarnings("unchecked")
    List<String> errors = (List<String>) flash.get(Flash.ERRORS);
    if (null != errors) {
      for (String msg : errors) {
        addActionError(msg);
      }
      errors.clear();
    }
    return super.getActionErrors();
  }

  // get parameters
  protected String getRemoteAddr() {
    HttpServletRequest request = ServletActionContext.getRequest();
    if (null == request) return null;
    return RequestUtils.getIpAddr(request);
  }

  protected void put(String key, Object value) {
    ContextHelper.put(key, value);
  }

  protected Object[] getAll(String paramName) {
    return Params.getAll(paramName);
  }

  protected <T> T[] getAll(String paramName, Class<T> clazz) {
    return Params.getAll(paramName, clazz);
  }

  protected String get(String paramName) {
    return Params.get(paramName);
  }

  @SuppressWarnings({ "unchecked" })
  protected <T> T get(String paramName, T defaultValue) {
    String value = Params.get(paramName);
    if (null == value || Strings.isEmpty(value)) return defaultValue;
    else return (T) Params.converter.convert(value, defaultValue.getClass());
  }

  protected Object getAttribute(String name) {
    return ActionContext.getContext().getContextMap().get(name);
  }

  @SuppressWarnings("unchecked")
  protected <T> Object getAttribute(String name, Class<T> clazz) {
    return (T) ActionContext.getContext().getContextMap().get(name);
  }

  protected <T> T get(String name, Class<T> clazz) {
    return Params.get(name, clazz);
  }

  protected Boolean getBoolean(String name) {
    return Params.getBoolean(name);
  }

  protected boolean getBool(String name) {
    return Params.getBool(name);
  }

  protected java.sql.Date getDate(String name) {
    return Params.getDate(name);
  }

  protected Date getDateTime(String name) {
    return Params.getDateTime(name);
  }

  protected Float getFloat(String name) {
    return Params.getFloat(name);
  }

  protected int getInt(String name) {
    Integer rs = Params.getInteger(name);
    return null == rs ? 0 : rs.intValue();
  }

  protected Integer getInteger(String name) {
    return Params.getInteger(name);
  }

  protected Long getLong(String name) {
    return Params.getLong(name);
  }

  protected Long getId(String shortName) {
    return getId(Long.class, shortName);
  }

  /**
   * Get entity's id from shortname.id,shortnameId,id
   * 
   * @param shortName
   * @param clazz
   * @return
   */
  protected <T> T getId(Class<T> clazz, String shortName) {
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
   * @return
   */
  protected Long[] getIds(String shortName) {
    return getIds(Long.class, shortName);
  }

  /**
   * Get entity's id array from parameters shortname.id,shortname.ids,shortnameIds
   * 
   * @param shortName
   * @param clazz
   * @return empty array if not found
   */
  protected <T> T[] getIds(Class<T> clazz, String shortName) {
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
   * @param request
   * @param clazz
   * @param title
   * @return
   */
  protected <T> T populate(Class<T> clazz, String shortName) {
    return PopulateHelper.populate(clazz, shortName);
  }

  protected void populate(Object obj, String shortName) {
    Model.populate(Params.sub(shortName), obj);
  }

  protected Object populate(String entityName) {
    return PopulateHelper.populate(entityName);
  }

  protected Object populate(Class<?> clazz) {
    return PopulateHelper.populate(clazz);
  }

  protected Object populate(String entityName, String shortName) {
    return PopulateHelper.populate(entityName, shortName);
  }

  protected Object populate(Object obj, String entityName, String shortName) {
    return PopulateHelper.populate(obj, entityName, shortName);
  }

  protected void populate(Map<String, Object> params, Entity<?> entity, String entityName) {
    Assert.notNull(entity, "Cannot populate to null.");
    Model.getPopulator().populate(entity, entityName, params);
  }

  protected void populate(Map<String, Object> params, Entity<?> entity) {
    Assert.notNull(entity, "Cannot populate to null.");
    Model.populate(params, entity);
  }

  // query------------------------------------------------------
  protected int getPageNo() {
    return QueryHelper.getPageNo();
  }

  protected int getPageSize() {
    return QueryHelper.getPageSize();
  }

  /**
   * 从request的参数或者cookie中(参数优先)取得分页信息
   * 
   * @param request
   * @return
   */
  protected PageLimit getPageLimit() {
    return QueryHelper.getPageLimit();
  }

  protected void populateConditions(OqlBuilder<?> builder) {
    QueryHelper.populateConditions(builder);
  }

  protected void populateConditions(OqlBuilder<?> builder, String exclusiveAttrNames) {
    QueryHelper.populateConditions(builder, exclusiveAttrNames);
  }

  protected String getCookieValue(String cookieName) {
    return CookieUtils.getCookieValue(ServletActionContext.getRequest(), cookieName);
  }

  protected void addCookie(String name, String value, String path, int age) {
    try {
      CookieUtils.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name,
          value, path, age);
    } catch (Exception e) {
      logger.error("setCookie error", e);
    }
  }

  protected void addCookie(String name, String value, int age) {
    try {
      CookieUtils.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name,
          value, age);
    } catch (Exception e) {
      logger.error("setCookie error", e);
    }
  }

  protected void deleteCookie(String name) {
    CookieUtils.deleteCookieByName(ServletActionContext.getRequest(), ServletActionContext.getResponse(),
        name);
  }

  protected URL getResource(String name) {
    URL url = ClassLoaderUtil.getResource(name, getClass());
    if (url == null) {
      logger.error("Cannot load template {}", name);
    }
    return url;
  }

  protected HttpServletRequest getRequest() {
    return ServletActionContext.getRequest();
  }

  protected HttpServletResponse getResponse() {
    return ServletActionContext.getResponse();
  }

  protected Map<String, Object> getSession() {
    return ActionContext.getContext().getSession();
  }
}
