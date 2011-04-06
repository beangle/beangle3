/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License; Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.url;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

/**
 * @author chaostone
 * @version $Id: UrlBuilder.java Nov 12; 2010 5:42:10 PM chaostone $
 */
public class UrlBuilder {
	private String scheme;
	private String serverName;
	private int port;
	// start with /
	private String contextPath;
	// start with /
	private String servletPath;
	// start with /
	private String requestURI;
	private String pathInfo;
	private String queryString;

	public UrlBuilder(String contextPath) {
		super();
		Validate.notEmpty(contextPath);
		this.contextPath = contextPath;
	}

	/**
	 * ServetPath without contextPath
	 * 
	 * @return
	 */
	private String buildServletPath() {
		String uri = servletPath;
		if (uri == null && null != requestURI) {
			uri = requestURI;
			if (!contextPath.equals("/")) uri = uri.substring(contextPath.length());
		}
		return (null == uri) ? "" : uri;
	}

	/**
	 * Request Url contain pathinfo and queryString but without contextPath .
	 * 
	 * @return
	 */
	public String buildRequestUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(buildServletPath());
		if (null != pathInfo) {
			sb.append(pathInfo);
		}
		if (null != queryString) {
			sb.append('?').append(queryString);
		}
		return sb.toString();
	}

	/**
	 * build full url
	 * 
	 * @return
	 */
	public String buildUrl() {
		StringBuilder sb = new StringBuilder();
		boolean includePort = true;
		if (null != scheme) {
			sb.append(scheme).append("://");
			includePort = (port != (scheme.equals("http") ? 80 : 443));
		}
		if (null != serverName) {
			sb.append(serverName);
			if (includePort && port > 0) {
				sb.append(':').append(port);
			}
		}
		if (!ObjectUtils.equals(contextPath, "/")) {
			sb.append(contextPath);
		}
		sb.append(buildRequestUrl());
		return sb.toString();
	}

	public UrlBuilder scheme(String scheme) {
		this.scheme = scheme;
		return this;
	}

	public UrlBuilder serverName(String serverName) {
		this.serverName = serverName;
		return this;
	}

	public UrlBuilder port(int port) {
		this.port = port;
		return this;
	}

	/**
	 * ContextPath should start with / but not ended with /
	 * 
	 * @param contextPath
	 * @return
	 */
	public UrlBuilder contextPath(String contextPath) {
		this.contextPath = contextPath;
		return this;
	}

	/**
	 * start with /
	 * 
	 * @param servletPath
	 * @return
	 */
	public UrlBuilder servletPath(String servletPath) {
		this.servletPath = servletPath;
		return this;
	}

	/**
	 * start with /
	 * 
	 * @param requestURI
	 * @return
	 */
	public UrlBuilder requestURI(String requestURI) {
		this.requestURI = requestURI;
		return this;
	}

	public UrlBuilder pathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
		return this;
	}

	public UrlBuilder queryString(String queryString) {
		this.queryString = queryString;
		return this;
	}
}
