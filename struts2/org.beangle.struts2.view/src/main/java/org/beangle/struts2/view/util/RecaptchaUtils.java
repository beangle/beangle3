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
package org.beangle.struts2.view.util;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;

/**
 * Recapcha Utility
 * 
 * @author chaostone
 * @since 3.0.0
 */
public final class RecaptchaUtils {

  public static boolean isValid(String remoteip, String privatekey, String challenge, String response) {
    if (Strings.isEmpty(response)) { return false; }
    String result = HttpUtils.getResponseText("http://www.google.com/recaptcha/api/verify?remoteip="
        + remoteip + "&privatekey=" + privatekey + "&challenge=" + challenge + "&response=" + response);
    if (!result.contains("true")) { return false; }
    return true;
  }
}
