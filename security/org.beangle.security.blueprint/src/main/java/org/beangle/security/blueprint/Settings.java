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
package org.beangle.security.blueprint;

import org.beangle.commons.config.property.PropertyConfig;
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
