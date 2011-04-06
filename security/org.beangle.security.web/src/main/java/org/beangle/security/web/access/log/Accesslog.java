/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Access log entry
 * 
 * @author chaostone
 */
public class Accesslog {
	private String uri;
	private String params;
	private long beginAt;
	private long endAt;
	private Object user;

	public long getDuration() {
		if (0 == endAt) {
			return System.currentTimeMillis() - beginAt;
		} else {
			return endAt - beginAt;
		}
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public long getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(long beginAt) {
		this.beginAt = beginAt;
	}

	public long getEndAt() {
		return endAt;
	}

	public void setEndAt(long endAt) {
		this.endAt = endAt;
	}

	public Date getBeginTime() {
		if (0 != beginAt) {
			return new Date(beginAt);
		} else return null;
	}

	public Date getEndTime() {
		if (0 != endAt) {
			return new Date(endAt);
		} else return null;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(uri);
		if (null != params) {
			sb.append('?').append(params);
		}
		sb.append('(');
		DateFormat f = new SimpleDateFormat("HH:mm:ss");
		sb.append(f.format(new Date(beginAt)));
		sb.append('-');
		if (0 != endAt) {
			sb.append(f.format(new Date(endAt)));
			sb.append(" duration ").append((endAt - beginAt) / 1000).append(" s");
		} else {
			sb.append(" not ended");
		}
		sb.append(')');
		return sb.toString();
	}

}
