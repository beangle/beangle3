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
package org.beangle.commons.config;

/**
 * <p>
 * Version class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 * @since 3.2.0
 */
public class Version {

  private String name;

  private String vendor;

  private String version;

  private int majorVersion;

  private int minorVersion;

  /**
   * <p>
   * Getter for the field <code>name</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return name;
  }

  /** {@inheritDoc} */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * <p>
   * Getter for the field <code>vendor</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getVendor() {
    return vendor;
  }

  /** {@inheritDoc} */
  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  /**
   * <p>
   * Getter for the field <code>version</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getVersion() {
    return version;
  }

  /** {@inheritDoc} */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * <p>
   * Getter for the field <code>majorVersion</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getMajorVersion() {
    return majorVersion;
  }

  /** {@inheritDoc} */
  public void setMajorVersion(int majorVersion) {
    this.majorVersion = majorVersion;
  }

  /**
   * <p>
   * Getter for the field <code>minorVersion</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getMinorVersion() {
    return minorVersion;
  }

  /** {@inheritDoc} */
  public void setMinorVersion(int minorVersion) {
    this.minorVersion = minorVersion;
  }
}
