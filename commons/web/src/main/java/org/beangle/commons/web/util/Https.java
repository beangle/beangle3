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

import java.net.HttpURLConnection;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

public class Https {

  private static final NullTrustManager NullTM = new NullTrustManager();
  private static final TrustAllHosts TrustAll = new TrustAllHosts();

  public static void noverify(HttpURLConnection connection) {
    if (connection instanceof HttpsURLConnection) {
      HttpsURLConnection conn = (HttpsURLConnection) connection;
      conn.setHostnameVerifier(TrustAll);
      try {
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, new TrustManager[] { NullTM }, new java.security.SecureRandom());
        conn.setSSLSocketFactory(sslContext.getSocketFactory());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static class NullTrustManager extends X509ExtendedTrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] c, String at) throws CertificateException {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] c, String at, SSLEngine engine) {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] c, String at, Socket socket) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] c, String s) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] c, String s, SSLEngine engine) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] c, String s, Socket socket) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }
  }

  private static final class TrustAllHosts implements HostnameVerifier {
    public boolean verify(String arg0, SSLSession arg1) {
      return true;
    }
  }
}
