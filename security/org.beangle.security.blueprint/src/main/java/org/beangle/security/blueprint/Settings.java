/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General  License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.commons.context.property.PropertyConfig;
import org.beangle.commons.lang.Assert;

/**
 * Security Settings
 * 
 * @author chaostone
 * @since 3.0.0
 */
public final class Settings {

  public static final String MinPasswordLength = "security.password.minlength";
  public static final String MaxPasswordLength = "security.password.maxlength";
  public static final String MinPasswordStrength = "security.password.minstrength";

  /** minimal password length */
  private final int minPwdLength;

  /** maxamum password length */
  private final int maxPwdLength;

  /** minimal password strength */
  private final int minPwdStrength;

  public Settings(PropertyConfig config) {
    Assert.notNull(config);
    Integer minLength = config.get(Integer.class, MinPasswordLength);

    if (null != minLength && minLength > 0) minPwdLength = minLength.intValue();
    else minPwdLength = 3;

    Integer maxLength = config.get(Integer.class, MaxPasswordLength);
    if (null != maxLength && maxLength > 0) maxPwdLength = maxLength.intValue();
    else maxPwdLength = 30;

    Integer minStrength = config.get(Integer.class, MinPasswordStrength);
    if (null != minStrength && minStrength > 0) minPwdStrength = minStrength.intValue();
    else minPwdStrength = 1;
  }

  public int getMinPwdLength() {
    return minPwdLength;
  }

  public int getMaxPwdLength() {
    return maxPwdLength;
  }

  public int getMinPwdStrength() {
    return minPwdStrength;
  }

}
