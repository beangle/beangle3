/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.Validate;

public class UrlRender {

	private String prefix;
	// encode
	private boolean escapeAmp;

	public UrlRender() {
		super();
	}

	public UrlRender(String prefix) {
		super();
		this.prefix = prefix;
	}

	public String render(String referer, String uri, Map<String, String> params) {
		String separator = "&";
		if (escapeAmp) {
			separator = "&amp;";
		}
		StringBuilder sb = renderUri(referer, uri);
		sb.append(separator);
		for (String key : params.keySet()) {
			try {
				sb.append(key).append('=').append(URLEncoder.encode(params.get(key), "UTF-8"));
				sb.append(separator);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		sb.delete(sb.length() - separator.length(), sb.length());
		return sb.toString();
	}

	public String render(String referer, String uri, String... params) {
		String separator = "&";
		if (escapeAmp) {
			separator = "&amp;";
		}
		StringBuilder sb = renderUri(referer, uri);
		sb.append(separator);
		for (String param : params) {
			try {
				sb.append(URLEncoder.encode(param, "UTF-8"));
				sb.append(separator);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		sb.delete(sb.length() - separator.length(), sb.length());
		return sb.toString();
	}

	public String render(String referer, String uri) {
		return renderUri(referer,uri).toString();
	}
	
	private StringBuilder renderUri(String referer, String uri) {
		Validate.notNull(referer);
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isEmpty(uri)){
			sb.append(referer);
			return sb;
		}
		// query string
		String queryStr = null;
		int questIndex = uri.indexOf('?');
		if (-1 == questIndex) {
			questIndex = uri.length();
		} else {
			queryStr = uri.substring(questIndex + 1);
			uri = uri.substring(0, questIndex);
		}
		// uri
		if (uri.startsWith("/")) {
			int rirstslash = referer.indexOf("/", 1);
			String context = (-1 == rirstslash) ? "" : referer.substring(0, rirstslash);
			sb.append(context);
			sb.append(uri.substring(0, questIndex));
		} else {
			int lastslash = referer.lastIndexOf("/");
			String namespace = referer.substring(0, lastslash);
			sb.append(namespace);
			if (uri.startsWith("!")) {
				int dot = referer.indexOf("!", lastslash);
				if (-1 == dot) {
					dot = referer.indexOf(".", lastslash);
				}
				dot = (-1 == dot) ? referer.length() : dot;
				String action = referer.substring(lastslash, dot);
				sb.append(action);
				sb.append(uri);
			} else {
				sb.append('/').append(uri);
			}
		}
		// prefix
		if (null != prefix) sb.append(prefix);
		if (null != queryStr) {
			sb.append('?').append(queryStr);
		}
		return sb;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isEscapeAmp() {
		return escapeAmp;
	}

	public void setEscapeAmp(boolean escapeAmp) {
		this.escapeAmp = escapeAmp;
	}
}
