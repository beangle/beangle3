/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.interceptor;

import java.util.Locale;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import static com.opensymphony.xwork2.util.LocalizedTextUtil.localeFromString;

/**
 * Simplify I18nInterceptor
 * 
 * @author chaostone
 * @since 2.4
 */
public class I18nInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 3154197236572163145L;

  public static final String SessionAttribute = "WW_TRANS_I18N_LOCALE";
  public static final String SessionParameter = "session_locale";
  public static final String RequestParameter = "request_locale";

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> params = invocation.getInvocationContext().getParameters();
    Locale locale = null;
    // get session locale
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    if (null != session) {
      String session_locale = findLocaleParameter(params, SessionParameter);
      if (null == session_locale) {
        locale = (Locale) session.get(SessionAttribute);
      } else {
        locale = localeFromString(session_locale, null);
        // save it in session
        session.put(SessionAttribute, locale);
      }
    }
    // get request locale
    String request_locale = findLocaleParameter(params, RequestParameter);
    if (null != request_locale) locale = localeFromString(request_locale, null);

    if (null != locale) invocation.getInvocationContext().setLocale(locale);
    return invocation.invoke();
  }

  private String findLocaleParameter(Map<String, Object> params, String parameterName) {
    Object localParam = params.remove(parameterName);
    if (localParam != null && localParam.getClass().isArray() && ((Object[]) localParam).length == 1) {
      localParam = ((Object[]) localParam)[0];
    }
    return null == localParam ? null : localParam.toString();
  }
}
