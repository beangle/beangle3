/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
