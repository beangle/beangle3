/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.struts2.captcha.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.struts2.captcha.service.CaptchaProvider;

/**
 * Google support online recaptcha
 * 
 * @author chaostone
 */
public class RecaptchaProvider implements CaptchaProvider {
  private static Set<String> buildins = CollectUtils.newHashSet("red", "clean", "white", "blackglass");

  String privatekey;
  String publickey;
  String apibase = "http://www.google.com/recaptcha/api";

  public boolean isBuildinTheming(String theming) {
    return buildins.contains(theming);
  }

  public RecaptchaProvider() {
    super();
  }

  public RecaptchaProvider(String publickey, String privatekey) {
    super();
    this.publickey = publickey;
    this.privatekey = privatekey;
    this.apibase = "http://www.google.com/recaptcha/api";
  }

  public RecaptchaProvider(String apibase, String publickey, String privatekey) {
    super();
    this.apibase = apibase;
    this.publickey = publickey;
    this.privatekey = privatekey;
  }

  @Override
  public boolean verify(HttpServletRequest request, String response) {
    String remoteip = RequestUtils.getIpAddr(request);
    String challenge = request.getParameter("recaptcha_challenge_field");
    if (Strings.isEmpty(response)) { return false; }
    String result = HttpUtils.getResponseText(apibase + "/verify?remoteip=" + remoteip + "&privatekey="
        + privatekey + "&challenge=" + challenge + "&response=" + response);
    if (!result.contains("true")) { return false; }
    return true;
  }

  @Override
  public ByteArrayOutputStream generater(HttpServletRequest request) {
    // do not to impl,because it will generate from google website
    return null;
  }

  public String getPrivatekey() {
    return privatekey;
  }

  public String getPublickey() {
    return publickey;
  }

  public void setPrivatekey(String privatekey) {
    this.privatekey = privatekey;
  }

  public void setPublickey(String publickey) {
    this.publickey = publickey;
  }

}
