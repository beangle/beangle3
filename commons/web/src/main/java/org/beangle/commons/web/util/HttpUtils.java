/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public final class HttpUtils {

  public static String getResponseText(String urlString) {
    try {
      // escape special char(space)
      URL url = new URL(urlString);
      URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
          url.getQuery(), url.getRef());
      url = uri.toURL();
      return getResponseText(url, null);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getResponseText(URL constructedUrl, String encoding) {
    return getResponseText(constructedUrl, null, encoding);
  }

  public static String getResponseText(URL constructedUrl, final HostnameVerifier hostnameVerifier,
      String encoding) {
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) constructedUrl.openConnection();
      if (conn instanceof HttpsURLConnection && null != hostnameVerifier) {
        ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
      }
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
      throw new RuntimeException(e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
  }

}
