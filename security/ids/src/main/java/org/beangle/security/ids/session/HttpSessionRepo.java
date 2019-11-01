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
package org.beangle.security.ids.session;

import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.commons.web.util.Https;
import org.beangle.security.core.session.DefaultSession;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.userdetail.DefaultAccount;
import org.beangle.security.session.protobuf.Model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;

public class HttpSessionRepo extends CacheSessionRepo {

  private String geturl;
  private String accessUrl;
  private String expireUrl;

  protected Option<Session> getInternal(String sessionId) {
    byte[] data = HttpUtils.getData(Strings.replace(geturl, "{id}", sessionId));
    if (null != data && data.length > 0) {
      ByteArrayInputStream is = new ByteArrayInputStream(data);
      Model.Session s;
      try {
        s = Model.Session.parseFrom(is);
        DefaultSession session = new DefaultSession(s.getId(), toAccount(s.getPrincipal()),
          Instant.ofEpochSecond(s.getLoginAt()), toAgent(s.getAgent()));
        session.setTtiSeconds(s.getTtiSeconds());
        session.setLastAccessAt(Instant.ofEpochSecond(s.getLastAccessAt()));
        return Option.some(session);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return Option.none();
    }
  }

  private Session.Agent toAgent(Model.Agent a) {
    return new Session.Agent(a.getName(), a.getIp(), a.getOs());
  }

  private DefaultAccount toAccount(Model.Account pa) {
    DefaultAccount account = new DefaultAccount(pa.getName(), pa.getDescription());
    account.setStatus(pa.getStatus());
    account.setAuthorities(pa.getAuthorities());
    account.setPermissions(pa.getPermissions());
    account.setRemoteToken(pa.getRemoteToken());
    account.setCategoryId(pa.getCategoryId());
    Iterator<Map.Entry<String, String>> dk = pa.getDetailsMap().entrySet().iterator();
    while (dk.hasNext()) {
      Map.Entry<String, String> entry = dk.next();
      account.getDetails().put(entry.getKey(), entry.getValue());
    }
    return account;
  }

  @Override
  boolean flush(Session session) {
    String surl = Strings.replace(accessUrl, "{id}", session.getId());
    surl = Strings.replace(surl, "{time}", String.valueOf(session.getLastAccessAt().getEpochSecond()));
    try {
      URL url = new URL(surl);
      HttpURLConnection hc = (HttpURLConnection) url.openConnection();
      hc.setRequestMethod("GET");
      Https.noverify(hc);
      return hc.getResponseCode() == 200;
    } catch (Exception e) {
      e.printStackTrace();
      return true;
    }
  }

  @Override
  void expire(String sid) {
    String surl = Strings.replace(expireUrl, "{id}", sid);
    try {
      URL url = new URL(surl);
      HttpURLConnection hc = (HttpURLConnection) url.openConnection();
      hc.setRequestMethod("GET");
      Https.noverify(hc);
      hc.getResponseCode();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setGeturl(String geturl) {
    this.geturl = geturl;
  }

  public void setAccessUrl(String accessUrl) {
    this.accessUrl = accessUrl;
  }

  public void setExpireUrl(String expireUrl) {
    this.expireUrl = expireUrl;
  }

}
