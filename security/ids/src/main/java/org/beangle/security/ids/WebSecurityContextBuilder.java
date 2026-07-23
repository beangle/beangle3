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
package org.beangle.security.ids;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.security.RequestConvertor;
import org.beangle.commons.web.util.CookieUtils;
import org.beangle.security.core.context.RunAs;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.session.SessionRepo;
import org.beangle.security.core.userdetail.Account;
import org.beangle.security.core.userdetail.Profile;
import org.beangle.security.ids.session.SessionIdReader;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebSecurityContextBuilder implements SecurityContextBuilder {

  private SessionIdReader sessionIdReader;

  private SessionRepo sessionRepo;

  private RequestConvertor requestConvertor;

  public static final String ProfileCookieName = "beangle.security.profileId";

  @Override
  public SecurityContext build(HttpServletRequest request, HttpServletResponse response) {
    Option<String> os = sessionIdReader.getId(request, response);
    Session session = null;
    if (os.isDefined()) {
      String sessionId = os.get();
      session = sessionRepo.get(sessionId);
      if (null != session) {
        sessionRepo.access(sessionId, Instant.now());
      }
    }
    Profile profile = null;
    RunAs runAs = null;
    if (null != session) {
      boolean isRoot = ((Account) session.getPrincipal()).isRoot();
      if (isRoot) {
        String rs = CookieUtils.getCookieValue(request, "beangle.security.runAs");
        if (null != rs && rs.startsWith("{") && rs.endsWith("}")) {
          Map data = new Gson().fromJson(rs, Map.class);
          if (data.containsKey("name")) {
            String name = data.get("name").toString();
            List<Profile> profileList = new ArrayList<>();
            if (data.containsKey("profiles")) {
              List<Map> mps = (List<Map>) data.get("profiles");
              for (Map mp : mps) {
                if (mp.containsKey("id") && mp.containsKey("name")) {
                  Profile p = new Profile(Long.valueOf(mp.get("id").toString()),
                    mp.get("name").toString(), (Map<String, String>) mp.get("properties"));
                  profileList.add(p);
                }
              }
            }
            runAs = new RunAs(name, profileList.toArray(new Profile[0]));
          }
        }
      }

      String profileId = request.getParameter("contextProfileId");
      if (null == profileId) {
        profileId = CookieUtils.getCookieValue(request, ProfileCookieName);
      }
      if (Strings.isNotBlank(profileId)) {
        Profile[] profiles;
        if (null == runAs) {
          profiles = ((Account) session.getPrincipal()).getProfiles();
        } else {
          profiles = runAs.getProfiles();
        }
        if (null != profiles) {
          long longProfileId = Long.parseLong(profileId);
          for (Profile p : profiles) {
            if (p.id == longProfileId) {
              profile = p;
              break;
            }
          }
          if (null == profile) {
            profile = profiles[0];
          }
        }
      } else {
        Profile[] profiles = ((Account) session.getPrincipal()).getProfiles();
        if (null != profiles) {
          profile = profiles[0];
          //CookieUtils.addCookie(request, response, ProfileCookieName, String.valueOf(profile.id), 60 * 60 * 24 * 7);// 7 days
        }
      }
    }
    String runAsName = (null == runAs) ? null : runAs.getName();
    return new SecurityContext(session, requestConvertor.convert(request), profile, runAsName);
  }

  public void setSessionIdReader(SessionIdReader sessionIdReader) {
    this.sessionIdReader = sessionIdReader;
  }

  public void setSessionRepo(SessionRepo sessionRepo) {
    this.sessionRepo = sessionRepo;
  }

  public void setRequestConvertor(RequestConvertor requestConvertor) {
    this.requestConvertor = requestConvertor;
  }

}
