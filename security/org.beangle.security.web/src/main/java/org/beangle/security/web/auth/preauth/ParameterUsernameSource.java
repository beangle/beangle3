/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.codec.EncryptUtil;
import org.beangle.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterUsernameSource implements UsernameSource {

	private final static Logger logger = LoggerFactory.getLogger(ParameterUsernameSource.class);

	private boolean enableExpired = true;

	// default 10min second
	private int expiredTime = 600;

	private String timeParam = "t";

	private String userParam = "cid";

	private String digestParam = "s";

	private String extra = "123456!";

	public String obtainUsername(HttpServletRequest request) {
		String ip = RequestUtils.getIpAddr(request);
		String cid = request.getParameter(userParam);
		String timeParamStr = request.getParameter(timeParam);
		Long t = null;
		if (null != timeParamStr) {
			t = Long.valueOf(timeParamStr);
		}
		String s = request.getParameter(digestParam);
		if (null == t || null == s || null == cid || null == ip) return null;
		String full = cid + "," + ip + "," + t + "," + extra;
		String digest = EncryptUtil.encode(full, "MD5");
		if (logger.isDebugEnabled()) {
			logger.debug("user {} at :{}", cid, ip);
			logger.debug("time:{} digest:{} ", t, s);
			logger.debug("full:{}", full);
			logger.debug("my_digest:{}", digest);
		}
		if (digest.equals(s)) {
			long time = t.longValue() * 1000;
			Date now = new Date();
			if (enableExpired && (Math.abs(now.getTime() - time) > (expiredTime * 1000))) {
				logger.debug("user " + cid + " time expired:server time:{} and given time :{}", now,
						new Date(time));
				return null;
			} else {
				logger.debug("user {} login at server time:{}", cid, now);
				return cid;
			}
		} else {
			return null;
		}
	}

	public boolean isEnableExpired() {
		return enableExpired;
	}

	public void setEnableExpired(boolean enableTime) {
		this.enableExpired = enableTime;
	}

	public int getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(int expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getTimeParam() {
		return timeParam;
	}

	public void setTimeParam(String timeParam) {
		this.timeParam = timeParam;
	}

	public String getUserParam() {
		return userParam;
	}

	public void setUserParam(String userParam) {
		this.userParam = userParam;
	}

	public String getDigestParam() {
		return digestParam;
	}

	public void setDigestParam(String digestParam) {
		this.digestParam = digestParam;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}
