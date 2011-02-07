/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.interceptor;

import java.util.Locale;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class I18nInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 3154197236572163145L;

	public static final String DEFAULT_SESSION_ATTRIBUTE = "WW_TRANS_I18N_LOCALE";
	public static final String DEFAULT_SESSION_PARAMETER = "session_locale";
	public static final String DEFAULT_REQUEST_PARAMETER = "request_locale";

	protected String sessionParameterName = DEFAULT_SESSION_PARAMETER;
	protected String requestParameterName = DEFAULT_REQUEST_PARAMETER;
	protected String attributeName = DEFAULT_SESSION_ATTRIBUTE;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> params = invocation.getInvocationContext().getParameters();
		Locale locale = null;
		// get session locale
		String session_locale = findLocaleParameter(params, sessionParameterName);
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		if (null != session) {
			if (null == session_locale) {
				locale = (Locale) session.get(attributeName);
			} else {
				locale = LocalizedTextUtil.localeFromString(session_locale, null);
				// save it in session
				session.put(attributeName, locale);
			}
		}
		// get request locale
		String request_locale = findLocaleParameter(params, requestParameterName);
		if (null != request_locale) {
			locale = LocalizedTextUtil.localeFromString(request_locale, null);
		}
		if (null != locale) invocation.getInvocationContext().setLocale(locale);
		return invocation.invoke();
	}

	private String findLocaleParameter(Map<String, Object> params, String parameterName) {
		Object requested_locale = params.remove(parameterName);
		if (requested_locale != null && requested_locale.getClass().isArray()
				&& ((Object[]) requested_locale).length == 1) {
			requested_locale = ((Object[]) requested_locale)[0];
		}
		return null == requested_locale ? null : requested_locale.toString();
	}

	public void setSessionParameterName(String sessionParameterName) {
		this.sessionParameterName = sessionParameterName;
	}

	public void setRequestParameterName(String requestParameterName) {
		this.requestParameterName = requestParameterName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
}
