/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class HttpUtils {

	public static String getResponseText(String url) {
		try {
			return getResponseText(new URL(url), null);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getResponseText(URL constructedUrl, String encoding) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) constructedUrl.openConnection();
			BufferedReader in = null;
			if (null == encoding) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			}
			String line;
			final StringBuffer stringBuffer = new StringBuffer(255);

			synchronized (stringBuffer) {
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line);
					stringBuffer.append("\n");
				}
				return stringBuffer.toString();
			}
		} catch (final Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}
