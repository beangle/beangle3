/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.web.util;

import org.beangle.commons.io.IOs;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

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

  public static boolean access(URL url) {
    try {
      HttpURLConnection hc = followRedirect(url.openConnection(), "HEAD");
      int rc = hc.getResponseCode();
      return rc == HttpURLConnection.HTTP_OK;
    } catch (Exception e) {
      return false;
    }
  }

  private static HttpURLConnection followRedirect(URLConnection c, String method) {
    try {
      HttpURLConnection conn = (HttpURLConnection) c;
      conn.setRequestMethod(method);
      conn.setInstanceFollowRedirects(false);
      Https.noverify(conn);
      int rc = conn.getResponseCode();
      if (rc == HttpURLConnection.HTTP_OK) {
        return conn;
      } else if (rc == HttpURLConnection.HTTP_MOVED_TEMP || rc == HttpURLConnection.HTTP_MOVED_PERM) {
        String newLoc = conn.getHeaderField("location");
        return followRedirect(new URL(newLoc).openConnection(), method);
      } else {
        return conn;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public static String getResponseText(URL constructedUrl, String encoding) {
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) constructedUrl.openConnection();
      Https.noverify(conn);
      conn.setConnectTimeout(5 * 1000);
      conn.setReadTimeout(5 * 1000);
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

  public static byte[] getData(String urlString) {
    HttpURLConnection conn = null;
    try {
      URL url = new URL(urlString);
      conn = (HttpURLConnection) url.openConnection();
      conn.setConnectTimeout(5 * 1000);
      conn.setReadTimeout(5 * 1000);
      conn.setRequestMethod("GET");
      conn.setDoOutput(true);
      Https.noverify(conn);
      if (conn.getResponseCode() == 200) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOs.copy(conn.getInputStream(), bos);
        return bos.toByteArray();
      } else {
        return new byte[0];
      }
    } catch (Exception e) {
      return new byte[0];
    } finally {
      if (null != conn) conn.disconnect();
    }
  }


  public static int invoke(URL url, byte[] body, String contentType) {
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) url.openConnection();
      Https.noverify(conn);
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", contentType);

      OutputStream os = conn.getOutputStream();
      os.write(body);
      os.flush();
      os.close(); //don't forget to close the OutputStream
      conn.connect();
      return conn.getResponseCode();
    } catch (Exception e) {
      try {
        System.out.println("access url:" + conn.getResponseCode() + " " + url);
        return conn.getResponseCode();
      } catch (Exception e2) {
        return 500;
      }
    } finally {
      if (null != conn) conn.disconnect();
    }
  }

}
