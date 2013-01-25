/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.struts2.convention.route.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.inject.Resources;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.convention.Constants;
import org.beangle.struts2.convention.route.Profile;
import org.beangle.struts2.convention.route.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileServiceImpl implements ProfileService {

  private final static Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

  private List<Profile> profiles = new ArrayList<Profile>();

  private Profile defaultProfile = new Profile();

  // 匹配缓存[String,Profile]
  private Map<String, Profile> cache = new ConcurrentHashMap<String, Profile>();

  private Resources resources;

  public ProfileServiceImpl() {
    super();
    defaultProfile.setActionSuffix("Action");
    defaultProfile.setActionScan(true);
    defaultProfile.setDefaultMethod("index");
    defaultProfile.setUriPath("/");
    defaultProfile.setUriPathStyle(Constants.SEO_URI);
    defaultProfile.setUriExtension("action");
    // defaultProfile.setViewPath("/pages/");
    defaultProfile.setViewPathStyle(Constants.FULL_VIEWPATH);
    defaultProfile.setViewExtension("ftl");
    setResources(getDefaultResource());
  }

  private Resources getDefaultResource() {
    Resources resources = new Resources();
    resources.setGlobal(ClassLoaders.getResource("META-INF/beangle/convention-default.properties",
        ProfileServiceImpl.class));
    List<URL> urls = ClassLoaders.getResources("META-INF/beangle/convention-route.properties",
        ProfileServiceImpl.class);
    for (URL url : urls)
      resources.getLocals().add(url);
    return resources;
  }

  public Profile getProfile(String className) {
    Profile matched = cache.get(className);
    if (null != matched) { return matched; }
    int index = -1;
    int patternLen = 0;
    for (Profile profile : profiles) {
      int newIndex = profile.matchedIndex(className);
      if (newIndex < 0) continue;
      if (newIndex >= index && profile.getActionPattern().length() >= patternLen) {
        matched = profile;
        index = newIndex;
        patternLen = profile.getActionPattern().length();
      }
    }
    if (matched == null) {
      matched = defaultProfile;
    }
    cache.put(className, matched);
    logger.debug("{} match profile:{}", className, matched);
    return matched;
  }

  public Profile getProfile(Class<?> clazz) {
    return getProfile(clazz.getName());
  }

  private Properties getProperties(URL url) {
    logger.debug("loading {}", url);
    InputStream in = null;
    try {
      in = url.openStream();
      if (in != null) {
        Properties p = new Properties();
        p.load(in);
        return p;
      }
    } catch (IOException e) {
      logger.error("Error while loading " + url, e);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException io) {
          logger.warn("Unable to close input stream", io);
        }
      }
    }
    return null;
  }

  /**
   * 初始化配置,依次加载META-INF/convention-default.properties,META-INF/convention-route.properties
   */
  private void loadProfiles() {
    URL convention_default = resources.getGlobal();
    if (null == convention_default) { throw new RuntimeException("cannot find convention-default.properties!"); }
    List<Profile> defaultProfiles = buildProfiles(convention_default, true);
    if (!defaultProfiles.isEmpty()) {
      defaultProfile = defaultProfiles.get(0);
    }
    for (URL url : resources.getLocals()) {
      profiles.addAll(buildProfiles(url, false));
    }
  }

  private List<Profile> buildProfiles(URL url, boolean isDefault) {
    List<Profile> myProfiles = CollectUtils.newArrayList();
    Properties props = getProperties(url);
    if (isDefault) {
      Profile profile = populatProfile(props, "default");
      myProfiles.add(profile);
    } else {
      int profileIndex = 0;
      while (true) {
        Profile profile = populatProfile(props, "profile" + profileIndex);
        if (null == profile) {
          break;
        } else {
          myProfiles.add(profile);
        }
        profileIndex++;
      }
    }
    return myProfiles;
  }

  private Profile populatProfile(Properties props, String name) {
    Profile profile = new Profile();
    profile.setName(name);
    String actionPattern = props.getProperty(profile.getName() + ".actionPattern");
    if (Strings.isEmpty(actionPattern)) { return null; }
    populateAttr(profile, "actionPattern", props);
    populateAttr(profile, "actionSuffix", props);
    populateAttr(profile, "viewPath", props);
    populateAttr(profile, "viewExtension", props);
    populateAttr(profile, "viewPathStyle", props);
    populateAttr(profile, "defaultMethod", props);
    populateAttr(profile, "uriPath", props);
    populateAttr(profile, "uriPathStyle", props);
    populateAttr(profile, "uriExtension", props);
    populateAttr(profile, "actionScan", props);
    // 保证页面路径以/结束
    // if (!profile.getViewPath().endsWith(Convention.separator + "")) {
    // profile.setViewPath(profile.getViewPath() + Convention.separator);
    // }
    // FIXME validate attribute
    return profile;
  }

  private void populateAttr(Profile profile, String attr, Properties props) {
    Object value = props.getProperty(profile.getName() + "." + attr);
    try {
      if (null == value) {
        value = PropertyUtils.getProperty(defaultProfile, attr);
      }
      BeanUtils.copyProperty(profile, attr, value);
    } catch (Exception e) {
      logger.error("error attr {} for profile", attr);
    }
  }

  public List<Profile> getProfiles() {
    return profiles;
  }

  public void setProfiles(List<Profile> profiles) {
    this.profiles = profiles;
  }

  public Profile getDefaultProfile() {
    return defaultProfile;
  }

  public void setDefaultProfile(Profile defaultProfile) {
    this.defaultProfile = defaultProfile;
  }

  public Resources getResources() {
    return resources;
  }

  public void setResources(Resources resources) {
    this.resources = resources;
    if (null != resources) {
      loadProfiles();
    } else {
      if (null != profiles) {
        profiles.clear();
      }
    }
  }

}
